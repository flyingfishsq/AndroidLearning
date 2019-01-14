package com.bignerdranch.android.criminalintent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class CrimeCameraFragment extends Fragment {

    public final String TAG = CrimeCameraFragment.class.getSimpleName();

    public static final String EXTRA_PHOTO_FILENAME = "com.bignerdranch.android.criminalintent.photo_filename";

    private Camera mCamera;
    private SurfaceView surfaceview_camera;

    private View crime_camera_progress_container;

    //实现Camera.ShutterCallback接口显示进度条视图
    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            crime_camera_progress_container.setVisibility(View.VISIBLE);
        }
    };

    //实现Camera.PictureCallback接口命名并保存已拍摄的JPEG图片文件
    private Camera.PictureCallback mJPEGCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //创建文件名
            String fileName = UUID.randomUUID().toString() + ".jpg";
            //把文件保存到存储器
            FileOutputStream os = null;
            boolean success = true;

            try {
                os = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                os.write(data);
            } catch (Exception ex) {
                Log.e(TAG, "Error writing to file " + fileName, ex);
                success = false;
            } finally {
                try {
                    if (os != null)
                        os.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing file " + fileName, e);
                    success = false;
                }
            }

            if (success) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PHOTO_FILENAME, fileName);
                getActivity().setResult(Activity.RESULT_OK, intent);
            } else {
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            getActivity().finish();
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);

        Button btn_take_pic = view.findViewById(R.id.btn_take_pic);
        btn_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera != null) {
                    mCamera.takePicture(mShutterCallback, null, mJPEGCallback);
                }
            }
        });
        surfaceview_camera = view.findViewById(R.id.surfaceview_camera);
        final SurfaceHolder holder = surfaceview_camera.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (mCamera != null) {
                        mCamera.setPreviewDisplay(holder);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
                if (mCamera == null)
                    return;

                Camera.Parameters parameters = mCamera.getParameters();
                Size s = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);
                parameters.setPreviewSize(s.width, s.height);
                mCamera.setParameters(parameters);

                try {
                    mCamera.startPreview();
                } catch (Exception ex) {
                    Log.e(TAG, "Could not start preview", ex);
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                }
            }
        });

        crime_camera_progress_container = view.findViewById(R.id.crime_camera_progress_container);
        crime_camera_progress_container.setVisibility(View.INVISIBLE);

        return view;
    }

    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(0);
        } else {
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    //设置图片尺寸
    public void surfaceChanger(SurfaceHolder holder, int format, int w, int h) {
        if (mCamera == null)
            return;

        //surface的尺寸发生了改变，要同时更新相机预览尺寸
        Camera.Parameters parameters = mCamera.getParameters();
        Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
        parameters.setPreviewSize(s.width, s.height);
        s = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);
        parameters.setPictureSize(s.width, s.height);
        mCamera.setParameters(parameters);
    }

    private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
        Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
