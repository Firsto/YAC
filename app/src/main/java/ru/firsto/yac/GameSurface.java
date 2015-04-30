package ru.firsto.yac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
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
    int cx, cy, offx, offy;

    public GameSurface(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init(){
        getHolder().addCallback(this);
        thread = new GameSurfaceThread(getHolder(), this);

        setFocusable(true); // make sure we get key events

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);

        cx = 0;
        cy = 0;
        offx = 10;
        offy = 10;

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        thread.setRunning(true);
        thread.start();

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
//        canvas.drawRGB(0, 0, 0);
        canvas.drawARGB(10, 0, 0, 0);
//        canvas.drawCircle(cx, cy, 3, paint);

        canvas.drawText("+" + Notation.get(Math.round(Game.get().getIncome() / 10)), cx, cy, paint);


        cx = -50;
        cy = -50;


//        cx += offx;
//        if (cx > getWidth() || (cx < 0)){
//            offx *= -1;
//            cx += offx;
//        }
//
//        cy += offy;
//        if (cy > getHeight() || (cy < 0)){
//            offy *= -1;
//            cy += offy;
//        }
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
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            path.lineTo(event.getX(), event.getY());
        }

//        if (path != null) {
//            Canvas canvas = surfaceHolder.lockCanvas();
//            canvas.drawPath(path, paint);
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }

        return true;
    }
}