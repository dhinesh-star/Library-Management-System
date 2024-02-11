package com.example.librarymanagementsystem.Controller;

import com.example.librarymanagementsystem.RequestDTO.IssueBookDTO;
import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;
import com.example.librarymanagementsystem.ResponseDTO.IssueAndReturnResponse;
import com.example.librarymanagementsystem.Service.TransactionService;
import com.example.librarymanagementsystem.Transformer.ErrorDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issueBook")
    public ResponseEntity issueBook(@RequestParam("cardId") Integer cardId,@RequestParam("bookId") Integer bookId){
        try{
            IssueAndReturnResponse issueAndReturnResponse = transactionService.issueBook(cardId,bookId);
            return new ResponseEntity<>(issueAndReturnResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/returnBook")
    public ResponseEntity returnBook(@RequestParam("cardId") Integer cardId,@RequestParam("bookId") Integer bookId){
        try{
            IssueAndReturnResponse issueAndReturnResponse = transactionService.returnBook(bookId, cardId);
            return new ResponseEntity<>(issueAndReturnResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/totalFineCollected")
    public ResponseEntity totalFineCollected(){
        int totalAmt = transactionService.totalFineCollected();
        return new ResponseEntity<>(totalAmt,HttpStatus.OK);
    }
}
