package com.example.librarymanagementsystem.Service;

import com.example.librarymanagementsystem.Entity.Book;
import com.example.librarymanagementsystem.Entity.LibraryCard;
import com.example.librarymanagementsystem.Entity.Transaction;
import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.Enum.TransactionType;
import com.example.librarymanagementsystem.Repository.BookRepository;
import com.example.librarymanagementsystem.Repository.CardRepository;
import com.example.librarymanagementsystem.Repository.TransactionRepository;
import com.example.librarymanagementsystem.RequestDTO.IssueBookDTO;
import com.example.librarymanagementsystem.ResponseDTO.IssueAndReturnResponse;
import com.example.librarymanagementsystem.Transformer.IssueAndReturnResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BookRepository bookRepository;

    public IssueAndReturnResponse issueBook(Integer cardId, Integer bookId) throws Exception{
        Transaction transaction = Transaction.builder()
                .transactionStatus(TransactionStatus.ONGOING)
                .build();
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transactionRepository.save(transaction);
            throw new Exception("Book with the given Id not found");
        }
        Book book = bookOptional.get();

        if(book.getNoOfBooksAvailable()<=0){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transactionRepository.save(transaction);
            throw new Exception("Book with the given Id is not available right now");
        }

        Optional<LibraryCard> libraryCardOptional = cardRepository.findById(cardId);
        if(libraryCardOptional.isEmpty()){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transactionRepository.save(transaction);
            throw new Exception("Library Card with the given Id not found");
        }

        LibraryCard libraryCard = libraryCardOptional.get();

        if(libraryCard.getNoOfBooksIssued()>LibraryCard.MAX_NO_OF_ALLOWED_BOOK){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
            transactionRepository.save(transaction);
            throw new Exception("Max book limit reached return to get new books");
        }

        transaction.setTransactionType(TransactionType.ISSUED);
        transaction.setLibraryCard(libraryCard);
        transaction.setBook(book);
        transaction.setFineAmount(0);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        //Bidirectional part
        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()+1);
        libraryCard.getTransactionList().add(transaction);

        book.setNoOfBooksAvailable(book.getNoOfBooksAvailable()-1);
        book.getTransactionList().add(transaction);

        Transaction issueBookTransaction = transactionRepository.save(transaction);

        IssueAndReturnResponse issueAndReturnResponse = IssueAndReturnResponseTransformer.issueAndReturnResponse(libraryCard,book,issueBookTransaction);
        issueAndReturnResponse.setFineAmount(0);

        return issueAndReturnResponse;
    }

    public IssueAndReturnResponse returnBook(Integer bookId, Integer cardId) throws Exception{
        Optional<LibraryCard> libraryCardOptional = cardRepository.findById(cardId);
        if(libraryCardOptional.isEmpty()){
            throw new Exception("Student with the given Id not found");
        }
        LibraryCard libraryCard = libraryCardOptional.get();

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()){
            throw new Exception("Book with given Id not found");
        }
        Book book = bookOptional.get();

        //Find whether the book is associated to the student or not
        boolean flag=false;
        Transaction currTransaction=null;

        List<Transaction> transactionList = transactionRepository.findAllByTransactionStatusAndTransactionType(TransactionStatus.SUCCESS, TransactionType.ISSUED);
        for(Transaction transaction:transactionList){
            Integer currentBookId = transaction.getBook().getBookId();
            Integer currentCardId = transaction.getLibraryCard().getLibraryId();

            if(Objects.equals(currentBookId, bookId) && Objects.equals(currentCardId, cardId)){
                flag=true;
                currTransaction=transaction;
                break;
            }
        }
        if(flag==false){
            throw new Exception("Student did not get this book");
        }
        //Calculating the fine amount
        int fineAmt = fineAmount(currTransaction.getDate());

        currTransaction.setTransactionType(TransactionType.RETURN);
        currTransaction.setFineAmount(fineAmt);

        IssueAndReturnResponse issueAndReturnResponse = IssueAndReturnResponseTransformer.issueAndReturnResponse(libraryCard,book,currTransaction);
        issueAndReturnResponse.setFineAmount(fineAmt);

        libraryCard.setNoOfBooksIssued(libraryCard.getNoOfBooksIssued()-1);
        book.setNoOfBooksAvailable(book.getNoOfBooksAvailable()+1);

        transactionRepository.save(currTransaction);

        return issueAndReturnResponse;
    }
    public static int countNoDaysBetweenDates(LocalDate date1, LocalDate date2){
        long noOfDays = date2.toEpochDay() - date1.toEpochDay();
        return (int)noOfDays;
    }
    public int fineAmount(LocalDate bookIssuedDate){
        LocalDate currentDate = LocalDate.now();
        int noOfDays = countNoDaysBetweenDates(bookIssuedDate,currentDate);

        if(noOfDays<=10) return 0;

        else if(noOfDays>10 && noOfDays<=30){
            double fineAmt = noOfDays-10*0.5;
            return (int)Math.ceil(fineAmt);
        }

        double fineAmt = 5 + 5*(noOfDays-30);
        return (int)Math.ceil(fineAmt);
    }

    public int totalFineCollected(){
        int totalFine = 0;
        List<Transaction> transactionList = transactionRepository.findAllByTransactionStatusAndTransactionType(TransactionStatus.SUCCESS,TransactionType.RETURN);
        for(Transaction transaction:transactionList){
            totalFine +=transaction.getFineAmount();
        }
        return totalFine;
    }
}
