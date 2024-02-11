package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.ResponseDTO.BookResponseDTO;

public class BookResponseDTOTransfomer {
    public static BookResponseDTO frameBookResponseDTO(Book book){
        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .bookId(book.getBookId())
                .bookName(book.getBookName())
                .genre(book.getGenre())
                .noOfPages(book.getNoOfPages())
                .price(book.getPrice())
                .noOfBooksAvailable(book.getNoOfBooksAvailable())
                .authorName(book.getAuthor().getAuthorName())
                .build();

        return bookResponseDTO;
    }
}
