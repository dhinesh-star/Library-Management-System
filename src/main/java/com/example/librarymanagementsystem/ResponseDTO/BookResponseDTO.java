package com.example.librarymanagementsystem.ResponseDTO;

import com.example.librarymanagementsystem.Enum.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Integer bookId;
    private String bookName;
    private Genre genre;
    private int noOfPages;
    private int price;
    private int noOfBooksAvailable;
    private String authorName;
}
