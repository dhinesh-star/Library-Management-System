package com.example.librarymanagementsystem.Repository;

import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.Enum.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT DISTINCT genre FROM book;",nativeQuery = true)
    List<Genre> getAllGenre();
}
