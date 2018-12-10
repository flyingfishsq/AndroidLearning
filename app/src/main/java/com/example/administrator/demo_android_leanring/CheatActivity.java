package com.example.administrator.demo_android_leanring;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private TextView tv_answer;
    private Button btn_show_answer;

    private boolean mAnswerIsTrue;
    private MyOnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);

        myOnClickListener = new MyOnClickListener();
        tv_answer = (TextView) findViewById(R.id.tv_answer);
        btn_show_answer = (Button) findViewById(R.id.btn_show_answer);
        btn_show_answer.setOnClickListener(myOnClickListener);

        mAnswerIsTrue = getIntent().getBooleanExtra(Config.EXTRA_ANSWER_IS_TRUE, false);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (mAnswerIsTrue) {
                tv_answer.setText(R.string.true_button);
            } else {
                tv_answer.setText(R.string.false_button);
            }
        }
    }
}
