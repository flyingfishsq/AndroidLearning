package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {

    public final String TAG = CrimeCameraFragment.class.getSimpleName();

    private SurfaceView surfaceview_camera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);

        Button btn_take_pic = view.findViewById(R.id.btn_take_pic);
        btn_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        surfaceview_camera = view.findViewById(R.id.surfaceview_camera);

        return view;
    }
}
