package com.nhatminh.example.customview.curveprogressdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RatioImageView extends AppCompatImageView {
    private static final float DEFAULT_RATIO = -1;
    private static final boolean DEFAULT_AUTO_SCALE_HEIGHT = false;
    private static final boolean DEFAULT_AUTO_SCALE_WIDTH = false;

    float ratio;
    boolean autoScaleHeight;
    boolean autoScaleWidth;

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RatioImageView,
                0, 0);

        ratio = typedArray.getFloat(R.styleable.RatioImageView_ratio, DEFAULT_RATIO);
        autoScaleWidth = typedArray.getBoolean(R.styleable.RatioImageView_autoScaleWidth, DEFAULT_AUTO_SCALE_WIDTH);
        autoScaleHeight = typedArray.getBoolean(R.styleable.RatioImageView_autoScaleHeight, DEFAULT_AUTO_SCALE_HEIGHT);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        int desiredWidth = 0;
        int desiredHeight = 0;


        if (widthMode == MeasureSpec.EXACTLY){
            desiredWidth = widthSize;
        }
        else {
            desiredWidth = getDrawable().getIntrinsicWidth();
        }


        if (heightMode == MeasureSpec.EXACTLY){
            desiredHeight = heightSize;
        }
        else {
            desiredHeight = getDrawable().getIntrinsicHeight();
        }

        if (ratio == DEFAULT_RATIO){

            float imageRatio = (float) getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();

            if (autoScaleWidth){
                desiredWidth = (int) (desiredHeight * imageRatio);
            }


            if (autoScaleHeight){
                desiredHeight = (int) (desiredWidth / imageRatio);
            }

            Log.e("default_ratio", "Width: " + desiredWidth + " - Height: " + desiredHeight);
        }

        else {

            if (desiredWidth > 0) {
                desiredHeight = (int) (desiredWidth / ratio);
            } else {
                desiredWidth = (int) (desiredHeight * ratio);
            }

        }

        setMeasuredDimension(desiredWidth, desiredHeight);
    }


    public void setRatio(float ratio) {
        this.ratio = ratio;
        invalidate();
    }

    public float getRatio() {
        return ratio;
    }
}
