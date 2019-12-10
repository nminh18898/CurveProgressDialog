package com.nhatminh.example.customview.curveprogressdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    Button btnIncrease, btnDecrease;
    Button btnIncrease2, btnDecrease2;
    CurveProgressBar curveProgressBar, progressBarTestColor;
    int progress = 50;

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        curveProgressBar = findViewById(R.id.curveProgressBar);
        progressBarTestColor = findViewById(R.id.progressBarTestColor);

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress+=10;
                curveProgressBar.setProgress(progress);
                progress = curveProgressBar.getProgress();
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress-=10;
                curveProgressBar.setProgress(progress);
                progress = curveProgressBar.getProgress();
            }
        });


        btnIncrease2 = findViewById(R.id.btnIncrease2);
        btnDecrease2 = findViewById(R.id.btnDecrease2);

        btnIncrease2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress+=10;
                progressBarTestColor.setProgress(progress);
                progress = progressBarTestColor.getProgress();
            }
        });

        btnDecrease2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress-=10;
                progressBarTestColor.setProgress(progress);
                progress = progressBarTestColor.getProgress();
            }
        });

        progressBarTestColor.setProgress(progress);

        curveProgressBar.setProgress(progress);

    }
}
