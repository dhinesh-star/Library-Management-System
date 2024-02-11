package com.example.librarymanagementsystem.Repository;

import com.example.librarymanagementsystem.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByAuthorEmailId(String emailId);
}
