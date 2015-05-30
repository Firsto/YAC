package ru.firsto.yac.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import ru.firsto.yac.Game;
import ru.firsto.yac.Statistics;

/**
 * Created by razor on 27.05.15.
 * Saves manager
 */
public class Saver {
    public static final String PREF_RESOURSES = "yac_resourses";
    public static final String PREF_STARTGAME = "yac_startgame";
    public static final String[] PREF_LEVELS = {
            "pref_level_abacus",
            "pref_level_arithmometer",
            "pref_level_calculator",
            "pref_level_microcircuit",
            "pref_level_CPU",
            "pref_level_desktop",
            "pref_level_workstation",
            "pref_level_server",
            "pref_level_mainframe",
            "pref_level_supercomputer",
            "pref_level_AI"
    };
    public static final String PREF_STATS = "yac_stats";

    public static void save(Context context, long resources, long startgame, int[] levels, HashMap<String, String> stats) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(PREF_RESOURSES, resources);
        editor.putLong(PREF_STARTGAME, startgame);
        for (int i = 0; i < levels.length; i++) {
            editor.putInt(PREF_LEVELS[i], levels[i]);
        }
        for (String s : stats.keySet()) {
            editor.putString(PREF_STATS + "_" + s, stats.get(s));
        }

        editor.apply();
    }

    public static void load(Context context, Game game) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        long resourses = preferences.getLong(PREF_RESOURSES, 0);
        long startgame = preferences.getLong(PREF_STARTGAME, System.currentTimeMillis());
        int[] levels = new int[PREF_LEVELS.length];
        for (int i = 0; i < levels.length; i++) {
            levels[i] = preferences.getInt(PREF_LEVELS[i], -1);
        }
        HashMap<String, String> stats = Statistics.get().statistics();
        for (String s : stats.keySet()) {
            stats.put(s, preferences.getString(PREF_STATS + "_" + s, "0"));
        }

        game.loadGame(resourses, startgame, levels, stats);
    }
}
