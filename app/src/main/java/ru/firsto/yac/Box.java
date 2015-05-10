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
    private int speed = 5;
    private boolean clear = false;
    private boolean clicked = false;
    private long updatedAt;

    Box () {
        point = new PointF(10, 10);
        mRectF = new RectF(getPoint().x, getPoint().y, getPoint().x+30, getPoint().y+30);
        updatedAt = System.currentTimeMillis();
    }

    public void setBox(PointF point, RectF rect) {
        this.point = point;
        this.mRectF = rect;
    }

    public void setPoint(int y) {
        this.point.y = y;
    }

    public void setPoint(int x, int y) {
        this.point.x = x;
        this.point.y = y;
    }

    private void initRecT() {
        mRectF.set(getPoint().x, getPoint().y, getPoint().x+30, getPoint().y+30);
        updatedAt = System.currentTimeMillis();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
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

    public boolean isClicked() {
        return clicked;
    }

    public boolean click(int x, int y) {
        if (mRectF.contains(x, y)) clicked = true;
        return clicked;
    }
}
