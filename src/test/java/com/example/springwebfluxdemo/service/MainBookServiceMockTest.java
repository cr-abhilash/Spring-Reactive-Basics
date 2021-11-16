package com.example.springwebfluxdemo.service;

import com.example.springwebfluxdemo.Exception.BookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainBookServiceMockTest {

    @Mock
    private ReviewService reviewService;
    @Mock
    private BookService bookService;

    @InjectMocks
    private MainBookService mainBookService;



    @Test
    void getBooksMock() {
        Mockito.when(bookService.getBooks())
                .thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong())).thenCallRealMethod();
        var books = mainBookService.getBooks();

        StepVerifier.create(books).expectNextCount(3).verifyComplete();
    }

    @Test
    void getBooksMockOnError() {
        Mockito.when(bookService.getBooks())
                .thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("exception using test"));
        var books = mainBookService.getBooks();

        StepVerifier.create(books).
                expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetry() {
        Mockito.when(bookService.getBooks())
                .thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("exception using test"));
        var books = mainBookService.getBooksRetry();

        StepVerifier.create(books).
                expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetryWhen() {
        Mockito.when(bookService.getBooks())
                .thenCallRealMethod();
        Mockito.when(reviewService.getReviews(Mockito.anyLong()))
                .thenThrow(new IllegalStateException("exception using test"));
        var books = mainBookService.getBooksRetryWhen();

        StepVerifier.create(books).
                expectError(BookException.class)
                .verify();
    }
}