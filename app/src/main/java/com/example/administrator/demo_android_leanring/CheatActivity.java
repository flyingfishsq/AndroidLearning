package com.example.administrator.demo_android_leanring;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private TextView tv_answer;
    private Button btn_show_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);
123
        tv_answer = (TextView) findViewById(R.id.tv_answer);
        btn_show_answer = (Button)findViewById(R.id.btn_show_answer);
    }
}
