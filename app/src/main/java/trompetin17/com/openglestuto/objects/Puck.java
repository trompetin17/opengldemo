package trompetin17.com.openglestuto.objects;

import java.util.List;

import trompetin17.com.openglestuto.data.VertexArray;
import trompetin17.com.openglestuto.programs.ColorShaderProgram;
import trompetin17.com.openglestuto.util.Geometry;

/**
 * Created by JG on 24/07/17.
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawlist;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPuck(
                new Geometry.Cylinder(
                        new Geometry.Point(0f, 0f, 0f),
                        radius,
                        height
                ),
                numPointsAroundPuck
        );

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawlist = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawlist) {
            drawCommand.draw();
        }
    }
}
