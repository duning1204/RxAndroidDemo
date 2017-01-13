package com.zdu.simplenewsdemo.network.api;

import com.zdu.simplenewsdemo.bean.WeatherBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天气APi
 * Created by duzongning on 2016/9/7.
 */
public interface WeatherApi {
    @GET("weather_mini")
    Observable<WeatherBean> getWeather(@Query("city") String city);
}
