package com.quantcast.cookie.read;

import com.quantcast.cookie.model.Cookie;
import com.quantcast.cookie.exceptions.CookieFileReadingException;

import java.util.List;

public interface ICookieReadable {
    public List<Cookie> readCookiesFromFile(String filePath) throws CookieFileReadingException;
}
