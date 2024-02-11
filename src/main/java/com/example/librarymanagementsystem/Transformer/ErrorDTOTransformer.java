package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.ResponseDTO.ErrorDTO;

public class ErrorDTOTransformer {
    public static ErrorDTO frameResponse(String msg){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .msg(msg)
                .build();

        return errorDTO;
    }
}
