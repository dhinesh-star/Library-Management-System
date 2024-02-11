package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Entity.Author;
import com.example.librarymanagementsystem.ResponseDTO.AuthorResponseDTO;

public class AuthorResponseDTOTransformer {
    public static AuthorResponseDTO authorResponseDTO(Author author){
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .authorId(author.getAuthorId())
                .authorName(author.getAuthorName())
                .authorEmailId(author.getAuthorEmailId())
                .noOfBooksIssued(author.getNoOfBooksIssued())
                .authorAge(author.getAuthorAge())
                .build();
        return authorResponseDTO;
    }
}
