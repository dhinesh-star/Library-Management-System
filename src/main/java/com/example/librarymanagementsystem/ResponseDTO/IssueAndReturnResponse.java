package com.example.librarymanagementsystem.ResponseDTO;

import com.example.librarymanagementsystem.Enum.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueAndReturnResponse {
    private String transactionId;
    private Integer bookId;
    private Integer cardId;
    private TransactionType transactionType;
    private Integer fineAmount;
}
