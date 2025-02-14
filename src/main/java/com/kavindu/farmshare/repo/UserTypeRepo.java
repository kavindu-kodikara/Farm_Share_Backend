package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepo  extends JpaRepository<UserType,Integer> {
}
