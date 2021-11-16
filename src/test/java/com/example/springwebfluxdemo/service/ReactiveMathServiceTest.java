package com.example.springwebfluxdemo.service;



import com.example.springwebfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveMathServiceTest {
    ReactiveMathService reactiveMathService = new ReactiveMathService();
    @Test
    void findSquare() {
        var varFlux = reactiveMathService.findSquare(5);
        Response response = new Response(25);
        StepVerifier.create(varFlux)
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void multiplicationTable() {
    }
}