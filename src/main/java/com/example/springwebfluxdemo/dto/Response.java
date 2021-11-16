package com.example.springwebfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Response {

    private int data = new Date().getHours();
    private int output;

    public Response(int output){
        this.output=output;
    }
}
