package com.quantcast.cookie.exceptions;

public class ConsoleReadingException extends Exception {
    public ConsoleReadingException() {
        super("Error Cannot Read fileName -f and/or date -d Please check that your parameters are correct");
    }
}