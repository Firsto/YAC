package ru.firsto.yac;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.firsto.yac.Util.Notation;

/**
 * Created by razor on 29.04.15.
 * Game Surface
 **/
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

    private GameSurfaceThread thread;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBoxPaint;
    int cx, cy, offx, offy;

    public GameSurface(Context context) {
        super(context);
        init();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        getHolder().addCallback(this);
        thread = new GameSurfaceThread(getHolder(), this);

        setFocusable(true); // для принятия событий нажатий

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setTextSize(18);
        paint.setColor(Color.WHITE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22005599);

        cx = 0;
        cy = 0;
        offx = 10;
        offy = 10;

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
//        Toast.makeText(getContext(), "surface changed", Toast.LENGTH_SHORT).show();
        centerPic();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        thread.setRunning(true);
        if (thread.getState() == Thread.State.NEW) {
            thread.start();
        } else {
            init();
            thread.setRunning(true);
            thread.start();
        }

//        Toast.makeText(getContext(), "surfaceview : " + this, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
            }
        }
//        Toast.makeText(getContext(), "surface destroyed", Toast.LENGTH_SHORT).show();
//        thread.interrupt();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRGB(0, 0, 0);
        canvas.drawARGB(10, 0, 0, 0);

        if (cx > 0 && cy > 0) {
            canvas.drawText("+" + Notation.get(Math.round(Game.get().getIncome() / 10)), cx, cy, paint);
        }
        m++;
        if (m == 100) {
            resetC();
            m = 0;
        }

        canvas.drawRect(mBox.getRectF(), mBoxPaint);
//        canvas.drawRect(mBox.getPoint().x, mBox.getPoint().y, mBox.getPoint().x+30, mBox.getPoint().y+30, mBoxPaint);
        mBox.move();

    }

    Box mBox = new Box();

    class Box {
        private PointF point;
        private RectF mRectF;
        private int speed = 10;

        Box () {
            point = new PointF(10, 10);
            mRectF = new RectF(getPoint().x, getPoint().y, getPoint().x+30, getPoint().y+30);
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

    int m = 0;

    public void resetC() {
        cx = 0;
        cy = 0;
    }

    public void centerPic() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics);
        thread.centerMatrix(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    Path path;
    SurfaceHolder surfaceHolder = getHolder();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            path = new Path();
//            path.moveTo(event.getX(), event.getY());
            cx = (int) event.getX();
            cy = (int) event.getY();
            Game.get().addResources((long) (Game.get().getIncome() / 10));
            thread.setClicked(true);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            path.lineTo(event.getX(), event.getY());
//            cx = 0;
//            cy = 0;
        }

//        if (path != null) {
//            Canvas canvas = surfaceHolder.lockCanvas();
//            canvas.drawPath(path, paint);
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }

        return true;
    }
}