# UpgradeApkUtil

Android Apk检查更新升级工具

### 使用方法：

> 在Android Studio的Project build.gradle中加入：

    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }

> 在Module build.gradle中添加依赖：

    implementation 'com.github.LanceShu:upgrade-apk-util:1.2'

### 代码使用：

##### 获取ApkUpgradeManager的单例对象：

    ApkUpgradeManager manager = ApkUpgradeManager.getManager(context);

##### 获取Apk的版本号：

    String version = manager.getAPKVersionName(getPackageName());

##### 获取Apk的代码版本：

    int code = manager.getAPKVersionCode(getPackageName());

##### 利用Android系统原生的下载服务来实现Apk在线更新下载并安装，参数分别为服务器上的最新Apk的Url、下载到本地的Apk的父文件夹、下载到本地的Apk的命名+".apk"：

    manager.downloadAPK(httpUrl, "parentPath", "apkName.apk");

##### 在Android Manifest.xml中需要申请的权限，注意：在Android 6.0以后，有些危险的权限还需要在代码中动态申请 ：

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

