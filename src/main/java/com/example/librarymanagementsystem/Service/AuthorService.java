package com.example.librarymanagementsystem.Service;

import com.example.librarymanagementsystem.Entity.Author;
import com.example.librarymanagementsystem.Repository.AuthorRepository;
import com.example.librarymanagementsystem.RequestDTO.AddAuthorRequestDTO;
import com.example.librarymanagementsystem.ResponseDTO.AuthorResponseDTO;
import com.example.librarymanagementsystem.Transformer.AddAuthorRequestDTOTransfomer;
import com.example.librarymanagementsystem.Transformer.AuthorResponseDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JavaMailSender mailSender;
    public AuthorResponseDTO findByEmailId(String emailId) throws Exception{
        Author author = authorRepository.findByAuthorEmailId(emailId);
        if(author==null){
            throw new Exception("Author with given emailId not found");
        }
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTOTransformer.authorResponseDTO(author);
        return authorResponseDTO;
    }

    public AuthorResponseDTO findById(Integer authorId) throws Exception{
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if(authorOptional.isEmpty()){
            throw new Exception("Author with given Id not found");
        }
        Author author = authorOptional.get();
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTOTransformer.authorResponseDTO(author);
        return authorResponseDTO;
    }

    public String addAuthor(AddAuthorRequestDTO addAuthorRequestDTO){
        Author author = AddAuthorRequestDTOTransfomer.authorTransformer(addAuthorRequestDTO);

        //Sending mail
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Greeting Message");
        msg.setFrom("kishore19102001@gmail.com");
        msg.setTo(addAuthorRequestDTO.getAuthorEmailId());
        msg.setText("Your added to portal "+addAuthorRequestDTO.getAuthorName());

        mailSender.send(msg);

        Author newAuthorAddedToDB = authorRepository.save(author);
        return "Author with authorId added to DB "+newAuthorAddedToDB.getAuthorId();
    }
}
