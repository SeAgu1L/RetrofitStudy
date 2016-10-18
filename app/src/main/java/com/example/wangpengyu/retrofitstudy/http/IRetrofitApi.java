package com.example.wangpengyu.retrofitstudy.http;

import com.example.wangpengyu.retrofitstudy.bean.Bean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

/**
 * Created by wangpengyu on 16/10/17.
 */
public interface IRetrofitApi {

    /**
     * 获得流
     * 该方法的.enquene方法走UI线程
     *        .execute走当前线程
     * @param path 相对路径
     * @return
     */
    @Streaming
    @GET("{path}")
    Call<ResponseBody> getStreaming(@Path("path") String path);

    /**
     * 通过post请求获得该javaBean对象
     * @param path 相对路径
     * @param hashMap 参数集合
     * @return
     */
    @POST("{path}")
    Call<Bean> getBean(@Path("path") String path , @QueryMap Map<String,String> hashMap);
}
