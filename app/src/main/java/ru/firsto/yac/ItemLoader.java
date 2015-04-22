package ru.firsto.yac;

import android.content.Context;

/**
 * Created by razor on 22.04.15.
 **/
public class ItemLoader extends DataLoader<Item> {
    private int mItemId;

    public ItemLoader(Context context, int itemId) {
        super(context);
        mItemId = itemId;
    }

    @Override
    public Item loadInBackground() {
        return ItemPool.get().item(mItemId);
    }
}