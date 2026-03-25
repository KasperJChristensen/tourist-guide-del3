package com.example.touristguidedel3.exception;

public class AttractionNotFound extends RuntimeException {
    public AttractionNotFound(int id) {
        super("Attraction with ID: " + id + " was not found");
    }
}
