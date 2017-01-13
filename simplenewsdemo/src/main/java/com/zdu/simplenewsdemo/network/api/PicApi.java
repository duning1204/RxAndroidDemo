package com.zdu.simplenewsdemo.network.api;

import com.zdu.simplenewsdemo.bean.ImageBean;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by duzongning on 2016/9/6.
 */
public interface PicApi {
    @GET("open/tupian.json")
    Observable<ArrayList<ImageBean>> getPicData();
}
