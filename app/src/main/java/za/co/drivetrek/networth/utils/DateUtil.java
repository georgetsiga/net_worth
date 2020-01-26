package za.co.drivetrek.networth.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static String DATE_FORMAT = "yyyy-MM-dd";

    public static String getStringDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return formatter.format(date);
    }
}
