package com.example.springwebfluxdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoMethodsTest {


    FluxAndMonoMethods fluxAndMonoMethods = new FluxAndMonoMethods();

    @Test
    void fruitFluxMap() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxMap();

        StepVerifier.create(fruitsFlux)
                .expectNext("MANGO","ORANGE","BANANA")
                .verifyComplete();
    }

    @Test
    void fruitFluxFilter() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxFilter(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("orange","banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxFlatMapAsync();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsMonoMap(){
        var fruitsFlux = fluxAndMonoMethods.fruitsMonoMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMapAsync() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxConcatMapAsync();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsMonoToFlux() {
        var fruitsFlux = fluxAndMonoMethods.fruitsMonoToFlux();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxTransform(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("orange","banana")
                .verifyComplete();
     }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {

        var fruitsFlux = fluxAndMonoMethods.fruitsFluxTransformDefaultIfEmpty(10);

        StepVerifier.create(fruitsFlux)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxTransformSwitchIfEmpty(9);

        StepVerifier.create(fruitsFlux)
                .expectNext("Jack Fruit")
                .verifyComplete();
    }

    @Test
    void fruitFluxConcat() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxConcat();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fruitFluxConcatWith() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxConcatWith();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fruitFluxMerge() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxMerge().log();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fruitFluxMergeWith() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxMergeWith().log();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fruitFluxMergeSequential() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxMergeWithSequential().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Orange","Tomato","Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZip() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxZip();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato","OrangeLemon")
                .verifyComplete();
    }


    @Test
    void fruitsFluxZipTuple() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxZipTuple();

        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomatoPototo","OrangeLemonBeans")
                .verifyComplete();
    }

    @Test
    void fruitFluxFilterDoOn() {
        var fruitsFlux = fluxAndMonoMethods.fruitFluxFilterDoOn(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("orange","banana")
                .verifyComplete();
    }

    @Test
    void fruitFluxOnError() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxOnErrorReturn();

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple","Mango","Orange")
                .verifyComplete();
    }

    @Test
    void fruitFluxOnContinue() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxOnErrorContinue();

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple","Orange")
                .verifyComplete();
    }

    @Test
    void fruitFluxOnMap() {
        var fruitsFlux = fluxAndMonoMethods.fruitsFluxOnErrorMap();

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple")
                .expectError(IllegalStateException.class)
                .verify();
    }
}
