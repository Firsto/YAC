package ru.firsto.yac;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by razor on 29.04.15.
 */

public class GameSurfaceThread extends Thread {
    private SurfaceHolder myThreadSurfaceHolder;
    private GameSurface myThreadSurfaceView;
    private boolean myThreadRun = false;

    public GameSurfaceThread(SurfaceHolder surfaceHolder, GameSurface surfaceView) {
        myThreadSurfaceHolder = surfaceHolder;
        myThreadSurfaceView = surfaceView;
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(myThreadRun){
            Canvas c = null;

            try{
                c = myThreadSurfaceHolder.lockCanvas(null);
                synchronized (myThreadSurfaceHolder){
                    myThreadSurfaceView.onDraw(c);
                }
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    myThreadSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}