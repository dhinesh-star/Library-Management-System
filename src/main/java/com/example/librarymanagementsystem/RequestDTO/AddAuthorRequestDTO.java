package com.example.librarymanagementsystem.RequestDTO;

import lombok.Data;

@Data
public class AddAuthorRequestDTO {
    private String authorName;
    private String authorEmailId;
    private int authorAge;
}
