package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo  extends JpaRepository<Admin,Integer> {
}
