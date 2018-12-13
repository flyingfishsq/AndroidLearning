package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fl_container);

        if (fragment == null) {
            fragment = new CrimeFragment();
            //FragmentTransaction类使用了一个fluent interface接口方法
            //创建一个新的fragment事务，加入一个添加操作，然后提交新事务
            //这里的容器视图资源ID有两个作用，一是告知FragmentManager，fragment应该出现在activity视图的什么地方
            //二是用来作为FragmentManager队列中fragment的唯一标识符
            fm.beginTransaction().add(R.id.fl_container, fragment).commit();
        }
    }
}
