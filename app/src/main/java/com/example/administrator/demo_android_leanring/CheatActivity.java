package com.example.administrator.demo_android_leanring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private static final String TAG = CheatActivity.class.getSimpleName();
    private static final String KEY_ANSWER = "question_answer";
    private static final String KEY_HAS_CHEAT = "has_cheat";

    private TextView tv_answer;
    private Button btn_show_answer;

    private boolean mAnswerIsTrue;
    private boolean mHasCheat;
    private MyOnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);

        myOnClickListener = new MyOnClickListener();
        tv_answer = findViewById(R.id.tv_answer);
        btn_show_answer = findViewById(R.id.btn_show_answer);
        btn_show_answer.setOnClickListener(myOnClickListener);

        if (savedInstanceState != null) {
            mHasCheat = savedInstanceState.getBoolean(KEY_HAS_CHEAT, false);
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER, false);
            if(mHasCheat){
                setTvAnswer(mAnswerIsTrue);
            }
        }else{
            mAnswerIsTrue = getIntent().getBooleanExtra(Config.EXTRA_ANSWER_IS_TRUE, false);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_HAS_CHEAT, mHasCheat);
        outState.putBoolean(KEY_ANSWER, mAnswerIsTrue);
        Log.e(TAG, "onSaveInstanceState" + "---mHasCheat = " + mHasCheat, new Throwable());
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            setTvAnswer(mAnswerIsTrue);
            mHasCheat = true;
            setAnswerShownResult(mHasCheat);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent intent = new Intent();
        intent.putExtra(Config.EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    private void setTvAnswer(boolean bool){
        if (bool) {
            tv_answer.setText(R.string.true_button);
        } else {
            tv_answer.setText(R.string.false_button);
        }
    }
}
