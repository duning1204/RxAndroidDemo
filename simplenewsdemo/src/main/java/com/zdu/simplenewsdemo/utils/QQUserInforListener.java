package com.zdu.simplenewsdemo.utils;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.zdu.simplenewsdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * QQ用户信息回调
 * Created by duzongning on 2016/9/23.
 */
public class QQUserInforListener implements IUiListener {

    private NavigationView mNavigationView;

    public QQUserInforListener(NavigationView navigationView) {
        this.mNavigationView = navigationView;
    }

    @Override
    public void onComplete(Object o) {
        Log.d("QQUserInforListener", o.toString());
        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            String nickname = jsonObject.optString("nickname");
            String figureurl_2 = jsonObject.optString("figureurl_2");
            View headerView = mNavigationView.getHeaderView(0);
            ImageView profile_image = (ImageView) headerView.findViewById(R.id.profile_image);
            TextView profile_name = (TextView) headerView.findViewById(R.id.profile_name);
            profile_name.setText(nickname);
            Glide.with(profile_image.getContext()).load(figureurl_2).into(profile_image);
            Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.login_success), Snackbar.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.userinfo_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Snackbar.make(mNavigationView, mNavigationView.getContext().getString(R.string.userinfo_cancel), Snackbar.LENGTH_SHORT).show();
    }

}
