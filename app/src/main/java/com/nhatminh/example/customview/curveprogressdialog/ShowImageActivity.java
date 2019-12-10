package com.nhatminh.example.customview.curveprogressdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowImageActivity extends AppCompatActivity {

    RatioImageView ratioImageView;
    TextView tvSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        ratioImageView = findViewById(R.id.ratioImageView);
        tvSize = findViewById(R.id.tvSize);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            int width = ratioImageView.getWidth();
            int height = ratioImageView.getHeight();
            float ratio = (float) width / height;
            tvSize.setText("Width: " + width + " - Height: " + height + " - Ratio: " + ratio);
        }
    }
}
