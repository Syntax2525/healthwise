package com.example.healthwise.utils;

import java.util.concurrent.TimeUnit;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static String formatRelativeTime(long timestamp) {
        long diffMillis = System.currentTimeMillis() - timestamp;
        if (diffMillis < 0) {
            return "Just now";
        }

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);
        if (minutes < 1) {
            return "Just now";
        }
        if (minutes < 60) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(diffMillis);
        if (hours < 24) {
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        }

        long days = TimeUnit.MILLISECONDS.toDays(diffMillis);
        if (days < 7) {
            return days + (days == 1 ? " day ago" : " days ago");
        }

        long weeks = days / 7;
        if (weeks < 5) {
            return weeks + (weeks == 1 ? " week ago" : " weeks ago");
        }

        long months = days / 30;
        return months + (months == 1 ? " month ago" : " months ago");
    }
}
