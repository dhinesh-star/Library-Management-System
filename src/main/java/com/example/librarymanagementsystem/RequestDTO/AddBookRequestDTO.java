package com.example.librarymanagementsystem.RequestDTO;

import com.example.librarymanagementsystem.Enum.Genre;
import lombok.Data;

@Data
public class AddBookRequestDTO {
    private Integer authorId;
    private String bookName;
    private Genre genre;
    private int noOfPages;
    private int price;
    private int noOfBooksAvailable;
}
