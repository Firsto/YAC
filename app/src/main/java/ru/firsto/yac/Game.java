package ru.firsto.yac;

import java.util.HashMap;

/**
 * Created by razor on 20.04.15.
 * Game
 */
public class Game {
    private static Game sGame;
    private ItemPool mItemPool;

    private long resources = 1000L;
    private double income = 10.0;
    private long startgame = System.currentTimeMillis();

    private Game(ItemPool itemPool) {
        mItemPool = itemPool;
    }

    public static Game get() {
        if (sGame == null) {
            sGame = new Game(ItemPool.get());
        }
        return sGame;
    }

    public Game newGame(boolean newgame) {
        if (newgame) {
            ItemPool.get().newItemPool();
            Statistics.get().newStatistics();
            sGame = null;
        }
        return get();
    }

    public long getResources() {
        return resources;
    }

    public long getStartgame() {
        return startgame;
    }

    public void addResources(long inc) {
        resources += inc;
    }

    public double getIncome() {
        income = 0;
        for (int i = 1; i <= mItemPool.allItems().size(); i++) {
            if (mItemPool.item(i).getLevel() >= 0) {
                income += mItemPool.item(i).getBonus();
            }
        }
        return income;
    }

    public long getGametime() {
        return System.currentTimeMillis() - startgame;
    }

    public void loadGame(long resources, long startgame, int[] levels, HashMap<String, String> stats) {
        this.resources = resources;
        this.startgame = startgame;
        mItemPool.initItems(levels);
        Statistics.get().loadStatistics(stats);
    }
}
