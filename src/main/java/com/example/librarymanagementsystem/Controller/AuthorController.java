package com.example.librarymanagementsystem.Controller;

import com.example.librarymanagementsystem.Entity.Author;
import com.example.librarymanagementsystem.RequestDTO.AddAuthorRequestDTO;
import com.example.librarymanagementsystem.ResponseDTO.AuthorResponseDTO;
import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;
import com.example.librarymanagementsystem.Service.AuthorService;
import com.example.librarymanagementsystem.Transformer.ErrorDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @PostMapping("/addAuthor")
    public ResponseEntity<String> addAuthor(@RequestBody AddAuthorRequestDTO addAuthorRequestDTO){
        String response = authorService.addAuthor(addAuthorRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAuthorByEmailId")
    public ResponseEntity findByEmailId(@RequestParam("emailId") String emailId){
        try{
            AuthorResponseDTO authorResponseDTO = authorService.findByEmailId(emailId);
            return new ResponseEntity<>(authorResponseDTO,HttpStatus.OK);
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAuthorById")
    public ResponseEntity findAuthorById(@RequestParam("authorId") Integer authorId){
        try{
            AuthorResponseDTO authorResponseDTO = authorService.findById(authorId);
            return new ResponseEntity<>(authorResponseDTO,HttpStatus.OK);
        } catch (Exception e) {
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
}
