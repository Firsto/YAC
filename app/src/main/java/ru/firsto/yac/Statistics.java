package ru.firsto.yac;

import java.util.HashMap;

/**
 * Created by razor on 24.05.15.
 * Statistics
 */
public class Statistics {
    private static Statistics sStatistics;

    public static final String STATS_GAMETIME = "gametime";
    public static final String STATS_CLICKS = "clicks";
    public static final String STATS_ITEMS = "items";
    public static final String STATS_ACHIEVEMENTS = "achievements";
    public static final String STATS_BOXES_GENERATED = "boxesGenerated";
    public static final String STATS_BOXES_CAUGHT = "boxesCaught";

    private long gametime = 0;
    private int clicks = 0;
    private int items = 0;
    private int achievements = 0;
    private int boxesGenerated = 0;
    private int boxesCaught = 0;

    HashMap<String, String> stats = new HashMap<>();

    private Statistics() {
    }

    public static Statistics get() {
        if (sStatistics == null) {
            sStatistics = new Statistics();
        }
        return sStatistics;
    }

    public void newStatistics() {
        sStatistics = null;
    }

    public void init() {
        gametime = Game.get().getGametime();
        items = ItemPool.get().availableItems().size();

        stats.put(STATS_GAMETIME, String.valueOf(gametime));
        stats.put(STATS_CLICKS, String.valueOf(clicks));
        stats.put(STATS_ITEMS, String.valueOf(items));
        stats.put(STATS_ACHIEVEMENTS, String.valueOf(achievements));
        stats.put(STATS_BOXES_GENERATED, String.valueOf(boxesGenerated));
        stats.put(STATS_BOXES_CAUGHT, String.valueOf(boxesCaught));
    }

    public HashMap<String, String> statistics() {
        init();
        return stats;
    }

    public void loadStatistics(HashMap<String, String> stats) {
//        gametime = Long.parseLong(stats.get(STATS_GAMETIME));
        clicks = Integer.parseInt(stats.get(STATS_CLICKS));
        boxesGenerated = Integer.parseInt(stats.get(STATS_BOXES_GENERATED));
        boxesCaught = Integer.parseInt(stats.get(STATS_BOXES_CAUGHT));
        init();
    }

    public void click() {
        clicks++;
    }

    public void boxGenerated() {
        boxesGenerated++;
    }

    public void boxCaught() {
        boxesCaught++;
    }
}
