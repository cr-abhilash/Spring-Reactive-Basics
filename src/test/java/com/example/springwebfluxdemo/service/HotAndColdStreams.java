package com.example.springwebfluxdemo.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStreams {

    @Test
    public void coldStreamTest() {
        var numbers = Flux.range(1,10);
        numbers.subscribe(System.out::println);
        numbers.subscribe(System.out::println);
    }

    @Test
    public void hotStreamTest() throws InterruptedException {
        var numbers = Flux.range(1,10).delayElements(Duration.ofMillis(1000));

        ConnectableFlux<Integer> publisher = numbers.publish();

        publisher.connect();

        publisher.subscribe(integer -> System.out.println("S1"+" "+ integer));
        Thread.sleep(4000l);
        publisher.subscribe(integer -> System.out.println("S2"+" "+ integer));
        Thread.sleep(10000);
    }
}
