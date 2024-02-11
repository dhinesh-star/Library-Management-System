package com.example.librarymanagementsystem.Controller;

import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.RequestDTO.AddBookRequestDTO;
import com.example.librarymanagementsystem.ResponseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;
import com.example.librarymanagementsystem.Service.BookService;
import com.example.librarymanagementsystem.Transformer.ErrorDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;


    @PostMapping("/addBook")
    public ResponseEntity addBook(@RequestBody AddBookRequestDTO addBookRequestDTO){
        try{
            String response = bookService.addBook(addBookRequestDTO);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getBookById")
    public ResponseEntity getBookById(@RequestParam("bookId") Integer bookId){
        try{
            BookResponseDTO bookResponseDTO = bookService.getBookById(bookId);
            return new ResponseEntity<>(bookResponseDTO,HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/isBookWithGivenIdAvailable")
    public ResponseEntity isBookWithGivenIdAvailable(@RequestParam("bookId") Integer bookId){
        try{
            boolean response = bookService.isBookWithGivenIdAvailable(bookId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mostBookIssued")
    public ResponseEntity mostBookIssued(){
        try {
            BookResponseDTO bookResponseDTO = bookService.mostBookIssued();
            return new ResponseEntity<>(bookResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
}
