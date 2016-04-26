package com.example.horang.journeytracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
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

        if(MainActivity.active){
            p.setColor(Color.RED);
            int n = 60 - (int)MainActivity.Q.getAverageSpeed();
            if(n < 0)
                n = 0;
            int height = viewHeight / 60;
            canvas.drawLine(0, height * n, viewWidth, height * n, p);

            p.setColor(Color.GREEN);
            CircularQueue queue = MainActivity.Q;
            Location[] loclist = queue.getQueue();
            for(int i = 0; i < queue.getSize() - 1; i++){
                int width1 = (viewWidth/queue.getSize() + 1)*i;
                int width2 = (viewWidth/queue.getSize() + 1)*(i + 1);
                float height1 = height * (60 - loclist[(queue.getHead() + i) % 30].getSpeed() * 3.6f);
                float height2 = height * (60 - loclist[(queue.getHead() + i + 1) % 30].getSpeed() * 3.6f);
                canvas.drawLine(width1, height1, width2, height2, p);
            }
        }

        invalidate();
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
