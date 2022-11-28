package com.quantcast;

import com.quantcast.cookie.ICommandLineReader;
import com.quantcast.cookie.count.CookieCountImpl;
import com.quantcast.cookie.count.ICookieComputable;
import com.quantcast.cookie.exceptions.ConsoleReadingException;
import com.quantcast.cookie.exceptions.CookieFileReadingException;
import com.quantcast.cookie.helper.DateHelper;
import com.quantcast.cookie.model.Cookie;
import com.quantcast.cookie.read.CsvCookieReader;
import com.quantcast.cookie.read.ICookieReadable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MostActiveCookies implements ICommandLineReader {
    private static final Logger LOGGER = LogManager.getLogger(MostActiveCookies.class);


    public static void main(String[] args) throws ConsoleReadingException, CookieFileReadingException {
        ICommandLineReader consoleReader = new MostActiveCookies();
        String[] commandLineParams = consoleReader.readFileNameAndDate();
        ICookieReadable cookieReadable = new CsvCookieReader();
        List<Cookie> listOfCookies = cookieReadable.readCookiesFromFile(commandLineParams[0]);
        ICookieComputable cookieComputable = new CookieCountImpl();
        HashMap<String, Integer> cookiesUsagePerDay = cookieComputable.computeCookieUsageInSpecificDay(listOfCookies, DateHelper.getDateFromString(commandLineParams[1]));
        cookieComputable.computeMostUsedCookiesForDay(cookiesUsagePerDay).forEach(System.out::println);
    }


    @Override
    public String[] readFileNameAndDate() throws ConsoleReadingException {
        String fileName = "";
        String date = "";
        try {
            Scanner scanner = new Scanner(System.in);
            String ConsoleCommand = scanner.nextLine();
            for (int i = 0; i < ConsoleCommand.length(); ++i) {
                if (ConsoleCommand.charAt(i) == 'f' && ConsoleCommand.charAt(i - 1) == '-') {  // Read FileName
                    int f = i + 2;
                    while (f < ConsoleCommand.length() && ConsoleCommand.charAt(f) != ' ') {
                        fileName += ConsoleCommand.charAt(f);
                        ++f;
                    }
                    i = f;
                }
                if (ConsoleCommand.charAt(i) == 'd' && ConsoleCommand.charAt(i - 1) == '-') { // Read Date
                    int d = i + 2;
                    while (d < ConsoleCommand.length() && ConsoleCommand.charAt(d) != ' ') {
                        date += ConsoleCommand.charAt(d);
                        ++d;
                    }
                    i = d;
                }
            }
            if (date.isEmpty() || fileName.isEmpty()) {
                LOGGER.error("file is empty " + fileName.isEmpty() + " date is empty " + date.isEmpty());
                throw new ConsoleReadingException();
            }
            LOGGER.info("entered Date {}", date, DateHelper.getDateFromString(date));
            LOGGER.info("entered FileName {}", fileName);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ConsoleReadingException();
        }

        return new String[]{fileName, date};

    }

}
