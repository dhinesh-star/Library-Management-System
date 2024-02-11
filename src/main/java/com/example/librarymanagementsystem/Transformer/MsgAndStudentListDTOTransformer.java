package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.ResponseDTO.MsgAndStudentListDTO;

import java.util.List;

public class MsgAndStudentListDTOTransformer {
    public static MsgAndStudentListDTO frameMsgAndStudentListDTO(List<String> studentName, String msg){
        MsgAndStudentListDTO msgAndStudentListDTO = MsgAndStudentListDTO.builder()
                .studentList(studentName)
                .msg(msg)
                .build();

        return msgAndStudentListDTO;
    }
}
