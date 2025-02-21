package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.*;
import com.kavindu.farmshare.entity.ActiveStatus;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.entity.UserType;
import com.kavindu.farmshare.repo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        adminFarmDto.setFarmList(farmRepo.findAllByActiveStatus(activeStatus));

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

}
