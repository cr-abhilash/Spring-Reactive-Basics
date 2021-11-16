package com.example.springwebfluxdemo.service;

import com.example.springwebfluxdemo.Domain.Book;
import com.example.springwebfluxdemo.Domain.Review;
import com.example.springwebfluxdemo.Exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Slf4j
public class MainBookService {

    private BookService bookService;
    private ReviewService reviewService;

    public MainBookService(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }


    public Flux<Book> getBooks(){
        var allBooks = bookService.getBooks();
        return allBooks.flatMap(bookInfo -> {
            Mono<List<Review>>  reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
            return reviews.map(review -> new Book(bookInfo,review));
        }).onErrorMap(throwable -> {
            log.error("expection:" +throwable);
            return new BookException("Exception occurred while fetching books");
        }).log();
    }

    public Flux<Book> getBooksRetry(){
        var allBooks = bookService.getBooks();
        return allBooks.flatMap(bookInfo -> {
            Mono<List<Review>>  reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
            return reviews.map(review -> new Book(bookInfo,review));
        }).onErrorMap(throwable -> {
            log.error("expection:" +throwable);
            return new BookException("Exception occurred while fetching books");
        })
                .retry(3).log();
    }

    public Flux<Book> getBooksRetryWhen(){
        var allBooks = bookService.getBooks();
        var retrySpec = Retry.backoff(
                3, Duration.ofMillis(1000)
        ).filter(throwable -> throwable instanceof BookException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
            Exceptions.propagate(retrySignal.failure())
        );
        return allBooks.flatMap(bookInfo -> {
                    Mono<List<Review>>  reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review -> new Book(bookInfo,review));
                }).onErrorMap(throwable -> {
                    log.error("expection:" +throwable);
                    return new BookException("Exception occurred while fetching books");
                })
                .retryWhen(retrySpec).log();
    }

    public Mono<Book> getBookById(long bookId){
        var book = bookService.getBookById(bookId);
        var review = reviewService.getReviews(bookId).collectList();
        return book.zipWith(review,(b,r)-> new Book(b,r));
    }
}
