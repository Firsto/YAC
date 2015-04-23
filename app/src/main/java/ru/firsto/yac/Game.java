package ru.firsto.yac;

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

    public long getResources() {
        return resources;
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
}
