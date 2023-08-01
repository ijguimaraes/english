package com.speakto.english.service;

public class NextTime {
    public static Long get30Minutes() {
        return 30L;
    }

    public static Long getAnHour() {
        return 60L;
    }

    public static Long getAhDay() {
        return getAnHour()*24;
    }

    public static Long getTwoDays() {
        return getAhDay()*2;
    }

    public static Long getFourDays() {
        return getAhDay()*2;
    }

    public static Long getAhWeek() {
        return getAhDay()*7;
    }

    public static Long getTwoWeek() {
        return getAhWeek()*2;
    }

    public static Long getAhMonth() {
        return getAhWeek()*4;
    }

    public static Long getTwoMonth() {
        return getAhMonth()*2;
    }

    public static Long getThreeMonth() {
        return getAhMonth()*3;
    }

    public static Long getSixMonth() {
        return getAhMonth()*6;
    }

    public static Long getAhYear() {
        return getAhMonth()*12;
    }
}
