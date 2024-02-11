package com.example.librarymanagementsystem.Service;

import com.example.librarymanagementsystem.Entity.Student;
import com.example.librarymanagementsystem.Entity.Transaction;
import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.Enum.TransactionType;
import com.example.librarymanagementsystem.Repository.BookRepository;
import com.example.librarymanagementsystem.Repository.StudentRepository;
import com.example.librarymanagementsystem.Repository.TransactionRepository;
import com.example.librarymanagementsystem.ResponseDTO.MaxGenreResponseDTO;
import com.example.librarymanagementsystem.ResponseDTO.MsgAndStudentListDTO;
import com.example.librarymanagementsystem.ResponseDTO.StudentResponseDTO;
import com.example.librarymanagementsystem.Transformer.MaxGenreResponseDTOTransformer;
import com.example.librarymanagementsystem.Transformer.MsgAndStudentListDTOTransformer;
import com.example.librarymanagementsystem.Transformer.StudentResponseDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BookRepository bookRepository;

    public String addStudent(Student student){
        Student studentAddedToDB = studentRepository.save(student);
        return "New Student Added To DB "+studentAddedToDB.getStudentId();
    }

    public StudentResponseDTO getStudent(Integer studentId) throws Exception{
        Optional<Student> optionalStudentList = studentRepository.findById(studentId);
        if(optionalStudentList==null){
            throw new Exception("Student not found in DB");
        }
        Student student = optionalStudentList.get();

        StudentResponseDTO studentResponseDTO = StudentResponseDTOTransformer.studentResponseDTO(student);

        return studentResponseDTO;
    }

    public StudentResponseDTO changeNumber(String phoneNumber, Integer studentId) throws Exception{
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isEmpty()){
            throw new Exception("Student with the given Id not found");
        }
        Student student = optionalStudent.get();
        student.setPhoneNo(phoneNumber);

        Student newStudentAddedToDB = studentRepository.save(student);

        StudentResponseDTO studentResponseDTO = StudentResponseDTOTransformer.studentResponseDTO(newStudentAddedToDB);
        return studentResponseDTO;
    }

    public MsgAndStudentListDTO sentEmailOrGetDefaultStudentList(boolean sentEmail){
        List<Transaction> transactionList = transactionRepository.findAllByTransactionStatusAndTransactionType(
                TransactionStatus.SUCCESS,
                TransactionType.ISSUED
        );
        List<String> studentName = new ArrayList<>();
        for(Transaction transaction:transactionList){
            LocalDate currentDate = LocalDate.now();
            int countDays = TransactionService.countNoDaysBetweenDates(transaction.getDate(),currentDate);
            if(countDays>=15){
                studentName.add(transaction.getLibraryCard().getStudent().getName());
                if(sentEmail==true) {
                    String emailId = transaction.getLibraryCard().getStudent().getEmailId();
                    SimpleMailMessage msg = new SimpleMailMessage();
                    msg.setSubject("Return Book");
                    msg.setText("Please return the all book who dues are over 15 days");
                    msg.setTo(emailId);
                    msg.setFrom("kishore19102001@gmail.com");
                    javaMailSender.send(msg);
                }
            }
        }

        String msg = "Default Student List";
        if(sentEmail==true){
            msg = "Email Sent to all the the list of students";
        }

        MsgAndStudentListDTO msgAndStudentListDTO = MsgAndStudentListDTOTransformer.frameMsgAndStudentListDTO(studentName,msg);

        return msgAndStudentListDTO;
    }

    public MaxGenreResponseDTO mostPopularGenre() throws Exception{
        List<Genre> genreList = bookRepository.getAllGenre();
        List<Transaction> transactionList = transactionRepository.findAll();

        int maxGenreCount = 0;
        Genre maxGenre = null;
        for(Genre genre:genreList){
            int currGenreCount = 0;
            for(Transaction transaction:transactionList){
                Genre currGenre = transaction.getBook().getGenre();
                if(currGenre==genre) currGenreCount++;
            }
            if(maxGenreCount<currGenreCount){
                maxGenreCount = currGenreCount;
                maxGenre = genre;
            }
        }
        if(maxGenre==null){
            throw new Exception("No transaction available in transaction table");
        }
        MaxGenreResponseDTO maxGenreResponseDTO = MaxGenreResponseDTOTransformer
                .frameMaxGenreResponseDTO(maxGenre,maxGenreCount);
        return maxGenreResponseDTO;
    }
}
