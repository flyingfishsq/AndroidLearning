package com.bignerdranch.android.criminalintent;

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

import java.text.DateFormat;

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText et_crime_title;
    private Button btn_crime_date;
    private CheckBox cb_crime_solved;

    private final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    //通过该方法生成fragment视图的布局，然后将生成的View返回给托管activity
    //Fragment.onCreateView(...)方法中的组件引用几乎等同于Activity.OnCreate(...)方法的处理
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        et_crime_title = v.findViewById(R.id.et_crime_title);
        btn_crime_date = v.findViewById(R.id.btn_crime_date);
        cb_crime_solved = v.findViewById(R.id.cb_crime_solved);
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
        cb_crime_solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
}
