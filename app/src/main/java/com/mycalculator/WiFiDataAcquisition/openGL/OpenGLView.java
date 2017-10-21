package com.mycalculator.WiFiDataAcquisition.openGL;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by lenovo on 2017/4/13.
 */
public class OpenGLView extends GLSurfaceView {
    private MyRender myRender;

    public OpenGLView(Context context) {
        super(context);
        myRender = new MyRender();
        setRenderer(myRender);
    }
}
