package com.example.springwebfluxdemo.controller;

import com.example.springwebfluxdemo.dto.Response;
import com.example.springwebfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        return reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> findMultipicationTable(@PathVariable int input){
        return reactiveMathService.multiplicationTable(input);
    }

    @GetMapping(value="table-stream/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> findMultipicationTableStream(@PathVariable int input){
        return reactiveMathService.multiplicationTable(input);
    }

}
