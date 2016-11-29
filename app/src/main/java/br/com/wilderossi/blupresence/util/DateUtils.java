package br.com.wilderossi.blupresence.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
    private static final SimpleDateFormat DATE_FORMAT     = new SimpleDateFormat("dd/MM/yyyy");
    private static final TimeZone TIMEZONE                = TimeZone.getTimeZone("UTC");

    static {
        DATETIME_FORMAT.setTimeZone(TIMEZONE);
        DATE_FORMAT.setTimeZone(TIMEZONE);
    }

    public static String getDateTimeString(Calendar calendar){
        return DATETIME_FORMAT.format(calendar.getTime());
    }

    public static String getDateString(Calendar calendar){
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String data) {
        try {
            Date date = DATE_FORMAT.parse(data);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.setTimeZone(TIMEZONE);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
