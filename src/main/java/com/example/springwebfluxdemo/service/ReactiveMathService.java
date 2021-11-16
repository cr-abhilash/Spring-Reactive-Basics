package com.example.springwebfluxdemo.service;

import com.example.springwebfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(()->input * input)
                .map(Response::new).log();
    }


    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("reactive math service processing "+ i))
                .map(i -> new Response(i*input)).log();
    }

    public static void main(String[] args) {
        ReactiveMathService r = new ReactiveMathService();

        //To subscribe to flux
        r.findSquare(5).subscribe(System.out::println);

        r.multiplicationTable(5).subscribe(System.out::println);
    }
}
