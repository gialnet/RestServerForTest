package com.vivaldispring.restserverfortest.rest_controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class RestControllerForTest {

    Map<String, Object> response = new HashMap<>();
    @GetMapping("/hello")
    ResponseEntity<?> SayHello(){

        response.clear();

        response.put("Greeting", "I hope you are doing well");
        response.put("Say Hello", "I say hello");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/wait/{seconds}")
    ResponseEntity<?> PleaseWait(@PathVariable Integer seconds){

        response.clear();
        Instant start = Instant.now();

        try {
            // Convert seconds to milliseconds and make the thread sleep
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            // Handle the exception if needed
            System.out.println(e.getMessage());
            response.put("Error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("Time taken: "+ timeElapsed.getSeconds() +" Seconds");

        response.put("Time taken", timeElapsed.getSeconds() +" Seconds");
        response.put("Say Hello", "I say hello");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
