package ru.firsto.yac;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics);
        thread.centerMatrix(displayMetrics.widthPixels, displayMetrics.heightPixels);

        setFocusable(true); // make sure we get key events

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setTextSize(18);
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
        if (thread.getState() == Thread.State.NEW) {
            thread.start();
        }

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
    }

    int m = 0;

    public void resetC() {
        cx = 0;
        cy = 0;
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