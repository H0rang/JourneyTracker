package com.example.horang.journeytracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by horang on 20/04/16.
 */
public class TrackerView extends View{
    private int viewHeight;
    private int viewWidth;

    public TrackerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint p = new Paint(0);

        init(p, canvas);
    }

    public void init(Paint p, Canvas canvas){
        p.setColor(Color.BLACK);

        canvas.drawRect(0, 0, viewWidth, viewHeight, p);

        p.setColor(Color.WHITE);
        canvas.drawLine(0, viewHeight / 6, viewWidth, viewHeight / 6, p);
        canvas.drawLine(0,(viewHeight/6)*2, viewWidth, (viewHeight/6)*2, p);
        canvas.drawLine(0,(viewHeight/6)*3, viewWidth, (viewHeight/6)*3, p);
        canvas.drawLine(0,(viewHeight/6)*4, viewWidth, (viewHeight/6)*4, p);
        canvas.drawLine(0,(viewHeight/6)*5, viewWidth, (viewHeight/6)*5, p);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        if(xNew < yNew){
            viewWidth = xNew;
            viewHeight = xNew;
        }
        else
        {
            viewWidth = yNew;
            viewHeight = yNew;
        }
    }
}
