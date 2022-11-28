package com.quantcast.cookie;

import com.quantcast.cookie.exceptions.ConsoleReadingException;

public interface ICommandLineReader {
    String[] readFileNameAndDate() throws ConsoleReadingException;
}