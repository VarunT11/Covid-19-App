package com.example.covid19_safetyapp.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String getDisplayTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm a, dd-MM-yyyy", Locale.ENGLISH);
        return format.format(new Date(time));
    }

}
