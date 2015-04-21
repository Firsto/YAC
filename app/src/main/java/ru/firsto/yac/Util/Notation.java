package ru.firsto.yac.Util;

/**
 * Created by razor on 21.04.15.
 * Number to order conversion
 */
public class Notation {

    static String[] prefix = {
            "",
            "thousand",
            "million",
            "billion",
            "trillion",
            "quadrillion",
            "quintillion",
            "sextillion",
            "septillion"
    };

    public static String get(double value) {
        String s = "";
        int o = 1;

        while ((value = (value / 1000)) > 1000) {
            o++;
        }

        if (o > 1) {
            s = String.valueOf(String.format("%.3f", value) + " " + prefix[o]);
        } else {
            s = String.valueOf(String.format("%.0f", value * 1000));
        }

        return s;
    }
}
