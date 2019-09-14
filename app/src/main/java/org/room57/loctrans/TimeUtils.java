package org.room57.loctrans;

import java.util.Calendar;

public class TimeUtils {
    public static boolean isLuniVineri(Calendar C) {
        if ((C.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (C.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
            return false;
        return true;
    }
}
