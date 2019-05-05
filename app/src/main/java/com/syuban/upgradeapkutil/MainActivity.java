package com.syuban.upgradeapkutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.syuban.upgradeapktool.ApkUpgradeManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApkUpgradeManager manager = ApkUpgradeManager.getManager(this);
        Log.e("activity", manager.getAPKVersionName(getPackageName()));
    }
}
