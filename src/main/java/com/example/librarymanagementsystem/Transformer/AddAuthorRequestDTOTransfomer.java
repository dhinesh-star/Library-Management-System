package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Entity.Author;
import com.example.librarymanagementsystem.RequestDTO.AddAuthorRequestDTO;

public class AddAuthorRequestDTOTransfomer {
    public static Author authorTransformer(AddAuthorRequestDTO addAuthorRequestDTO){
        Author author = Author.builder()
                .authorName(addAuthorRequestDTO.getAuthorName())
                .authorEmailId(addAuthorRequestDTO.getAuthorEmailId())
                .authorAge(addAuthorRequestDTO.getAuthorAge())
                .noOfBooksIssued(0)
                .build();
        return author;
    }
}
