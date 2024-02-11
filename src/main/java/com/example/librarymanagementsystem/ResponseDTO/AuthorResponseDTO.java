package com.example.librarymanagementsystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorResponseDTO {
    private Integer authorId;
    private String authorName;
    private String authorEmailId;
    private int noOfBooksIssued;
    private int authorAge;
}
