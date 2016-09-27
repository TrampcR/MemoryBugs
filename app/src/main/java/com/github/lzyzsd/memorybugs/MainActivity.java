package com.github.lzyzsd.memorybugs;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LINE_TAG = "-------: ";
    private Rect mRect;
    private String mLogString;

    private TextView mTextView;
    private Button mStartBButton;
    private Button mStartAllocationButton;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRect = new Rect(0, 0, 100, 100);
        mLogString = LINE_TAG + mRect.width();

        mTextView = (TextView) findViewById(R.id.tv_text);
        mTextView.setText(R.string.hello_world);

        mStartBButton = (Button) findViewById(R.id.btn_start_b);
        mStartBButton.setOnClickListener(this);

        mStartAllocationButton = (Button) findViewById(R.id.btn_allocation);
        mStartAllocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_b:
                startB();
                break;
            case R.id.btn_allocation:
                startAllocationLargeNumbersOfObjects();
                break;
        }
    }

    private void startB() {
        finish();
        startActivity(new Intent(this, ActivityB.class));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("post delayed may leak");
            }
        }, 5000);
        Toast.makeText(this, R.string.leakmemory, Toast.LENGTH_SHORT).show();
    }

    private void startAllocationLargeNumbersOfObjects() {
        Toast.makeText(this, R.string.memory_monitor, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 10000; i++) {
            System.out.println(mLogString);
        }
    }
}
