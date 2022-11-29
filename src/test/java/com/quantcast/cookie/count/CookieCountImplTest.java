package com.quantcast.cookie.count;

import com.quantcast.cookie.exceptions.CookieFileReadingException;
import com.quantcast.cookie.helper.DateHelper;
import com.quantcast.cookie.model.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CookieCountImplTest {
    ICookieComputable cookieCount;

    @BeforeEach
    public void init() {
        cookieCount = new CookieCountImpl();
    }

    @Test
    void ShouldReturnTheCorrectNumberOfCookiesPerDay() throws CookieFileReadingException {
        List<Cookie> cookieList = generateCookiesListForTest();
        Date testDay = DateHelper.getDateFromString("2018-12-07");
        HashMap<String, Integer> cookiesUsageMap = cookieCount.computeCookieUsageInSpecificDay(cookieList, testDay);
        assertTrue(cookiesUsageMap.containsKey("XXXXXXX") && cookiesUsageMap.get("XXXXXXX") == 2);
        assertTrue(cookiesUsageMap.containsKey("ZZZZZZZ") && cookiesUsageMap.get("ZZZZZZZ") == 1);
        assertFalse(cookiesUsageMap.containsKey("BBBBBBB"));
    }

    @Test
    void ShouldReturnTheCorrectMostUsedCookieForDayWhenOneCookie() {
        HashMap<String, Integer> cookiesUsagePerDayTestMap = new HashMap<>();
        cookiesUsagePerDayTestMap.put("XXXXXXX", 2);
        cookiesUsagePerDayTestMap.put("ZZZZZZZ", 1);
        List<String> ExpectedMostUsedCookie = new ArrayList<>();
        ExpectedMostUsedCookie.add("XXXXXXX");
        List<String> actualMostUsedCookie = cookieCount.computeMostUsedCookiesForDay(cookiesUsagePerDayTestMap);
        Assertions.assertEquals(actualMostUsedCookie.size(), 1);
        Assertions.assertEquals(actualMostUsedCookie, ExpectedMostUsedCookie);
    }


    @Test
    void ShouldReturnTheCorrectMostUsedCookiesForDayWhenMoreThanOneCookies() {
        HashMap<String, Integer> cookiesUsagePerDayTestMap = new HashMap<>();
        cookiesUsagePerDayTestMap.put("XXXXXXX", 2);
        cookiesUsagePerDayTestMap.put("ZZZZZZZ", 2);
        List<String> ExpectedMostUsedCookie = new ArrayList<>();
        ExpectedMostUsedCookie.add("XXXXXXX");
        ExpectedMostUsedCookie.add("ZZZZZZZ");
        List<String> actualMostUsedCookie = cookieCount.computeMostUsedCookiesForDay(cookiesUsagePerDayTestMap);
        Assertions.assertEquals(actualMostUsedCookie.size(), 2);
        Assertions.assertEquals(actualMostUsedCookie, ExpectedMostUsedCookie);

    }

    private List<Cookie> generateCookiesListForTest() {
        List<Cookie> cookieList = new ArrayList<>();
        cookieList.add(new Cookie("BBBBBBB", DateHelper.getDateFromString("2022-12-30")));
        cookieList.add(new Cookie("XXXXXXX", DateHelper.getDateFromString("2018-12-07")));
        cookieList.add(new Cookie("XXXXXXX", DateHelper.getDateFromString("2018-12-07")));
        cookieList.add(new Cookie("ZZZZZZZ", DateHelper.getDateFromString("2018-12-07")));
        return cookieList;
    }
}