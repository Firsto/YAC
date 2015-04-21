package ru.firsto.yac;

import java.util.concurrent.TimeUnit;

/**
 * Created by razor on 16.04.15.
 * Item
 */
public class Item {
    private int id;
    private String name;
    private long base_cost;
    private long base_bonus;
    private int level;
    private double price;
    private double bonus;
    private double cost;

    private Item() {
    }

    public Item(int id, String name, long base_cost, long base_bonus) {
        this.id = id;
        this.name = name;
        this.base_cost = base_cost;
        this.base_bonus = base_bonus;
        this.level = -1;
        recalculateStats();
    }

    public void recalculateStats() {
        if (level >= 0) {
            this.price = base_cost * Math.pow(1.5, level);
            this.bonus = base_bonus * Math.pow(1.2, level);
            this.cost = base_cost;
            for (int i = 1; i <= level; i++) {
                this.cost += base_cost * Math.pow(1.5, i);
            }
        } else {
            this.price = base_cost;
            this.bonus = 0;
            this.cost = 0;
        }
    }

    public void levelUp() {
        this.level++;
        recalculateStats();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getPrice() {
        return price;
    }

    public double getBonus() {
        return bonus;
    }

    public double getCost() {
        return cost;
    }

    public String getTime(double bps) {
        String remaining = "";

        long time = (long) (getPrice() / bps) * 1000;

        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        remaining = remaining
                + (days > 0 ? days + ":" : "")
                + (hours > 0 ? hours + ":" : "")
                + (minutes > 0 ? (minutes > 10 ? minutes : "0" + minutes) + ":" : "00:")
                + (seconds > 0 ? (seconds > 10 ? seconds : "0" + seconds) : "00");

        return remaining;
    }
}
