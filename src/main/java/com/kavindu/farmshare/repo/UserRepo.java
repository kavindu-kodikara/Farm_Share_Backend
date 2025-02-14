package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByMobileAndUserType(String mobile, UserType userType);
    public User findByMobileAndPasswordAndUserType(String mobile, String password, UserType userType);
}
