package com.zdu.simplenewsdemo.ui;

import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zdu.simplenewsdemo.R;
import com.zdu.simplenewsdemo.utils.CommonUtils;
import com.zdu.simplenewsdemo.utils.IMUtils;
import com.zdu.simplenewsdemo.utils.SpUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.login_fast_qq)
    ImageButton mLoginFastQq;
    @Bind(R.id.login_userid)
    TextView mLoginUserid;
    @Bind(R.id.login_btn)
    Button mLoginBtn;
    @Bind(R.id.header_title)
    TextView mHeaderTitle;
    @Bind(R.id.header_toolbar)
    Toolbar mHeaderToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mHeaderToolbar.setTitle("");
        setSupportActionBar(mHeaderToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//toolbar上向左的小箭头可以点击
        mHeaderToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mHeaderTitle.setText(getString(R.string.login_btn));
        mLoginUserid.setOnClickListener(this);
        mLoginFastQq.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    private void login() {
        String token = IMUtils.getInstance(this).getTokens().get(mLoginUserid.getText().toString());
        connect(token);
    }

    private RongIMClient.ConnectCallback callback;

    /**
     * 连接im服务器
     *
     * @param token
     */
    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            callback = new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "过期");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "onSuccess : " + userid);
                    SpUtils.getInstance(LoginActivity.this).put("userid", userid);
                    CommonUtils.IM_SUCCESS = true;
                    setResult(CommonUtils.FINISH_ACTIVITY_LOGIN);
                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "errorCode.getValue():" + errorCode.getValue());
                    Log.d("LoginActivity", errorCode.getMessage());
                }
            };
            RongIM.connect(token, callback);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_userid://选择账号登录
                initPopupWindows();
                break;
            case R.id.login_fast_qq://快速登录

                break;
            case R.id.login_btn://登录
                login();
                break;
        }
    }

    private PopupWindow mWindow;
    private ListView mListView;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initPopupWindows() {
        if (mWindow == null) {
            View view = getLayoutInflater().inflate(R.layout.item_pupopwindows, null, false);
            mListView = (ListView) view.findViewById(R.id.pupop_lv);
            mWindow = new PopupWindow(view, 600, 600, true);
            mWindow.setFocusable(true);
            mWindow.setOutsideTouchable(true);
            mWindow.setBackgroundDrawable(new BitmapDrawable());
            mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
            final String[] strings = getListViewData();
            mListView.setAdapter(new ArrayAdapter<>(this, R.layout.item_popupwindows_textview, strings));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mLoginUserid.setText(strings[position]);
                    mWindow.dismiss();
                }
            });
        }
        mWindow.showAsDropDown(mLoginUserid);
    }

    public String[] getListViewData() {
        ArrayList<String> mListViewData = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            mListViewData.add("test" + i);
        }
        return mListViewData.toArray(new String[]{});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callback != null) {
            callback = null;
        }
    }
}
