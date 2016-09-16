package br.com.wilderossi.blupresence.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");

    public static String getDateTime(Calendar calendar){
        return DATETIME_FORMAT.format(calendar.getTime());
    }

}
