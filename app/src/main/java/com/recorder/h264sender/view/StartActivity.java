package com.recorder.h264sender.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.recorder.h264sender.R;


public class StartActivity extends AppCompatActivity{

    private AppCompatButton btnRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnRecord = findViewById(R.id.btn_test_record);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ScreenRecordVC.class);
                startActivity(intent);
            }
        });
    }

}
