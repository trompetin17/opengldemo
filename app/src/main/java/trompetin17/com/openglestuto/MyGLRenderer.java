package trompetin17.com.openglestuto;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import trompetin17.com.openglestuto.interfaces.IDrawObject;
import trompetin17.com.openglestuto.util.MatrixHelper;

/**
 * Created by JG on 23/07/17.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private IDrawObject mCurrentObject;
    private Context mContext;
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mViewProjectionMatrix = new float[16];

    public MyGLRenderer(Context context) {
        mContext = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mCurrentObject = new AirHockey(mContext);
    }

    public IDrawObject getCurrentObject() {
        return mCurrentObject;
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(mProjectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f,1f, 0f);
        mCurrentObject.onSurfaceChanged();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(mViewProjectionMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        mCurrentObject.onDraw(mViewProjectionMatrix);
    }
}
