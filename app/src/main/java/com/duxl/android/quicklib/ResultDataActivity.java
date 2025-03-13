package com.duxl.android.quicklib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultDataActivity extends AppCompatActivity {

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_data);
        tvTime = findViewById(R.id.tv_time);
        tvTime.setText("系统当前时间=" + System.currentTimeMillis());
    }

    public void backAndFinish(View view) {
        Intent intent = new Intent();
        intent.putExtra("data", tvTime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}