package com.mycalculator.WiFiDataAcquisition.openGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lenovo on 2017/4/27.
 */
public class Axis {
    // Our vertices.
    private float vertices[] = {
            0.0f,  0.0f, 0.0f,  // 0, origin
            3.0f, 0.0f, 0.0f,  // 1, x
            0.0f, 3.0f, 0.0f,  // 2, y
            0.0f,  0.0f, 6.0f,  // 3, z
    };
   /* 2.0f, 0.0f, 0.0f,  // 4, x
            1.0f, 0.0f, 0.0f,  // 5, x
            2.0f, 3.0f, 0.0f,  // 6, x
            1.0f, 3.0f, 0.0f,  // 7, x*/

    // The order we like to connect them.
    private short[] indices = { 1,0, 2,0,3,0 ,6,4,5,7};

    // Our vertex buffer.
    private FloatBuffer vertexBuffer;

    // Our index buffer.
    private ShortBuffer indexBuffer;

    public Axis() {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * This function draws our square on screen.
     * @param gl
     */
    public void draw(GL10 gl) {
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK); // OpenGL docs


        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, // OpenGL docs
                vertexBuffer);

        gl.glDrawElements(GL10.GL_LINE_STRIP, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
