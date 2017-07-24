package trompetin17.com.openglestuto;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import trompetin17.com.openglestuto.interfaces.IDrawObject;
import trompetin17.com.openglestuto.objects.Mallet;
import trompetin17.com.openglestuto.objects.Puck;
import trompetin17.com.openglestuto.objects.Table;
import trompetin17.com.openglestuto.programs.ColorShaderProgram;
import trompetin17.com.openglestuto.programs.TextureShaderProgram;
import trompetin17.com.openglestuto.util.Geometry;
import trompetin17.com.openglestuto.util.ShaderHelper;
import trompetin17.com.openglestuto.util.TextResourceReader;
import trompetin17.com.openglestuto.util.TextureHelper;

/**
 * Created by JG on 23/07/17.
 */

public class AirHockey implements IDrawObject {
    private final float[] mModelMatrix = new float[16];
    private final float[] mModelViewProjectionMatrix = new float[16];
    private final float[] mInvertedProjectionMatrix = new float[16];

    private Table mTable;
    private Mallet mMallet;
    private Puck mPuck;

    private TextureShaderProgram mTextureProgram;
    private ColorShaderProgram mColorProgram;

    private int mTexture;

    private boolean mMalletPressed = false;
    private Geometry.Point mBlueMalletPosition;

    public AirHockey(Context context) {
        mTable = new Table();
        mMallet = new Mallet(0.08f, 0.15f, 32);
        mPuck = new Puck(0.06f, 0.02f, 32);

        mTextureProgram = new TextureShaderProgram(context);
        mColorProgram = new ColorShaderProgram(context);

        mTexture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);

        mBlueMalletPosition = new Geometry.Point(0f, mMallet.height / 2f, 0.4f);
    }

    @Override
    public void onDraw(float[] viewProjectionMatrix) {
        Matrix.invertM(mInvertedProjectionMatrix, 0, viewProjectionMatrix, 0);

        positionTableInScene(viewProjectionMatrix);
        mTextureProgram.useProgram();
        mTextureProgram.setUniforms(mModelViewProjectionMatrix, mTexture);
        mTable.bindData(mTextureProgram);
        mTable.draw();

        positionObjectInScene(viewProjectionMatrix, 0f, mMallet.height / 2f, -0.4f);
        mColorProgram.useProgram();
        mColorProgram.setUniforms(mModelViewProjectionMatrix, 1f, 0f, 0f);
        mMallet.bindData(mColorProgram);
        mMallet.draw();

        positionObjectInScene(viewProjectionMatrix, 0f, mMallet.height / 2f, 0.4f);
        mColorProgram.setUniforms(mModelViewProjectionMatrix, 0f, 0f, 1f);
        mMallet.draw();

        positionObjectInScene(viewProjectionMatrix, 0f, mPuck.height / 2f, 0f);
        mColorProgram.setUniforms(mModelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        mPuck.bindData(mColorProgram);
        mPuck.draw();

    }

    private void positionObjectInScene(float[] viewProjectionMatrix, float x, float y, float z) {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, x, y, z);
        Matrix.multiplyMM(mModelViewProjectionMatrix, 0, viewProjectionMatrix, 0, mModelMatrix, 0);
    }

    private void positionTableInScene(float[] viewProjectionMatrix) {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.rotateM(mModelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(mModelViewProjectionMatrix, 0, viewProjectionMatrix, 0, mModelMatrix, 0);
    }

    @Override
    public void onSurfaceChanged() {
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(mModelMatrix, 0, -60f, 1f, 0f, 0f);
    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
        Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);

        Geometry.Sphere malletBoundingSphere = new Geometry.Sphere(
                new Geometry.Point(
                        mBlueMalletPosition.x,
                        mBlueMalletPosition.y,
                        mBlueMalletPosition.z),
                mMallet.height / 2
        );

        mMalletPressed = Geometry.intersects(malletBoundingSphere, ray);
    }

    private Geometry.Ray convertNormalized2DPointToRay(float normalizedX, float normalizedY) {
        final float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        final float[] farPointNdc = {normalizedX, normalizedY, 1, 1};

        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];

        Matrix.multiplyMV(nearPointWorld, 0, mInvertedProjectionMatrix, 0, nearPointNdc, 0);
        Matrix.multiplyMV(farPointWorld, 0, mInvertedProjectionMatrix, 0, farPointNdc, 0);

        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Geometry.Point nearPointRay = new Geometry.Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Geometry.Point farPointRay = new Geometry.Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);

        return new Geometry.Ray(nearPointRay, Geometry.vectorBetween(nearPointRay, farPointRay));
    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {

    }
}
