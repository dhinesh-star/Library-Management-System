package com.example.librarymanagementsystem.ResponseDTO;

import com.example.librarymanagementsystem.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaxGenreResponseDTO {
    private Genre genre;
    private Integer count;
}
