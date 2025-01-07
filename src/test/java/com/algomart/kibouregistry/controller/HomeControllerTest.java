package com.algomart.kibouregistry.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    void shouldReturnWelcomeMessage() {
        HomeController homeController = new HomeController();

        ResponseEntity<String> response = homeController.getHomeMessage();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Welcome to Kibou-Project", response.getBody());
    }
}
