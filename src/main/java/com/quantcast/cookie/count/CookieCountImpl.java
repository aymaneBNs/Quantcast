package com.quantcast.cookie.count;

import com.quantcast.cookie.model.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CookieCountImpl implements ICookieComputable {
    private static final Logger LOGGER = LogManager.getLogger(CookieCountImpl.class);
    @Override
    public HashMap<String, Integer> computeCookieUsageInSpecificDay(List<Cookie> cookies, Date day) {
        HashMap<String, Integer> cookiesUsageMap = new HashMap<>();
        Date lastCookieDate = null;
        for (int i = 0; i < cookies.size(); ++i) {
            Cookie cookie = cookies.get(i);

            if (lastCookieDate != null && lastCookieDate.after(cookie.getDate())) {
                break;
            }
            if (cookie.getDate().compareTo(day) != 0) {
                continue;
            }
            if (cookiesUsageMap.containsKey(cookie.getId())) {
                int NumberOfCookies = cookiesUsageMap.get(cookie.getId());
                cookiesUsageMap.put(cookie.getId(), ++NumberOfCookies);
                LOGGER.info("Incrementing The Number Usage of the cookie {} for the day :{} ", cookie.getId(), day);
            } else {
                cookiesUsageMap.put(cookie.getId(), 1);
                LOGGER.info("Adding a new key to The cookiesUsageMap with id {}", cookie.getId());
            }
            lastCookieDate = cookie.getDate();
        }
        return cookiesUsageMap;
    }
    @Override
    public List<String> computeMostUsedCookiesForDay(HashMap<String, Integer> cookiesUsageMap) {
        List<String> mostActiveCookies = new ArrayList<>();
        LinkedHashMap<String, Integer> sortedCookiesWithUsageMap = new LinkedHashMap<>();

        if (cookiesUsageMap.isEmpty()) {
            LOGGER.warn("Not Able to compute Most Used Cookies For The Day . Cookies Map Is Empty");
            return new ArrayList<>();
        }

        cookiesUsageMap.entrySet()
                .stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedCookiesWithUsageMap.put(x.getKey(), x.getValue()));

        LOGGER.info("Sorted Cookies per Usage for The requested Day  : {} ", sortedCookiesWithUsageMap);

        String mostActiveCookieId = null;
        int maxNumberOfUsage = 0;

        mostActiveCookieId = sortedCookiesWithUsageMap.keySet().iterator().next();
        maxNumberOfUsage = sortedCookiesWithUsageMap.values().iterator().next();
        mostActiveCookies.add(mostActiveCookieId);

        for (Map.Entry<String, Integer> entry : sortedCookiesWithUsageMap.entrySet()) {
            if (entry.getKey() != mostActiveCookieId && entry.getValue().intValue() == maxNumberOfUsage) {
                mostActiveCookies.add(entry.getKey());
            }
        }
        LOGGER.info(" Most Used Cookie(s) are/is {}", mostActiveCookies);
        return mostActiveCookies;
    }
}