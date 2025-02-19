package com.kavindu.farmshare.repo;

import com.kavindu.farmshare.entity.Transaction;
import com.kavindu.farmshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionRepo  extends JpaRepository<Transaction,Integer> {
    @Query("SELECT t FROM Transaction t " +
            "WHERE FUNCTION('DATE', t.time) = :today " +
            "AND (t.fromUser = :user OR t.toUser = :user)")
    List<Transaction> findAllByDateAndUser(
            @Param("today") LocalDate today,
            @Param("user") User user
    );

    @Query("SELECT t FROM Transaction t " +
            "WHERE FUNCTION('DATE', t.time) < :today " +
            "AND (t.fromUser = :user OR t.toUser = :user)")
    List<Transaction> findAllBeforeTodayByUser(
            @Param("today") LocalDate today,
            @Param("user") User user
    );
}
