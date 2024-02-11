package com.example.librarymanagementsystem.Service;

import com.example.librarymanagementsystem.Entity.LibraryCard;
import com.example.librarymanagementsystem.Entity.Student;
import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.Repository.CardRepository;
import com.example.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StudentRepository studentRepository;

    public String addNewCard(){
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setCardStatus(CardStatus.NEW);
        libraryCard.setNoOfBooksIssued(0);
        LibraryCard libraryCardAddedToDB = cardRepository.save(libraryCard);
        return "New Library Card added to DB with card Id "+libraryCardAddedToDB.getLibraryId();
    }

    public String associateStudentAndCard(Integer studentId, Integer cardId) throws Exception{
        Optional<LibraryCard> cardList = cardRepository.findById(cardId);
        if(cardList==null){
            throw new Exception("Card With given Id not found");
        }
        LibraryCard libraryCard = cardList.get();
        Optional<Student> studentList = studentRepository.findById(studentId);
        if(studentList==null){
            throw new Exception("Student with given Id not found");
        }
        Student student = studentList.get();

        libraryCard.setStudent(student);
        libraryCard.setCardStatus(CardStatus.ACTIVE);

        cardRepository.save(libraryCard);

        return "Student with StudentId "+student.getStudentId()+" and Card with CardId "+libraryCard.getLibraryId()+
                " is associated";
    }
    public LibraryCard getCard(Integer cardId) throws Exception{
        Optional<LibraryCard> cardOptional = cardRepository.findById((cardId));
        if(cardOptional.isEmpty()==true) throw new Exception("Card with given Id not found");


        return cardOptional.get();
    }
}
