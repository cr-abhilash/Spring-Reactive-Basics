package com.example.springwebfluxdemo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Service
public class FluxAndMonoMethods {

    //map method
    public Flux<String> fruitFluxMap() {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fruitFluxFilter(int number) {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .filter(s -> s.length() > number)
                .log();
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis( new Random().nextInt(1000)))
                .log();
    }

    public static void main(String[] args) {
           FluxAndMonoMethods fluxAndMonoMethods = new FluxAndMonoMethods();

           fluxAndMonoMethods.fruitsFluxFlatMapAsync().subscribe(System.out::println);

    }

    public Mono<List<String>> fruitsMonoMap(){
        return Mono.just("Mango")
                .flatMap(s->Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> fruitsFluxConcatMapAsync() {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .concatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis( new Random().nextInt(1000)))
                .log();
    }

    public Flux<String> fruitsMonoToFlux(){
        return Mono.just("Mango")
                .flatMapMany(s->Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxTransform(int number){
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s->s.length() > number);
        return Flux.fromIterable(List.of("mango","orange","banana"))
                //.filter(s -> s.length() > number)
                .transform(filterData)
                .log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number){
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s->s.length() > number);
        return Flux.fromIterable(List.of("mango","orange","banana"))
                //.filter(s -> s.length() > number)
                .transform(filterData)
                .defaultIfEmpty("Default")
                .log();
    }
    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number){
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s->s.length() > number);
        return Flux.fromIterable(List.of("mango","orange","banana"))
                //.filter(s -> s.length() > number)
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pineapple","Jack Fruit"))
                .transform(filterData)
                .log();
    }

    public Flux<String> fruitFluxConcat() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomoto","Lemon");

        return Flux.concat(fruits,veggies);

    }

    public Flux<String> fruitFluxConcatWith() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomoto","Lemon");

        return fruits.concatWith(veggies);

    }

    public Flux<String> fruitFluxMerge() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomoto","Lemon")
                .delayElements(Duration.ofMillis(150));

        return Flux.merge(fruits,veggies);

    }

    public Flux<String> fruitFluxMergeWith() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(150));
        var veggies = Flux.just("Tomoto","Lemon")
                .delayElements(Duration.ofMillis(50));

        return fruits.mergeWith(veggies);

    }

    public Flux<String> fruitFluxMergeWithSequential() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(150));
        var veggies = Flux.just("Tomoto","Lemon")
                .delayElements(Duration.ofMillis(50));

        return Flux.mergeSequential(fruits,veggies);

    }


    public Flux<String> fruitsFluxZip(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return Flux.zip(fruits,veggies,(first,second) -> first+second).log();
    }

    public Flux<String> fruitsFluxZipTuple(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");
        var moreVeggies = Flux.just("Pototo","Beans");
        return Flux.zip(fruits,veggies,moreVeggies)
                .map(objects->objects.getT1() + objects.getT2()+objects.getT3()).log();
    }

    public Flux<String> fruitFluxFilterDoOn(int number) {
        return Flux.fromIterable(List.of("mango","orange","banana"))
                .filter(s -> s.length() > number)
                .doOnNext(System.out::println)
                .doOnSubscribe(subscription -> {
                    System.out.println("subscription = " + subscription);
                })
                .doOnComplete(()-> System.out.println("Completed!!"))
                .log();
    }

    public Flux<String> fruitsFluxOnErrorReturn(){
        return Flux.just("Apple","Mango")
                .concatWith(Flux.error(
                        new RuntimeException("Error Occurred")
                ))
                .onErrorReturn("Orange");
    }

    public Flux<String> fruitsFluxOnErrorContinue(){
        return Flux.just("Apple","Mango","Orange")
                .map(s->{
                    if(s.equalsIgnoreCase("mango")){
                        throw new RuntimeException("Exception Occurred");
                    }
                    return s;
                })
                .onErrorContinue((e,f)->{
                    System.out.println(e + " "+ f);
                });
    }

    public Flux<String> fruitsFluxOnErrorMap(){
        return Flux.just("Apple","Mango","Orange")
                .map(s->{
                    if(s.equalsIgnoreCase("mango")){
                        throw new RuntimeException("Exception Occurred");
                    }
                    return s;
                })
                .onErrorMap(throwable -> {
                    System.out.println(throwable);
                    return new IllegalStateException("From OnError map");
                });
    }

    public Flux<String> fruitsFluxDoOnError(){
        return Flux.just("Apple","Mango","Orange")
                .map(s->{
                    if(s.equalsIgnoreCase("mango")){
                        throw new RuntimeException("Exception Occurred");
                    }
                    return s;
                })
                .doOnError(throwable -> {
                    System.out.println(throwable);
                });
    }

}
