package com.nhatminh.example.customview.curveprogressdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class CurveSeekBar extends CurveProgressBar {

    private static final String TAG = "CurveSeekBar";

    private Drawable thumb;
    private int thumbSize;
    private boolean isThumbSelected = false;
    private float thumbX;
    private float thumbY;

    Paint backgroundPaint;

    public CurveSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        thumb = context.getDrawable(R.drawable.thumb);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(5);
        backgroundPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(backgroundRect, backgroundPaint);

        drawThumb(canvas);
    }

    private void drawThumb(Canvas canvas){
        thumbX = getThumbXPos();
        thumbY = getThumbYPos();

        thumbSize = (int) (getRadius() / 18);

        int left = (int) (thumbX - thumbSize);
        int top = (int) (thumbY - thumbSize);
        int right = (int) (thumbX + thumbSize);
        int bottom = (int) (thumbY + thumbSize);


        thumb.setBounds(left, top , right , bottom);
        thumb.draw(canvas);
    }

    private float getRadius(){
        int width = getWidth();
        int height = getHeight();

        return width < height ? width * 0.5f : height * 0.5f;
    }


    private float getThumbYPos(){
        int currentAngle = getCurrentProgressAngle();
        float radius = getRadius() - curvePaint.getStrokeWidth();

        if (currentAngle <= 45){
            return (float) (getHeight() / 2 + radius * Math.sin(Math.toRadians(45 - currentAngle)));
        }

        else if(currentAngle <= 135){
            int alphaAngle = currentAngle - 45;
            return (float) (getHeight() / 2 - radius * Math.sin(Math.toRadians(alphaAngle)));

        }

        else if(currentAngle <= 225){
            int alphaAngle = 225 - currentAngle;
            return (float) (getHeight() / 2 - radius * Math.sin(Math.toRadians(alphaAngle)));

        }
        else {
            int alphaAngle = currentAngle - 225;
            return (float) (getHeight() / 2 + radius * Math.sin(Math.toRadians(alphaAngle)));
        }

    }

    private float getThumbXPos(){
        int currentAngle = getCurrentProgressAngle();
        float radius = getRadius() - curvePaint.getStrokeWidth();

        if (currentAngle <= 45){
            return (float) (getWidth() / 2 - radius * Math.cos(Math.toRadians(45 - currentAngle)));
        }

        else if(currentAngle <= 135){
            int alphaAngle = currentAngle - 45;
            return (float) (getWidth() / 2 - radius * Math.cos(Math.toRadians(alphaAngle)));
        }

        else if(currentAngle <= 225){
            int alphaAngle = 225 - currentAngle;
            return (float) (getWidth() / 2 + radius * Math.cos(Math.toRadians(alphaAngle)));
        }

        else {
            int alphaAngle = currentAngle - 225;
            return (float) (getWidth() / 2 + radius * Math.cos(Math.toRadians(alphaAngle)));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        /*Log.d(TAG, "X: " + eventX + " Y: " + eventY);
        Log.d(TAG, "Width: " +  backgroundRect.width() + " Height: " + backgroundRect.height());
        Log.d(TAG, "CenterX: " + backgroundRect.centerX() + " CenterY: " + backgroundRect.centerY());*/

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if (isTouchInThumbBounds(eventX, eventY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    isThumbSelected = true;

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isThumbSelected && isTouchInBarBound(eventX, eventY)){
                    updateState(eventX, eventY);
                }
                return true;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                isThumbSelected = false;
                return true;
            default:
                return false;

        }
    }

    private boolean isTouchInThumbBounds(float x, float y){
        if (x < thumbX + thumbSize && x > thumbX - thumbSize
        && y < thumbY + thumbSize && y > thumbY - thumbSize){
            return true;
        }
        return false;
    }

    private boolean isTouchInBarBound(float x, float y){
        float centerX = getWidth() * 0.5f;
        float centerY = getHeight() * 0.5f;

        int baseMargin = 20;

        float distance = (float) findDistanceBetweenTwoPoints(centerX, centerY, x, y);
        if (distance > getRadius() - baseMargin &&distance < getRadius() + baseMargin){
            return true;
        }
        return false;
    }

    private double findDistanceBetweenTwoPoints(float x1, float y1, float x2, float y2){
        return Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2));

    }

    private void updateState(float newX, float newY){
        float width = getWidth();
        float height = getHeight();

        float basePointX = (float) (width / 2 - getRadius() * Math.cos(Math.toRadians(45)));
        float basePointY = (float) (height / 2 + getRadius() * Math.sin(Math.toRadians(45)));

        float distance = (float) findDistanceBetweenTwoPoints(basePointX, basePointY, newX, newY);
        double degrees = Math.toDegrees(Math.acos(1 - Math.pow(distance, 2) / (2 * Math.pow(getRadius(), 2))));

        if(newX > width / 2){
            degrees = 270 - degrees;
        }

        setProgress( (int) (degrees * 100 /270));
    }

    @Override
    public void setProgress(int progress) {
        super.setProgress(progress);
    }
}
