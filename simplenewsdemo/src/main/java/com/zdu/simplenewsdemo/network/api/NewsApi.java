package com.zdu.simplenewsdemo.network.api;

import com.zdu.simplenewsdemo.bean.NewsBean;
import com.zdu.simplenewsdemo.bean.NewsDetailsBean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 新闻API的服务接口
 * Created by duzongning on 2016/8/10.
 */
public interface NewsApi {
    //http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html  头条
    //http://c.m.163.com/nc/article/list/T1348649145984/0-20.html   NBA
    //http://c.m.163.com/nc/article/list/T1348654060988/0-20.html   汽车
    //http://c.m.163.com/nc/article/list/T1350383429665/0-20.html   笑话
    @Headers({"User-Agent:app"})
    @GET("{type}/{id}/{index}-10.html")
    Observable<NewsBean> getNewsBean(@Path("type") String type, @Path("id") String ID, @Path("index") int index);

//    @Headers({"User-Agent:app"})
//    @GET("{Docid}/full.html")
//    Observable<String> getNewsDetails(@Path("Docid") String docid);

    @Headers({"User-Agent:app"})
    @GET("{docid}/full.html")
    Observable<String> getNewsDetails(@Path("docid") String doci);
}
