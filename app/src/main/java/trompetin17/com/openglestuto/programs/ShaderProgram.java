package trompetin17.com.openglestuto.programs;

import android.content.Context;
import android.opengl.GLES20;

import trompetin17.com.openglestuto.util.ShaderHelper;
import trompetin17.com.openglestuto.util.TextResourceReader;
import trompetin17.com.openglestuto.util.TextureHelper;

/**
 * Created by JG on 23/07/17.
 */

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourcesId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourcesId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));

    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }

}
