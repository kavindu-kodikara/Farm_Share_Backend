package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.ResponseDto;
import com.kavindu.farmshare.dto.UserDto;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.entity.UserType;
import com.kavindu.farmshare.repo.ActiveStatusRepo;
import com.kavindu.farmshare.repo.UserRepo;
import com.kavindu.farmshare.repo.UserTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private ActiveStatusRepo activeStatusRepo;

    public ResponseDto signUp(UserDto userDto,boolean isFarmer){
        ResponseDto responseDto = new ResponseDto();

        UserType userTypeFarmer;

        if (isFarmer){
            userTypeFarmer = userTypeRepo.getReferenceById(1);
        }else{
            userTypeFarmer = userTypeRepo.getReferenceById(2);
        }

        User userCheck = userRepo.findByMobileAndUserType(userDto.getMobile(),userTypeFarmer);


        if (userCheck == null){

            User user = new User();

            user.setDate(new Date());
            user.setActiveStatus(activeStatusRepo.getReferenceById(2));
            user.setFname(userDto.getFname());
            user.setLname(userDto.getLname());
            user.setMobile(userDto.getMobile());
            user.setPassword(userDto.getPassword());
            user.setUserType(userTypeFarmer);

            User savedUser = userRepo.save(user);

            UserDto responseUserDto = new UserDto(savedUser.getId(),savedUser.getMobile(),
                    savedUser.getFname(),
                    savedUser.getLname(),
                    savedUser.getPassword(),
                    savedUser.getDate(),"",
                    savedUser.getUserType().getName(),
                    savedUser.getActiveStatus().getName());

            responseDto.setData(responseUserDto);

            responseDto.setSuccess(true);
            responseDto.setMessage("Account Created");

        }else {
            responseDto.setMessage("Mobile number already exist");
        }

        return responseDto;
    }

    public ResponseDto signIn(UserDto userDto, boolean isFarmer){
        ResponseDto responseDto = new ResponseDto();

        UserType userType;

        if (isFarmer){
            userType = userTypeRepo.getReferenceById(1);
        }else{
            userType = userTypeRepo.getReferenceById(2);
        }

        User user = userRepo.findByMobileAndPasswordAndUserType(userDto.getMobile(),userDto.getPassword(),userType);

        if (user != null){

            UserDto respUserDto = new UserDto(user.getId(), user.getMobile(),
                    user.getFname(),
                    user.getLname(),
                    user.getPassword(),
                    user.getDate(),"",
                    user.getUserType().getName(),
                    user.getActiveStatus().getName());
            respUserDto.setProfilePic(user.getProfilePic() != null ? user.getProfilePic() : "");

            responseDto.setData(respUserDto);
            responseDto.setSuccess(true);
            responseDto.setMessage("Sign-in success");

        }else {
            responseDto.setMessage("Check your mobile or password");
        }



        return responseDto;
    }

    public ResponseDto update(UserDto userDto){
        ResponseDto responseDto = new ResponseDto();

        User user = userRepo.findById(userDto.getId()).get();
        user.setFname(userDto.getFname());
        user.setLname(userDto.getLname());
        user.setPassword(userDto.getPassword());
        user.setProfilePic(userDto.getProfilePic());

        User savedUser = userRepo.save(user);

        UserDto responseUserDto = new UserDto(savedUser.getId(),savedUser.getMobile(),
                savedUser.getFname(),
                savedUser.getLname(),
                savedUser.getPassword(),
                savedUser.getDate(),
                savedUser.getProfilePic(),
                savedUser.getUserType().getName(),
                savedUser.getActiveStatus().getName());


        responseDto.setData(responseUserDto);

        responseDto.setSuccess(true);
        responseDto.setMessage("Account Updated");

        return responseDto;
    }

}
