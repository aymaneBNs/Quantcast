package com.quantcast.cookie.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static Date getDateFromString(String textDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(textDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}