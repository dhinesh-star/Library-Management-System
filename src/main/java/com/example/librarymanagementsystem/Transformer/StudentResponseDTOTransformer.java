package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Entity.Student;
import com.example.librarymanagementsystem.ResponseDTO.StudentResponseDTO;

public class StudentResponseDTOTransformer {
    public static StudentResponseDTO studentResponseDTO(Student newStudentAddedToDB){
        StudentResponseDTO studentResponseDTO = StudentResponseDTO.builder()
                .studentId(newStudentAddedToDB.getStudentId())
                .studentId(newStudentAddedToDB.getStudentId())
                .name(newStudentAddedToDB.getName())
                .phoneNo(newStudentAddedToDB.getPhoneNo())
                .branch(newStudentAddedToDB.getBranch())
                .cgpa(newStudentAddedToDB.getCgpa())
                .emailId(newStudentAddedToDB.getEmailId())
                .msg("Sucess")
                .build();
        return studentResponseDTO;
    }
}
