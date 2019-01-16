package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

public class PictureUtils {

    //从本地文件夹中获取一个被缩放到适合当前窗口尺寸的BitmapDrawable
    public static BitmapDrawable getScaledDrawable(Activity activity, String path){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int destWidth = display.getWidth();
        int destHeight = display.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int screenWidth = options.outWidth;
        int screenHeight = options.outHeight;

        int inSampleSize =1;
        if(screenHeight>destHeight||screenWidth>destWidth){
            if(screenWidth>screenHeight){
                inSampleSize = Math.round(screenHeight/destHeight);
            }else{
                inSampleSize = Math.round(screenWidth/destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return new BitmapDrawable(activity.getResources(), bitmap);
    }

    public static void cleanImageView(ImageView imageView){
        if(!(imageView.getDrawable() instanceof  BitmapDrawable))
            return;

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        bitmapDrawable.getBitmap().recycle();
        imageView.setImageDrawable(null);
    }
}
