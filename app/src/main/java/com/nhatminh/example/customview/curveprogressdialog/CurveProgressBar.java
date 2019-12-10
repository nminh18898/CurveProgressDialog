package com.nhatminh.example.customview.curveprogressdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;

public class CurveProgressBar extends View {
    // Default value
    private static final int DEFAULT_PROGRESS_COLOR = Color.RED;
    private static final int DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    // user's value (default if not set by user)
    private int progressColor;
    private int progressBackgroundColor;
    private int textColor;

    // paint
    Paint textPaint;
    Paint curvePaint;
    Paint centerPaint;

    int progress = 0;
    private static int MAX_PROGRESS = 100;
    private static int MAX_ANGLE = 270;
    private static String MAX_PROGRESS_STRING = "100%";

    protected RectF backgroundRect;

    public CurveProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CurveProgressBar,
                0, 0);

        progressColor = typedArray.getColor(R.styleable.CurveProgressBar_progressColor, DEFAULT_PROGRESS_COLOR);
        progressBackgroundColor = typedArray.getColor(R.styleable.CurveProgressBar_progressBackgroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR);
        textColor = typedArray.getColor(R.styleable.CurveProgressBar_textColor, DEFAULT_TEXT_COLOR);


        typedArray.recycle();

        // text paint for number (% complete)
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setColor(textColor);

        // curve paint for progress bar
        curvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curvePaint.setStyle(Paint.Style.STROKE);

        // rect hold progress bar
        backgroundRect = new RectF();
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
              int widthSize = MeasureSpec.getSize(widthMeasureSpec);

              int heightMode = MeasureSpec.getMode(heightMeasureSpec);
              int heightSize = MeasureSpec.getSize(heightMeasureSpec);

              int desiredWidth = widthSize;
              int desiredHeight = heightSize;

              int baseWidth = getResources().getDisplayMetrics().widthPixels / 2;
              int baseHeight = baseWidth;

              if (widthMode == MeasureSpec.UNSPECIFIED || (widthMode == MeasureSpec.AT_MOST && baseWidth < widthSize)){
                  desiredWidth = baseWidth;
              }

              if (heightMode == MeasureSpec.UNSPECIFIED || (heightMode == MeasureSpec.AT_MOST && baseHeight < heightSize)){
                  desiredHeight = baseHeight;
              }

              setMeasuredDimension(
                      resolveSize(desiredWidth, widthMeasureSpec),
                      resolveSize(desiredHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        int minEdge = Math.min(width, height);

        // Calculate horizontal and vertical center.
        float centerX = width * 0.5f;
        float centerY = height * 0.5f;

        curvePaint.setStrokeWidth(minEdge / (108 / getResources().getDisplayMetrics().density));
        //curvePaint.setStrokeWidth(1);

        float baseMargin = curvePaint.getStrokeWidth();

        backgroundRect.set(centerX - minEdge / 2 + baseMargin,
                centerY - minEdge / 2 + baseMargin,
                centerX + minEdge / 2 - baseMargin,
                centerY + minEdge / 2 - baseMargin);

        // draw current progress
        int currentProgressAngle = getCurrentProgressAngle();
        curvePaint.setColor(progressColor);
        canvas.drawArc(backgroundRect, 135, currentProgressAngle, false, curvePaint);

        // draw the rest of progress bar
        curvePaint.setColor(progressBackgroundColor);
        canvas.drawArc(backgroundRect, 135 + currentProgressAngle, MAX_ANGLE - currentProgressAngle, false, curvePaint);

        // draw text
        textPaint.setTextSize(minEdge / (36 / getResources().getDisplayMetrics().density));
        float textWidth = textPaint.measureText(progress+"%");
        float textX = Math.round(centerX - textWidth * 0.5f);
        float textY = minEdge * 0.9f;
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(progress+"%", textX, textY, textPaint);

    }

    public int getMaxAngle(){
        return MAX_ANGLE;
    }

    public int getCurrentProgressAngle(){
        return progress * MAX_ANGLE / 100;
    }



    public void setProgress(int progress){
        if (progress < 0){
            progress = 0;
        }

        if (progress > MAX_PROGRESS){
            progress = MAX_PROGRESS;
        }

        this.progress = progress;
        invalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public int getProgressBackgroundColor() {
        return progressBackgroundColor;
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.progressBackgroundColor = progressBackgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getProgress(){ return progress;}
}
