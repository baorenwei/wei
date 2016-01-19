package com.example.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/3.
 */
public class DataUtils {

    public static String longConversionString(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String str = format.format(date);
        return str;
    }
}
