package com.zdu.simplenewsdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.zdu.simplenewsdemo.utils.CommonUtils;
import com.zdu.simplenewsdemo.utils.IMUtils;

import io.rong.imkit.RongIM;

/**
 * Created by duzongning on 2016/9/27.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
        //io.rong.push 为融云 push 进程名称，不可修改。
        if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(CommonUtils.getCurProcessName(getApplicationContext()))) {
            //IMKit SDK调用第一步 初始化
            RongIM.init(this);
            IMUtils.getInstance(this).initToken();
            //执行本app初始化操作
            if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext()))) {
                LeakCanary.install(this);
            }
        }
    }
}
