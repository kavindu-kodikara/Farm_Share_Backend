package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.FarmerHomeDto;
import com.kavindu.farmshare.dto.NameIdDto;
import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.entity.Farm;
import com.kavindu.farmshare.entity.StockPrice;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.repo.FarmRepo;
import com.kavindu.farmshare.repo.StockPriceRepo;
import com.kavindu.farmshare.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FarmerHomeService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    FarmRepo farmRepo;

    @Autowired
    StockPriceRepo stockPriceRepo;

    public FarmerHomeDto loadHome(RequestDto requestDto){

        User user = userRepo.findById(requestDto.getId()).get();

        List<Farm> farmList = farmRepo.findAllByUser(user);

        ArrayList<NameIdDto> chipArrayList = new ArrayList<>();

        for (Farm farm : farmList){
            NameIdDto nameIdDto = new NameIdDto();
            nameIdDto.setName(farm.getFarmName());
            nameIdDto.setId(farm.getId());

            chipArrayList.add(nameIdDto);
        }

        RequestDto farmIdDto = new RequestDto(farmList.get(0).getId());

        FarmerHomeDto farmerHomeDto = loadHomeData(farmIdDto);

        farmerHomeDto.setChipArray(chipArrayList);
        farmerHomeDto.setSuccess(true);
        return farmerHomeDto;
    }

    public FarmerHomeDto loadHomeData(RequestDto requestDto){
        FarmerHomeDto farmerHomeDto = new FarmerHomeDto();

        Farm farm = farmRepo.findById(requestDto.getId()).get();
        StockPrice stockPrice = stockPriceRepo.findTopByFarmOrderByDateDesc(farm);

        farmerHomeDto.setRiskScore(farm.getRiskScore());
        farmerHomeDto.setCropType(farm.getCropType().getName());
        farmerHomeDto.setFarmName(farm.getFarmName());
        farmerHomeDto.setTotStock(farm.getTotStock());
        farmerHomeDto.setRelesedStock(farm.getReleasedStock());
        farmerHomeDto.setStockProgress(farm.getStockProgress());
        farmerHomeDto.setExpectIncome(farm.getTotStock() * stockPrice.getPrice());

        return farmerHomeDto;
    }

}
