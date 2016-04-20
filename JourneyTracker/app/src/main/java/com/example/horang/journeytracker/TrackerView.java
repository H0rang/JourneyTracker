package com.example.horang.journeytracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by horang on 20/04/16.
 */
public class TrackerView extends View{
    public TrackerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.TrackerView, 0, 0);

        
    }
}
