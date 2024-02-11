package com.example.librarymanagementsystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgAndStudentListDTO {
    private String msg;
    private List<String> studentList;
}
