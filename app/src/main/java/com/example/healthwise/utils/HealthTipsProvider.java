package com.example.healthwise.utils;

import java.util.Calendar;

public final class HealthTipsProvider {

    private static final String[][] TIPS = {
            {"Staying Hydrated", "Drinking water regularly supports energy, digestion, and cognitive function."},
            {"Move Every Hour", "Short walks and stretching reduce stiffness and improve circulation."},
            {"Sleep Routine", "A consistent bedtime helps your body recover and strengthens immunity."},
            {"Balanced Meals", "Include vegetables, protein, and whole grains for steady daily energy."},
            {"Monitor Symptoms", "Track changes early so you can make informed decisions about care."},
            {"Stress Breaks", "Brief breathing exercises can lower tension and improve focus."},
            {"Sun Protection", "Use sunscreen and hydration when spending time outdoors."},
            {"Limit Screen Time", "Rest your eyes and posture by taking breaks from devices."}
    };

    private HealthTipsProvider() {
    }

    public static String getDailyTipTitle() {
        return TIPS[getDailyIndex()][0];
    }

    public static String getDailyTipBody() {
        return TIPS[getDailyIndex()][1];
    }

    public static String getDailyTipFormatted() {
        return "Daily tip\n" + getDailyTipTitle() + "\n" + getDailyTipBody();
    }

    private static int getDailyIndex() {
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return dayOfYear % TIPS.length;
    }
}
