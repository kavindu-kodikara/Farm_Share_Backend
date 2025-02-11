package com.kavindu.farmshare.service;

import com.kavindu.farmshare.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FarmerService {

    @Autowired
    private UserRepo userRepo;

}
