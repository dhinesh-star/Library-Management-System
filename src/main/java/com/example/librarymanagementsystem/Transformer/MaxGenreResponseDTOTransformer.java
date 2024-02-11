package com.example.librarymanagementsystem.Transformer;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.ResponseDTO.MaxGenreResponseDTO;

public class MaxGenreResponseDTOTransformer {
    public static MaxGenreResponseDTO frameMaxGenreResponseDTO(Genre genre, int genreCount){
        MaxGenreResponseDTO maxGenreResponseDTO = MaxGenreResponseDTO.builder()
                .genre(genre)
                .count(genreCount)
                .build();
        return maxGenreResponseDTO;
    }
}
