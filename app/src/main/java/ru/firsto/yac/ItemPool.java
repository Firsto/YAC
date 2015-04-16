package ru.firsto.yac;

import java.util.ArrayList;

/**
 * Created by razor on 16.04.15.
 * Storage for Item objects
 */
public class ItemPool {
    private static ItemPool sItemPool;
    private ArrayList<Item> mItems = new ArrayList<>();

    static String[] names = {
            "abacus",
            "arithmometer",
            "calculator",
            "microcircuit",
            "CPU",
            "desktop",
            "workstation",
            "server",
            "mainframe",
            "supercomputer",
            "AI"
    };

    private ItemPool() {
//        mItems.add(new Item(1, "abacus", 10, 1));
//        mItems.add(new Item(2, "arithmometer", 150, 10));
//        mItems.add(new Item(3, "calculator", 2250, 100));
//        mItems.add(new Item(4, "microcircuit", 33750, 1000));
        for (int i = 0; i < names.length; i++) {
            mItems.add(new Item(i+1, names[i], 10*Math.round(Math.pow(15, i)), Math.round(Math.pow(10, i+1))));
        }
    }

    public static ItemPool get() {
        if (sItemPool == null) {
            sItemPool = new ItemPool();
        }
        return sItemPool;
    }

    public ArrayList<Item> allItems() {
        return mItems;
    }

    public Item item(int id) {
        return mItems.get(id - 1);
    }
}
