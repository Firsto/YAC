package ru.firsto.yac;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by razor on 07.05.15.
 * Bonus box
 */
public class Box {
    private PointF point;
    private RectF mRectF;
    private int speed = 10;

    Box () {
        point = new PointF(10, 10);
        mRectF = new RectF(getPoint().x, getPoint().y, getPoint().x+30, getPoint().y+30);
    }

    public void setBox(PointF point, RectF rect) {
        this.point = point;
        this.mRectF = rect;
    }

    private void initRecT() {
        mRectF.set(getPoint().x, getPoint().y, getPoint().x+30, getPoint().y+30);
    }

    public PointF getPoint() {
        return point;
    }

    public RectF getRectF() {
        return mRectF;
    }

    public void move() {
        point.set(point.x, point.y + speed);
        initRecT();
    }
}
