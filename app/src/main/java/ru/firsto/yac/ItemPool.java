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
    public ArrayList<Item> availableItems() {
//        ArrayList<Item> availableItems = (ArrayList<Item>) mItems.clone();
        ArrayList<Item> availableItems = new ArrayList<Item>(mItems.size());
        availableItems.add(mItems.get(0));

        for (int i = 1; i < mItems.size(); i++) {
            if (mItems.get(i-1).getLevel() >= 0) {
                availableItems.add(mItems.get(i));
            }
        }

        return availableItems;
    }

    public Item item(int id) {
        return mItems.get(id - 1);
    }

    public boolean upItemLevel(int id, Game game) {
        boolean result = false;

        if (item(id).getPrice() <= game.getResources()) {
            game.addResources(-1 * (long) item(id).getPrice());
            item(id).levelUp();
            result = true;
        }
        return result;
    }
//
//    public void recalculateItemRemaining(double bps) {
//        for (Item item : mItems) {
//            item.getTime(bps);
//        }
//    }
}
