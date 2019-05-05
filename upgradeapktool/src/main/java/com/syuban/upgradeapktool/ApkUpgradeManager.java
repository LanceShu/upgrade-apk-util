package com.syuban.upgradeapktool;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Lance
 * on 2019/5/5.
 */

public class ApkUpgradeManager {
    @SuppressLint("StaticFieldLeak")
    private static volatile ApkUpgradeManager manager;
    private Context context;
    private ApkUpgradeManager(Context context) {
        this.context = context;
    }

    /**
     * create ApkUpgradeManager
     * @param context
     * @return manager(ApkUpgradeManager)
     * */
    public static ApkUpgradeManager getManager(Context context) {
        if (manager == null) {
            synchronized (ApkUpgradeManager.class) {
                if (manager == null) {
                    manager = new ApkUpgradeManager(context);
                }
            }
        }
        return manager;
    }

    /**
     * recycle manager
     * */
    public void recycle() {
        if (manager != null) {
            manager = null;
        }
    }

    /**
     * get apk version name;
     * @param packageName
     * @return versionName(String)
     * */
    public String getAPKVersionName(String packageName) {
        if (packageName != null && packageName.length() > 0) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(packageName, 0);
                return info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get apk version code
     * @param packageName
     * @return versionCode(int)
     * */
    public int getAPKVersionCode(String packageName) {
        if (packageName != null && packageName.length() > 0) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(packageName, 0);
                return info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * download apk
     * @param url
     * @param parentPath
     * @param childPath
     * */
    public void downloadAPK(String url, String parentPath, String childPath) {
        if (url == null) return;
        try {
            final DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.allowScanningByMediaScanner();
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("application/vnd.android.package-archive");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + parentPath + "/", childPath);
            if (file.exists()) {
                file.delete();
            }
            request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + parentPath + "/", childPath);
            long apkId = downloadManager.enqueue(request);
            // android 6.0以后需要扫描内存卡中的apk文件进行安装，apkID用于匹配；
            SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = manager.edit();
            editor.putLong("apk_id", apkId);
            editor.apply();
        } catch (Exception e) {
            Toast.makeText(context, "下载更新失败", Toast.LENGTH_SHORT).show();
        }
    }
}
