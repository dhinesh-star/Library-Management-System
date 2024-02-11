package com.example.librarymanagementsystem.Controller;

import com.example.librarymanagementsystem.Entity.LibraryCard;
import com.example.librarymanagementsystem.Entity.Student;
import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;
import com.example.librarymanagementsystem.Service.CardService;
import com.example.librarymanagementsystem.Transformer.ErrorDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping("/addNewCard")
    public ResponseEntity<String> addNewCard(){
        String response = cardService.addNewCard();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/associateStudentAndCard")
    public ResponseEntity associateStudentAndCard(@RequestParam("studentId") Integer studentId,
                                                  @RequestParam("cardId") Integer cardId){
        try {
            String response = cardService.associateStudentAndCard(studentId,cardId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity getCard(@RequestParam("cardId") Integer cardId){
        try {
            LibraryCard libraryCard = cardService.getCard(cardId);
            return new ResponseEntity<>(libraryCard,HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
}
