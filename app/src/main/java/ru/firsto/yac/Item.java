package ru.firsto.yac;

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
    private long time;

    public Item() {
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

    public long getTime() {
        return time;
    }
}
