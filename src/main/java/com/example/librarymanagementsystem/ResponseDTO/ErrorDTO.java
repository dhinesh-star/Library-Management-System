package com.example.librarymanagementsystem.ResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String msg;
}
