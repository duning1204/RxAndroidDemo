package com.zdu.simplenewsdemo.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 记录保存一些常量和常用方法
 * Created by duzongning on 2016/9/23.
 */
public class CommonUtils {

    public static final String QQ_APPID = "1105640431";
    public static final int FINISH_ACTIVITY_LOGIN = 100;
    public static boolean IM_SUCCESS = false;//判断是否已经成功连接im服务器


    /**
     * 获得当前进程的名字
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
