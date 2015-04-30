package ru.firsto.yac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceHolder;

/**
 * Created by razor on 29.04.15.
 */

public class GameSurfaceThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private GameSurface mGameSurface;
    private boolean myThreadRun = false;

    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;

    public GameSurfaceThread(SurfaceHolder surfaceHolder, GameSurface surfaceView) {
        mSurfaceHolder = surfaceHolder;
        mGameSurface = surfaceView;

        // загружаем картинку, которую будем отрисовывать
        picture = BitmapFactory.decodeResource(surfaceView.getResources(), android.R.drawable.ic_media_play);

        // формируем матрицу преобразований для картинки
        matrix = new Matrix();
        matrix.postScale(3.0f, 3.0f);
        matrix.postTranslate(100.0f, 100.0f);

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(myThreadRun){

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
            }

            Canvas c = null;

            try{
                c = mSurfaceHolder.lockCanvas(null);
                synchronized (mSurfaceHolder){
                    mGameSurface.onDraw(c);

//                    c.drawColor(Color.BLACK);
                    c.drawBitmap(picture, matrix, null);
                }
//                sleep(1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
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