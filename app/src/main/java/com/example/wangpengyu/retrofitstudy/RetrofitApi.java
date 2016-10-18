package com.example.wangpengyu.retrofitstudy;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * Created by wangpengyu on 16/10/17.
 */
public interface RetrofitApi {

    @Streaming
    @GET("300/1406/267200901034/267200901052.jpg")
    Call<ResponseBody> getStreaming();
}
