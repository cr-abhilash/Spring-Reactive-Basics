package com.example.springwebfluxdemo.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class MainBookServiceTest {

    private  BookService bookService = new BookService();
    private  ReviewService reviewService = new ReviewService();
    private MainBookService mainBookService = new MainBookService(bookService,reviewService);



    @Test
    void getBooks() {
        var books = mainBookService.getBooks();

        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book one",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book two",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book three",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviews().size());
                })
                .verifyComplete();
    }

    @Test
    void getBookById() {
        var books = mainBookService.getBookById(1);

        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book One",book.getBookInfo().getTitle());
                    assertEquals(2,book.getReviews().size());
                })
                .verifyComplete();
    }
}