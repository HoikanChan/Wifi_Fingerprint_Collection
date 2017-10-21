package com.mycalculator.WiFiDataAcquisition.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.mycalculator.WiFiDataAcquisition.R;

public class MapView extends RelativeLayout{

    private int row;
    private int column;


    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MapView);
        row = ta.getInt(R.styleable.MapView_row,0);
        column = ta.getInt(R.styleable.MapView_column,0);
        ta.recycle();
    }
}
