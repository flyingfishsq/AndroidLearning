package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    //子类可以选择覆盖这个方法以返回所需布局
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentById = fm.findFragmentById(R.id.fl_container);

        if(fragmentById == null){
            fragmentById = createFragment();
            fm.beginTransaction().add(R.id.fl_container, fragmentById).commit();
        }

    }
}
