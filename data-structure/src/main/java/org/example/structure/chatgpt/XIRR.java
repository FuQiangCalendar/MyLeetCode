package org.example.structure.chatgpt;

import java.util.*;
public class XIRR {
    public static double xirr(List<Double> cashFlows, List<Date> dates, double guess) {
        double x0 = guess;
        double x1 = 0.0;
        double fValue = 0.0;
        double fDerivative = 0.0;
        int i;
        for (i = 0; i < 100; i++) {
            fValue = 0.0;
            fDerivative = 0.0;
            for (int j = 0; j < cashFlows.size(); j++) {
                fValue += cashFlows.get(j) / Math.pow((1.0 + x0), ((double) daysBetween(dates.get(j), dates.get(0))) / 365.0);
                fDerivative += -(cashFlows.get(j) * ((double) daysBetween(dates.get(j), dates.get(0))) / 365.0) / Math.pow((1.0 + x0), (((double) daysBetween(dates.get(j), dates.get(0))) / 365.0 + 1.0));
            }
            x1 = x0 - fValue / fDerivative;
            if (Math.abs(x1 - x0) < 1E-9) {
                break;
            }
            x0 = x1;
        }
        return x1;
    }
    private static int daysBetween(Date d1, Date d2) {
        return (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
    }
}