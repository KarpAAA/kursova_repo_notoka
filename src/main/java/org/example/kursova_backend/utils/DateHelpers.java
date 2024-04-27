package org.example.kursova_backend.utils;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DateHelpers {

    public static String transformDateWithDayOfTheWeek(String isoDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Assuming ISO 8601 format
        try {
            Date date = sdf.parse(isoDateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String[] monthNames = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            String monthName = monthNames[calendar.get(Calendar.MONTH)];

            return calendar.get(Calendar.DAY_OF_MONTH) + " " + monthName;
        } catch (ParseException e) {
            throw new RuntimeException("Invalid ISO date format: " + isoDateString, e);
        }
    }
}
