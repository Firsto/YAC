package ru.firsto.yac;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by razor on 11.05.15.
 * BoxManager
 */
public class BoxManager {
    private static BoxManager sBoxManager;
    private ArrayList<Box> mBoxes = new ArrayList<>();
    private int count = 0;

    Random random = new Random(System.currentTimeMillis());

    private BoxManager() {
        mBoxes = new ArrayList<>();
    }

    public static BoxManager get() {
        if (sBoxManager == null) {
            sBoxManager = new BoxManager();
        }
        return sBoxManager;
    }

    public ArrayList<Box> boxes() {
        return mBoxes;
    }

    public int generateBox() {
        if (count < 3) {
            Box box = new Box();
            box.setPoint(random.nextInt(10) * 30 + random.nextInt(10)*50, 30);
            box.setSpeed(random.nextInt(10) * 2 + 5);
            mBoxes.add(box);
            Statistics.get().boxGenerated();
        }
        count = mBoxes.size();

        return random.nextInt(10);
    }
}
