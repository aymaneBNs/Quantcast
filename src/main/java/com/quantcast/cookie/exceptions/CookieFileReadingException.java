package com.quantcast.cookie.exceptions;

public class CookieFileReadingException extends Exception {
    public CookieFileReadingException() {
        super("Error Cannot Read Or Process the given cookies file Please check if the file Exists and the content is correct");
    }
}