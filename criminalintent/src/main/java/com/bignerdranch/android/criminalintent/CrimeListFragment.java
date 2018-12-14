package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {
    private final String TAG = CrimeListFragment.class.getSimpleName();
    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);

        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime item = ((CrimeAdapter) getListAdapter()).getItem(position);
//        Crime item = (Crime) (getListAdapter().getItem(position));
        Intent intent = new Intent(getActivity(), CrimeActivity.class);
        //UUID是Serializable对象
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, item.getId());
        startActivity(intent);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        //构造方法只绑定Crime对象的数组列表
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime item = getItem(position);

            TextView tv_title = convertView.findViewById(R.id.tv_title);
            TextView tv_date = convertView.findViewById(R.id.tv_date);
            CheckBox cb_item_solved = convertView.findViewById(R.id.cb_item_solved);

            tv_title.setText(item.getTitle());
            tv_date.setText(item.getDate().toString());
            cb_item_solved.setChecked(item.getSolved());

            return convertView;
        }
    }
}
