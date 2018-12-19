package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {
    private final String TAG = CrimeListFragment.class.getSimpleName();
    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);

        setRetainInstance(true);
        mSubtitleVisible = false;

        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    //选项菜单的回调方法
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem item = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && item != null) {
            item.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);

                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    getActivity().getActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime item = ((CrimeAdapter) getListAdapter()).getItem(position);
//        Crime item = (Crime) (getListAdapter().getItem(position));
//        Intent intent = new Intent(getActivity(), CrimeActivity.class);
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);

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
