package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.RequestDto;
import com.kavindu.farmshare.dto.TransactionDto;
import com.kavindu.farmshare.entity.Transaction;
import com.kavindu.farmshare.entity.User;
import com.kavindu.farmshare.model.TransactionItem;
import com.kavindu.farmshare.repo.TransactionRepo;
import com.kavindu.farmshare.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    UserRepo userRepo;

    public TransactionDto loadTransaction(RequestDto requestDto){
        TransactionDto transactionDto = new TransactionDto();

        User user = userRepo.findById(requestDto.getId()).get();

        LocalDate today = LocalDate.now();
        List<Transaction> transactions = transactionRepo.findAllByDateAndUser(today,user);

        ArrayList<TransactionItem> todayList = new ArrayList<>();
        for (Transaction transaction : transactions){
            TransactionItem transactionItem = new TransactionItem();

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, HH:mm", Locale.ENGLISH);
            String formattedDate = sdf.format(transaction.getTime());

            transactionItem.setName(transaction.getTitle());
            transactionItem.setTime(formattedDate);

            String price;
            if(transaction.getTransactionType().getName().equals("Deposit")){
                price = "Rs. +"+ new DecimalFormat("#,###").format(transaction.getPrice());

            }else{
                price = "Rs. "+ new DecimalFormat("#,###").format(transaction.getPrice());            }

            transactionItem.setPrice(price);

            transactionItem.setType(transaction.getTransactionType().getName());

            todayList.add(transactionItem);
        }

        List<Transaction> oldTransactions = transactionRepo.findAllBeforeTodayByUser(today,user);

        ArrayList<TransactionItem> oldList = new ArrayList<>();
        for (Transaction transaction : oldTransactions){
            TransactionItem transactionItem = new TransactionItem();

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, HH:mm", Locale.ENGLISH);
            String formattedDate = sdf.format(transaction.getTime());

            transactionItem.setName(transaction.getTitle());
            transactionItem.setTime(formattedDate);

            String price;
            if(transaction.getTransactionType().getName().equals("Deposit")){
                 price = "Rs. +"+ new DecimalFormat("#,###").format(transaction.getPrice());

            }else{
                 price = "Rs. "+ new DecimalFormat("#,###").format(transaction.getPrice());            }

            transactionItem.setPrice(price);
            transactionItem.setType(transaction.getTransactionType().getName());

            oldList.add(transactionItem);
        }


        transactionDto.setTodayList(todayList);
        transactionDto.setOldList(oldList);
        transactionDto.setSuccess(true);
        return transactionDto;
    }
}
