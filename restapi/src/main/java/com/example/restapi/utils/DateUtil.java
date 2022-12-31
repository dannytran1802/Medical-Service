package com.example.restapi.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {
    private String pattern = "yyyy-MM-dd";

    public boolean compareDate(String endDate, String startDate) {
        if (!endDate.isEmpty() || !startDate.isEmpty()) {
            Date end = convertStringToDate(endDate, pattern);
            Date start = convertStringToDate(startDate, pattern);
            return start.before(end);
        }
        return false;
    }

    public static Date convertStringToDate(String value, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // convert from string DTO to Date Entity
    public static String convertDateToString(Date value, String pattern) {
        return new SimpleDateFormat(pattern).format(value);
    }

    public boolean compareDate(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }


    public static boolean compareDate(Date dateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date date = new Date();
        int checkResult = dateInput.compareTo(date);
        if (checkResult >= 0) {
            return true;
        }
        return false;
    }

}
