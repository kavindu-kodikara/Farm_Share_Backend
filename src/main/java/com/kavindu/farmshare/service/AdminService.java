package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.*;
import com.kavindu.farmshare.repo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    ActiveStatusRepo activeStatusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserTypeRepo userTypeRepo;

    @Autowired
    SoilDocRepo soilDocRepo;

    @Autowired
    FarmStatusRepo farmStatusRepo;

    @Autowired
    StockAllocationRepo stockAllocationRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    @Autowired
    SeasonRepo seasonRepo;

    @Autowired
    FarmPaymentRepo farmPaymentRepo;

    @Autowired
    ReturnTypeRepo returnTypeRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    TransactionTypeRepo transactionTypeRepo;


    public ResponseDto signIn(HttpSession session, UserDto userDto){
        ResponseDto responseDto = new ResponseDto();

        if(adminRepo.existsByMobile(userDto.getMobile())){
            session.setAttribute("mobile",userDto.getMobile());
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Invalid user name or password");
        }


        return responseDto;
    }

    public AdminFarmDto loadPendingFarms(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        ActiveStatus activeStatus = activeStatusRepo.findById(1).get();

        List<Farm> searchFarmList = farmRepo.findAllByActiveStatus(activeStatus);
        List<Farm> farmList = new ArrayList<>();

        for (Farm farm : searchFarmList){
            SoilDoc soilDoc = soilDocRepo.findByFarm(farm);
            farm.setSoilDocUrl(soilDoc.getDocument());
            farmList.add(farm);
        }

        adminFarmDto.setFarmList(farmList);

        adminFarmDto.setSuccess(true);
        return  adminFarmDto;
    }

    public AdminFarmDto loadFarms(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        ActiveStatus activeStatus = activeStatusRepo.findById(1).get();

        adminFarmDto.setFarmList(farmRepo.findByActiveStatusNot(activeStatus));

        adminFarmDto.setSuccess(true);
        return  adminFarmDto;
    }

    public ResponseDto updateStatus(RequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();

        ActiveStatus activeStatus = activeStatusRepo.findByName(requestDto.getValue());
        Farm farm = farmRepo.findById(requestDto.getId()).get();
        farm.setActiveStatus(activeStatus);
        farmRepo.save(farm);

        responseDto.setSuccess(true);

        return responseDto;
    }

    public AdminUserDto loadUsers(){
        AdminUserDto adminUserDto = new AdminUserDto();

        UserType farmerType = userTypeRepo.findById(1).get();
        UserType investorType = userTypeRepo.findById(2).get();

        List<User> farmerList = userRepo.findAllByUserType(farmerType);
        List<User> investorList = userRepo.findAllByUserType(investorType);

        adminUserDto.setFarmerList(farmerList);
        adminUserDto.setInvestorList(investorList);
        adminUserDto.setSuccess(true);

        return adminUserDto;
    }

    public ResponseDto updateUserStatus(RequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();

        ActiveStatus activeStatus = activeStatusRepo.findByName(requestDto.getValue());
        User user = userRepo.findById(requestDto.getId()).get();
        user.setActiveStatus(activeStatus);
        userRepo.save(user);

        responseDto.setSuccess(true);

        return responseDto;
    }

    public AdminFarmDto loadPayment(){
        AdminFarmDto adminFarmDto = new AdminFarmDto();

        ActiveStatus activeStatus = activeStatusRepo.findById(2).get();
        FarmStatus farmStatus = farmStatusRepo.findById(1).get();
        ReturnType returnType = returnTypeRepo.findById(1).get();


        List<Farm> searchFarmList = farmRepo.findAllByActiveStatusAndFarmStatus(activeStatus,farmStatus);
        List<AdminPaymentDto> paymentList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Farm farm : searchFarmList){
            AdminPaymentDto adminPaymentDto = new AdminPaymentDto();

            List<StockAllocation> stockAllocationList = stockAllocationRepo.findAllByFarmAndReturnType(farm,returnType);
            StockPrice stockPrice = stockPriceRepo.findByFarmAndDate(farm,today);
            Season season = seasonRepo.findTopByFarm(farm);
            FarmPayment farmPayment = farmPaymentRepo.findTopBySeason(season);

            adminPaymentDto.setFarm(farm);
            adminPaymentDto.setStockAllocationList(stockAllocationList);
            adminPaymentDto.setPrice(stockPrice.getPrice());
            adminPaymentDto.setPayment(farmPayment.getPrice());
            paymentList.add(adminPaymentDto);

        }

        adminFarmDto.setAdminPaymentDtoList(paymentList);

        adminFarmDto.setSuccess(true);
        return  adminFarmDto;
    }

    public ResponseDto updatefarmPayment(RequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();

        LocalDate today = LocalDate.now();
        Farm farm = farmRepo.findById(requestDto.getId()).get();
        List<StockAllocation> stockAllocationList = stockAllocationRepo.findAllByFarm(farm);
        StockPrice stockPrice = stockPriceRepo.findByFarmAndDate(farm,today);

        TransactionType transactionType = transactionTypeRepo.findById(1).get();

        for (StockAllocation stockAllocation : stockAllocationList){
            if (stockAllocation.getReturnType().getId() != 2){
                Transaction transaction = new Transaction();
                transaction.setTitle(farm.getFarmName());
                transaction.setTime(new Date());
                transaction.setPrice(stockAllocation.getCount() * stockPrice.getPrice());
                transaction.setFromUser(farm.getUser());
                transaction.setToUser(stockAllocation.getUser());
                transaction.setTransactionType(transactionType);
                transactionRepo.save(transaction);
            }

        }

        FarmStatus farmStatus = farmStatusRepo.findById(6).get();

        farm.setFarmStatus(farmStatus);
        farm.setStockProgress(0);
        farm.setReleasedStock(0);
        farmRepo.save(farm);
        stockAllocationRepo.deleteAllInBatch(stockAllocationList);

        responseDto.setSuccess(true);

        return responseDto;
    }

}
