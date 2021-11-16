package com.example.springwebfluxdemo.service;

import com.example.springwebfluxdemo.Domain.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {
    public Flux<Review> getReviews(long bookId){
        var reviewList = List.of(
                new Review(1,bookId,9.1,"Good Book"),
                new Review(2,bookId,9.1,"Good Book")
        );
        return Flux.fromIterable(reviewList);
    }
}
