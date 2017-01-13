package com.zdu.simplenewsdemo.utils;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zdu.simplenewsdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * qq第三方登录回调
 * Created by duzongning on 2016/9/23.
 */

public class QQLoginListener implements IUiListener {
    private Tencent mTencent;
    private NavigationView mNavigationView;

    public QQLoginListener(Tencent tencent, NavigationView navigationView) {
        this.mNavigationView = navigationView;
        mTencent = tencent;
    }

    @Override
    public void onComplete(Object o) {
        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            mTencent.setOpenId(jsonObject.optString("openid"));
            mTencent.setAccessToken(jsonObject.optString("access_token"), jsonObject.optString("expires_in"));
            UserInfo userInfo = new UserInfo(mNavigationView.getContext(),mTencent.getQQToken());
            QQUserInforListener qqUserInforListener = new QQUserInforListener(mNavigationView);
            userInfo.getUserInfo(qqUserInforListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.login_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.login_cancel), Snackbar.LENGTH_SHORT).show();
    }
}
