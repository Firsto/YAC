package ru.firsto.yac;

/**
 * Created by razor on 11.05.15.
 * Achievement
 */
public class Achievement {
    private String mTitle;
    private String mDescription;

    public Achievement(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
