package com.example.librarymanagementsystem.Repository;

import com.example.librarymanagementsystem.Entity.Transaction;
import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.Enum.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
    List<Transaction> findAllByTransactionStatusAndTransactionType(
            TransactionStatus transactionStatus,
            TransactionType transactionType
    );
}
