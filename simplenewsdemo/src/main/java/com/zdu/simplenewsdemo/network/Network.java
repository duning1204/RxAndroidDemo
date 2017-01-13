package com.zdu.simplenewsdemo.network;

import com.zdu.simplenewsdemo.network.api.NewsApi;
import com.zdu.simplenewsdemo.network.api.PicApi;
import com.zdu.simplenewsdemo.network.api.WeatherApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络请求
 * Created by duzongning on 2016/8/10.
 */
public class Network {
    private static NewsApi mNewsApi;
    private static NewsApi mNewsApiNoAdapter;
    private static PicApi mPicApi;
    private static WeatherApi mWeatherApi;
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static NewsApi getNewsApi() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .client(mOkHttpClient)
                    .baseUrl("http://c.m.163.com/nc/article/")
                    .build();
            mNewsApi = retrofit.create(NewsApi.class);
        }
        return mNewsApi;
    }

    //直接返回字符串
    public static NewsApi getNewsApiForString() {
        if (mNewsApiNoAdapter == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(mOkHttpClient)
                    .baseUrl("http://c.m.163.com/nc/article/")
                    .build();
            mNewsApiNoAdapter = retrofit.create(NewsApi.class);
        }
        return mNewsApiNoAdapter;
    }

    public static PicApi getPicApi() {
        if (mPicApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .client(mOkHttpClient)
                    .baseUrl("http://api.laifudao.com/")
                    .build();
            mPicApi = retrofit.create(PicApi.class);
        }
        return mPicApi;
    }

    public static WeatherApi getWeatherApi() {
        if (mWeatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .baseUrl("http://wthrcdn.etouch.cn/")
                    .build();
            mWeatherApi = retrofit.create(WeatherApi.class);
        }
        return mWeatherApi;
    }

}
