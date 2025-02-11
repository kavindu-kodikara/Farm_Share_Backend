package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
