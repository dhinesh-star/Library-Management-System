package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.Entity.LibraryCard;
import com.example.librarymanagementsystem.Entity.Transaction;
import com.example.librarymanagementsystem.ResponseDTO.IssueAndReturnResponse;

public class IssueAndReturnResponseTransformer {
    public static IssueAndReturnResponse issueAndReturnResponse(LibraryCard libraryCard, Book book, Transaction transaction){
        IssueAndReturnResponse issueAndReturnResponse = IssueAndReturnResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType())
                .bookId(book.getBookId())
                .cardId(libraryCard.getLibraryId())
                .build();

        return issueAndReturnResponse;
    }
}
