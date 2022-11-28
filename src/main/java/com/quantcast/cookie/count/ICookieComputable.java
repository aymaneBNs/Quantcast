package com.quantcast.cookie.count;

import com.quantcast.cookie.model.Cookie;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ICookieComputable {
    HashMap<String, Integer> computeCookieUsageInSpecificDay(List<Cookie> cookies, Date day);
    List<String> computeMostUsedCookiesForDay(HashMap<String, Integer> cookiesUsageMap);
}