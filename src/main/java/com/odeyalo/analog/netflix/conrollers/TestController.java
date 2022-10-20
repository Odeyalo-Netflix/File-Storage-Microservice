package com.odeyalo.analog.netflix.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> test(Authentication authentication) {
        System.out.println(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
