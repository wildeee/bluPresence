package br.com.wilderossi.blupresence.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
    private static final SimpleDateFormat DATE_FORMAT     = new SimpleDateFormat("dd/MM/yyyy");

    public static String getDateTimeString(Calendar calendar){
        return DATETIME_FORMAT.format(calendar.getTime());
    }

    public static String getDateString(Calendar calendar){
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String data) {
        String[] dateFields = data.split("/");
        Integer day   = Integer.valueOf(dateFields[0]);
        Integer month = Integer.valueOf(dateFields[1]);
        Integer year  = Integer.valueOf(dateFields[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);

        return calendar;
    }
}
