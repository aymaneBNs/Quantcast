package com.quantcast.cookie.read;

import com.quantcast.cookie.count.CookieCountImpl;
import com.quantcast.cookie.exceptions.CookieFileReadingException;
import com.quantcast.cookie.model.Cookie;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvCookieReaderTest {
    CookieCountImpl cookieLog;
    ICookieReadable cookieReadable;

    @BeforeEach
    public void init() {
        cookieLog = new CookieCountImpl();
        cookieReadable = new CsvCookieReader();
    }

    @Test
    void ShouldReturnTheCorrectNumberOfCookiesInFile() throws CookieFileReadingException {
        String TEST_CSV_FILE_PATH = "TestCsv/cookie_log_test.csv";
        String[] expectedCookiesArray = new String[]{"AtY0laUfhglK3lC7", "5UAVanZf6UtGyKVS", "SAZuXPGUrfbcn5UA", "4sMM2LxV07bPJzwf"};
        List<Cookie> currentCookiesList = cookieReadable.readCookiesFromFile(TEST_CSV_FILE_PATH);
        Object[] currentCookiesAsArray = currentCookiesList.stream().map(e -> e.getId()).collect(Collectors.toList()).toArray();
        Assert.assertArrayEquals(expectedCookiesArray, currentCookiesAsArray);
    }

    @Test
    void ShouldReturnNoCookiesWhenFileIsEmpty() throws CookieFileReadingException {
        String TEST_CSV_FILE_PATH = "TestCsv/cookie_log_test_empty.csv";
        int expectedCookiesArraySize = 0;
        List<Cookie> currentCookiesList = cookieReadable.readCookiesFromFile(TEST_CSV_FILE_PATH);
        int currentCookiesAsArray = currentCookiesList.size();
        Assert.assertEquals(expectedCookiesArraySize, currentCookiesAsArray);
    }

    @Test
    void ShouldThrowExceptionWhenFileDoesntExist() throws CookieFileReadingException {
        String TEST_CSV_FILE_PATH = "TestCsv/notExists.csv";
        assertThrows(CookieFileReadingException.class, () -> cookieReadable.readCookiesFromFile(TEST_CSV_FILE_PATH));
    }
}