package com.example.springwebfluxdemo.service;

import com.example.springwebfluxdemo.Domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookService {

    public Flux<BookInfo> getBooks(){
        var books = List.of(
                new BookInfo(1,"Book one","Author one","1"),
                new BookInfo(2,"Book two","Author two","2"),
                new BookInfo(3,"Book three","Author three","3")
        );
        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId){
        var book =  new BookInfo(bookId,"Book One","Author one","1");
        return Mono.just(book);
    }
}
