package ru.firsto.yac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceHolder;

import java.util.ArrayList;

/**
 * Created by razor on 29.04.15.
 */

public class GameSurfaceThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private GameSurface mGameSurface;
    private boolean running = false;
    private boolean clicked = false;
    private boolean scaled = false;

    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;
    private int m = 10;

    Canvas drawing;
    Bitmap bmp;

    public GameSurfaceThread(SurfaceHolder surfaceHolder, GameSurface surfaceView) {
        mSurfaceHolder = surfaceHolder;
        mGameSurface = surfaceView;

        // загружаем картинку, которую будем отрисовывать
        picture = BitmapFactory.decodeResource(surfaceView.getResources(), android.R.drawable.ic_media_play);

        // формируем матрицу преобразований для картинки
        matrix = new Matrix();
        matrix.postScale(3.0f, 3.0f);

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    public void centerMatrix(int width, int height) {
        matrix = new Matrix();
        matrix.postScale(3.0f, 3.0f);
        matrix.postTranslate((width - picture.getWidth()*3) / 2, (height - picture.getHeight()*3 ) / 2);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public void run() {
        while(running){

            // получаем текущее время и вычисляем разницу с предыдущим
            // сохраненным моментом времени
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30 && picture != null){
                // если прошло больше 30 миллисекунд - сохраним текущее время
                // и повернем картинку на 2 градуса.
                // точка вращения - центр картинки
                prevTime = now;
                matrix.preRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);

                if (scaled) {
                    matrix.preScale(0.5f, 0.5f, picture.getWidth() / 2, picture.getHeight() / 2);
                    scaled = false;
                }
            }
//          Если нажали - увеличиваем картинку на мгновение
            if (clicked) {
                matrix.preScale(2.0f, 2.0f, picture.getWidth() / 2, picture.getHeight() / 2);
//                mGameSurface.resetC();
                clicked = false;
//                scaled = true;
                m = 0;
            }
            m++;
            if (m == 3) {
//                clicked = false;
                scaled = true;
//                m = 0;
            }

            Canvas c = null;

            try{
                c = mSurfaceHolder.lockCanvas(null);
                synchronized (mSurfaceHolder){
                    mGameSurface.onDraw(c);

//                    c.drawColor(Color.BLACK);
//                    drawing.drawARGB(10, 0, 0, 0);
//                    drawing.drawColor(Color.TRANSPARENT);
                    c.drawBitmap(picture, matrix, null);
//                    drawing.drawBitmap(erasebitmap, width, height, null);
//                    c.drawBitmap(bmp, 0, 0, null);
                }
//                sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}