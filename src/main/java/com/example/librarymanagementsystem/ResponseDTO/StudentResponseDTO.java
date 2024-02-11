package com.example.librarymanagementsystem.ResponseDTO;

import com.example.librarymanagementsystem.Entity.LibraryCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StudentResponseDTO {
    private Integer studentId;
    private String name;
    private String branch;
    private double cgpa;
    private String phoneNo;
    private String msg;
    private String emailId;
}
