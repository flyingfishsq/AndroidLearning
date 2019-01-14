package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class CrimeCameraActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏等
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //创建activity视图前，就必须调用隐藏标题栏和状态栏的方法
        super.onCreate(savedInstanceState);
    }
}
