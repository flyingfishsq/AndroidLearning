package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private Crime mCrime;
    private EditText et_crime_title;
    private Button btn_crime_date;
    private CheckBox cb_crime_solved;

    private final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    //这个启动Fragment的方法非常重要
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);

        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //简单直接方法获取extra，跳转至宿主Activity的intent
//        Intent intent = getActivity().getIntent();
//        UUID crimeId = (UUID) intent.getSerializableExtra(EXTRA_CRIME_ID);

        //复杂灵活方法获取extra，在Fragment创建的时候已经能获取到bundle数据了
        //等于是在构造方法中拿到了bundle数据
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    //通过该方法生成fragment视图的布局，然后将生成的View返回给托管activity
    //Fragment.onCreateView(...)方法中的组件引用几乎等同于Activity.OnCreate(...)方法的处理
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        et_crime_title = v.findViewById(R.id.et_crime_title);
        btn_crime_date = v.findViewById(R.id.btn_crime_date);
        cb_crime_solved = v.findViewById(R.id.cb_crime_solved);
        et_crime_title.setText(mCrime.getTitle());
        et_crime_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_crime_date.setText(df.format(mCrime.getDate()));
        btn_crime_date.setEnabled(false);
        cb_crime_solved.setChecked(mCrime.getSolved());
        cb_crime_solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
}