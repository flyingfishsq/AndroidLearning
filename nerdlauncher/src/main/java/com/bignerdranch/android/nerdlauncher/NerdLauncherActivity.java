package com.bignerdranch.android.nerdlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
