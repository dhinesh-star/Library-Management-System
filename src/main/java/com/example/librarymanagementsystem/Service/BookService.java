package com.example.librarymanagementsystem.Service;

import com.example.librarymanagementsystem.Entity.Author;
import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.Entity.Transaction;
import com.example.librarymanagementsystem.Repository.AuthorRepository;
import com.example.librarymanagementsystem.Repository.BookRepository;
import com.example.librarymanagementsystem.Repository.TransactionRepository;
import com.example.librarymanagementsystem.RequestDTO.AddBookRequestDTO;
import com.example.librarymanagementsystem.ResponseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.Transformer.BookResponseDTOTransfomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public String addBook(AddBookRequestDTO addBookRequestDTO) throws Exception{
        Optional<Author> authorOptional = authorRepository.findById(addBookRequestDTO.getAuthorId());
        if(authorOptional.isEmpty()){
            throw new Exception("Author with the given Id not found");
        }
        Author author = authorOptional.get();
        Book book = Book.builder()
                .bookName(addBookRequestDTO.getBookName())
                .genre(addBookRequestDTO.getGenre())
                .noOfPages(addBookRequestDTO.getNoOfPages())
                .price(addBookRequestDTO.getPrice())
                .noOfBooksAvailable(addBookRequestDTO.getNoOfBooksAvailable())
                .build();

        //Bidirectional Mapping
        book.setAuthor(author);
        author.getBookList().add(book);
        author.setNoOfBooksIssued(author.getNoOfBooksIssued()+1);

        //Saving the parent entity so eventually the child entity is saved
        authorRepository.save(author);

        return "Book with name "+book.getBookName()+" saved to DB";
    }
    public BookResponseDTO getBookById(Integer bookId) throws Exception{
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new Exception("No Book found with the given Id");
        }
        Book book = bookOptional.get();

        BookResponseDTO bookResponseDTO = BookResponseDTOTransfomer.frameBookResponseDTO(book);
        return bookResponseDTO;
    }
    public boolean isBookWithGivenIdAvailable(Integer bookId) throws Exception{
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new Exception("No Book found with the given Id");
        }
        Book book = bookOptional.get();

        if(book.getNoOfBooksAvailable()>0){
            return true;
        }
        return false;
    }

    public BookResponseDTO mostBookIssued() throws Exception{
        List<Book> bookList = bookRepository.findAll();
        List<Transaction> transactionList = transactionRepository.findAll();
        int maxBookCount = 0, maxBookId = -1;
        for(Book book:bookList){
            int currBookCount = 0;
            for(Transaction transaction:transactionList){
                int currTransBookId = transaction.getBook().getBookId();
                if(book.getBookId()==currTransBookId) currBookCount++;
            }
            if(maxBookCount<currBookCount){
                maxBookCount = currBookCount;
                maxBookId = book.getBookId();
            }
        }

        BookResponseDTO bookResponseDTO = getBookById(maxBookId);
        return bookResponseDTO;
    }
}
