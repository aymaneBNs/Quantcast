package com.quantcast.cookie.read;

import com.quantcast.cookie.model.Cookie;
import com.quantcast.cookie.exceptions.CookieFileReadingException;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CsvCookieReader implements ICookieReadable {

    private static final Logger LOGGER = LogManager.getLogger(CsvCookieReader.class);

    @Override
    public List<Cookie> readCookiesFromFile(String filePath) throws CookieFileReadingException {
        List<Cookie> cookies = null;
        try {
            cookies = new ArrayList<>();
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                String[] lineInArray;
                reader.readNext(); // skip header
                while ((lineInArray = reader.readNext()) != null) {
                    LOGGER.info("reader reads cookie Record with id {}", lineInArray[0]);
                    Date date = getCookieDateFromTimestamp(lineInArray[1].substring(0, 19)); // assuming Time Zone is not needed from the String
                    cookies.add(new Cookie(lineInArray[0], date));
                }
            }
            return cookies;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CookieFileReadingException();
        }
    }

    private Date getCookieDateFromTimestamp(String cookieTimestamp) throws ParseException {
        String simpleDatePattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(simpleDatePattern);
        LocalDateTime localDateTime = LocalDateTime.parse(cookieTimestamp);
        String formattedDateStr = DateTimeFormatter.ofPattern(simpleDatePattern, Locale.ENGLISH).format(localDateTime);
        Date date = formatter.parse(formattedDateStr);
        return date;
    }

}
