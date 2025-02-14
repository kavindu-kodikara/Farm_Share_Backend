package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepo extends JpaRepository<Documents,Integer> {
}
