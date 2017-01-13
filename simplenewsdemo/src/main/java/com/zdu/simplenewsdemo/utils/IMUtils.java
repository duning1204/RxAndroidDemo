package com.zdu.simplenewsdemo.utils;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * im服务相关的工具类
 * Created by duzongning on 2016/9/28.
 */
public class IMUtils {
    private static IMUtils mImUtils;
    private Context mContext;

    private IMUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static IMUtils getInstance(Context context) {
        if (mImUtils == null) {
            mImUtils = new IMUtils(context);
        }
        return mImUtils;
    }

    //连接im服务
    public void connect(String token) {
        if (mContext.getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(mContext))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
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
                    Log.d("LoginActivity", userid);
                    SpUtils.getInstance(mContext).put("userid", userid);
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
            });
        }
    }

    public HashMap<String, String> getTokens() {
        return tokens;
    }

    private HashMap<String, String> tokens;

    /**
     * 初始化N个试用账号
     */
    public void initToken() {
        tokens = new HashMap<>();
        tokens.put("test1", "nywlnUQyKdwbOkV2ZbtGuoop3vS5E4SrlIMbxk9dYOAIfCYkJyixl6uj2CkxBx/9MuWPWs3/nLfXwjjjOFIAgw==");
        tokens.put("test2", "2Hr/RxewZE3jdX9brGNjqsPh8QkjgxnGyieFbqBYMobNOdWmDn1Vhd/PQGgMQfREHcTV3oLv3C+LvLaVHlp3rw==");
        tokens.put("test3", "beBv9/QO+2G2f9ut9+vGucPh8QkjgxnGyieFbqBYMobNOdWmDn1VhfQH6k1HJ+14BLGDq9x0vQQ8uX2CuRz3hA==");
        tokens.put("test4", "aX5EW/YYixM2InjoflZSPPTQULeKiW9lsOuSw5fR7gjAcP/77ndRx1y58Wn4HYxgmZ5Z27v5TPyteWqMuyp2AA==");
        tokens.put("test4", "aX5EW/YYixM2InjoflZSPPTQULeKiW9lsOuSw5fR7gjAcP/77ndRx1y58Wn4HYxgmZ5Z27v5TPyteWqMuyp2AA==");
        tokens.put("test5", "+gyxLZ4ksWSiNOyKy9lS/4op3vS5E4SrlIMbxk9dYOAIfCYkJyixlwmqOD/HRU50xmjkcDnWfERa6GAF5LTG1w==");
        tokens.put("test6", "oRulYrNvscRy3Zvyg4ZlnzWviaF2k/rRwOjUqR5Tk5P9R+KAmgDNiQTcyXarvwHlYR10DezB54G95pZjaeXZYA==");
        tokens.put("test7", "oiqabermdguHBI1Vm4Te5Iop3vS5E4SrlIMbxk9dYOAIfCYkJyixl7S8/Y50Lh1v4F+E+Re26OwMaFMMg3LUHA==");
        tokens.put("test8", "YCMwazq4mfZsmMJvr7hXaMPh8QkjgxnGyieFbqBYMobNOdWmDn1Vhf50x3iHqwjJApvRYLgwlIzNwKJ8PgZLEA==");
        tokens.put("test9", "HcSTmvoa6MgjAV4iLAY308Ph8QkjgxnGyieFbqBYMobNOdWmDn1VheIal4lszRjSvWk74ywP06bRDUFX04YRvw==");
    }

    public String getToken(String key) {
        return tokens.get(key);
    }
}
