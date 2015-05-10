package ru.firsto.yac;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import ru.firsto.yac.Util.Notation;

/**
 * Created by razor on 29.04.15.
 * Game Surface
 **/
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

    private GameSurfaceThread thread;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBoxPaint, mBoxErase;
    int cx, cy, offx, offy;

    Box mBox = new Box();
    ArrayList<Box> mBoxes = new ArrayList<>();

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
//        mBoxPaint.setColor(0x22005599);
        mBoxPaint.setColor(Color.WHITE);
//        mBoxPaint.setColor(Color.argb(10,0,255,255));
        mBoxErase = new Paint();
//        mBoxErase.setColor(Color.argb(0, 0, 0, 0));
//        mBoxErase.setColor(0x22005599);
//        mBoxErase.setColor(0);
//        mBoxErase.setColor(Color.BLACK);
        mBoxErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

//        mBoxErase.setColor(getResources().getColor(android.R.color.transparent));
//        mBoxErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        mBoxErase.setAntiAlias(true);

        cx = 0;
        cy = 0;
        offx = 10;
        offy = 10;

        for (int i = 0; i < 3; i++) {
            Box box = new Box();
            box.setPoint(i*30+50, 30);
            box.setSpeed(i*2+5);
            mBoxes.add(box);
        }
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
        canvas.drawARGB(64, 0, 0, 0);

        if (cx > 0 && cy > 0) {
            canvas.drawText("+" + Notation.get(Math.round(Game.get().getIncome() / 10)), cx, cy, paint);
        }
        m++;
        if (m == 100) {
            resetC();
            m = 0;
        }

//        if (clear) {
//            clear = false;
//            drawing.drawColor(Color.BLACK);
//            canvas.drawBitmap(bmp, mBox.getPoint().x, mBox.getPoint().y, null);
//            mBox.move();
//        }
//
//        drawing.drawBitmap(boxbitmap, 0, 0, null);
//        canvas.drawBitmap(bmp, mBox.getPoint().x, mBox.getPoint().y, null);
//
//        long now = System.currentTimeMillis();
//        long elapsedTime = now - prevTime;
//        if (elapsedTime > 10) {
//            prevTime = now;
//            clear = true;
//        }

        for (Box box : mBoxes) {
            if (box.isClear()) {
                box.setClear(false);
                drawing.drawColor(Color.BLACK);
                canvas.drawBitmap(bmp, box.getPoint().x, box.getPoint().y, null);
                box.move();
            }

            drawing.drawBitmap(boxbitmap, 0, 0, null);
            canvas.drawBitmap(bmp, box.getPoint().x, box.getPoint().y, null);

            long now = System.currentTimeMillis();
            long elapsedTime = now - box.getUpdatedAt();
            if (elapsedTime > 10) {
                box.setClear(true);
            }
            if (box.getPoint().y > getHeight()) {
                box.setPoint(0);
            }
        }


    }

    boolean clear = false;

    static class FadePainter {

        public static Bitmap paint(int width, int height) {
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas drawing = new Canvas(bmp);

            return bmp;
        }
    }

    private long prevTime = System.currentTimeMillis();

    int m = 0;

    public void resetC() {
        cx = 0;
        cy = 0;
    }
    Canvas drawing;
    Bitmap bmp, boxbitmap;

    public void centerPic() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(displayMetrics);
//        thread.centerMatrix(displayMetrics.widthPixels, displayMetrics.heightPixels);
        thread.centerMatrix(getWidth(), getHeight());
        bmp = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
        boxbitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
        drawing = new Canvas(boxbitmap);
        drawing.drawRect(mBox.getRectF(), mBoxPaint);
        drawing = new Canvas(bmp);
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