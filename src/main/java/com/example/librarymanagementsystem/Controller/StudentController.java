package com.example.librarymanagementsystem.Controller;

import com.example.librarymanagementsystem.Entity.Student;
import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;
import com.example.librarymanagementsystem.ResponseDTO.MaxGenreResponseDTO;
import com.example.librarymanagementsystem.ResponseDTO.MsgAndStudentListDTO;
import com.example.librarymanagementsystem.ResponseDTO.StudentResponseDTO;
import com.example.librarymanagementsystem.Service.StudentService;
import com.example.librarymanagementsystem.Transformer.ErrorDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student){
        String response = studentService.addStudent(student);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity getStudent(@RequestParam("studentId") Integer studentId){
        try{
            StudentResponseDTO studentResponseDTO = studentService.getStudent(studentId);
            return new ResponseEntity(studentResponseDTO,HttpStatus.OK);
        }
        catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/changePhoneno")
    public ResponseEntity changeNumber(@RequestParam("phoneNo") String phoneNumber,
                                       @RequestParam("studentId") Integer studentId){
        try{
            StudentResponseDTO studentResponseDTO = studentService.changeNumber(phoneNumber, studentId);
            return new ResponseEntity<>(studentResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDefaultStudentList")
    public ResponseEntity getDefaultStudentList(){
        MsgAndStudentListDTO msgAndStudentListDTO = studentService.sentEmailOrGetDefaultStudentList(false);
        return new ResponseEntity<>(msgAndStudentListDTO,HttpStatus.OK);
    }

    @GetMapping("/getEmailToDefaultStudent")
    public ResponseEntity getEmailToDefaultStudent(){
        MsgAndStudentListDTO msgAndStudentListDTO = studentService.sentEmailOrGetDefaultStudentList(true);
        return new ResponseEntity<>(msgAndStudentListDTO,HttpStatus.OK);
    }

    @GetMapping("/mostPopularGenre")
    public ResponseEntity mostPopularGenre(){
        try {
            MaxGenreResponseDTO maxGenreResponseDTO = studentService.mostPopularGenre();
            return new ResponseEntity<>(maxGenreResponseDTO,HttpStatus.OK);
        }catch (Exception e){
            ErrorDTO errorDTO = ErrorDTOTransformer.frameResponse(e.getMessage());
            return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
        }
    }
}
