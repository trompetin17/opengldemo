package trompetin17.com.openglestuto;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by JG on 23/07/17.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    private AirHockey mAirHockey;
    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 Context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer(context);

        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            final float normalizedX =
                    (event.getX() / (float) getWidth()) * 2 - 1;
            final float normalizedY =
                    -((event.getY() / (float) getHeight()) * 2 - 1);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        mAirHockey.handleTouchPress(normalizedX, normalizedY);
                    }
                });
            } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                this.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        mAirHockey.handleTouchDrag(normalizedX, normalizedY);
                    }
                });
            }
            return true;
        }
        return false;
    }
}
