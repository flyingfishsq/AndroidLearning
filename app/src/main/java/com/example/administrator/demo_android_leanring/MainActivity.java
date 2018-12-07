package com.example.administrator.demo_android_leanring;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo_android_leanring.Bean.TrueFalse;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";

    private Context mContext;
    private TextView tv_question;
    private Button btn_left, btn_right;
    private Button btn_cheat;
    private ImageButton btn_previous, btn_next;
    private MyOnClickListener myOnClickListener;

    private int mCurrentIndex = 0;

    private final TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate() called");
        mContext = this;

        /**
         *这段代码表示，1：findViewById是activity的成员方法
         * 2：findViewById方法返回的是个View类型的视图对象
         */
//        Activity activity = (Activity) mContext;
//        View viewById = activity.findViewById(R.id.btn_right);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        myOnClickListener = new MyOnClickListener();
        tv_question = (TextView) findViewById(R.id.tv_question);
        updateQuestion();

        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
        btn_previous = (ImageButton) findViewById(R.id.btn_previous);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        btn_left.setOnClickListener(myOnClickListener);
        btn_right.setOnClickListener(myOnClickListener);
        btn_previous.setOnClickListener(myOnClickListener);
        btn_next.setOnClickListener(myOnClickListener);

        btn_cheat = (Button) findViewById(R.id.btn_cheat);
        btn_cheat.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_left:
                    /**
                     *这里括号内的第二个参数是个资源ID，不同于常用的传入具体的string
                     */
                    checkAnswer(true);
                    break;
                case R.id.btn_right:
                    checkAnswer(false);
                    break;
                case R.id.btn_previous:
                    mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                    updateQuestion();
                    break;
                case R.id.btn_next:
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                    break;
                case R.id.btn_cheat:
                    Intent intent = new Intent(mContext, CheatActivity.class);
                    //activity调用startActivity(...)方法时，调用请求发给了操作系统
                    //该方法调用请求是发送给操作系统的ActivityManager，
                    //ActivityManager负责创建Activity实例并调用其onCreate(...)方法
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void updateQuestion() {
        //以抛异常的输出方式，跟踪一个方法，与在这里写个每次调用即打印输出语句的意义相似，
        // 但是更明显，能够跟踪到方法调用的栈
//        Log.e(TAG, "Updating question text for question # " + mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        tv_question.setText(question);
    }

    private void checkAnswer(boolean isTrue) {
        boolean trueQuestion = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;
        if (isTrue == trueQuestion) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param savedInstanceState 为了旋转屏幕会被刷新的数据做个保存
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.e(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}
