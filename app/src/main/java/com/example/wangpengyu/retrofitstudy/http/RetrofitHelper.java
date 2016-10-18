package com.example.wangpengyu.retrofitstudy.http;

import com.example.wangpengyu.retrofitstudy.bean.Bean;
import com.example.wangpengyu.retrofitstudy.config.UrlConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangpengyu on 16/10/17.
 */
public class RetrofitHelper {

    private static RetrofitHelper singleton;

    public static RetrofitHelper getInstance(){
        if(singleton == null){
            synchronized (RetrofitHelper.class){
                if(singleton == null){
                    singleton = new RetrofitHelper();
                }
            }
        }
        return singleton;
    }

    RetrofitHelper(){
        getIRApi();
    }

    private Gson gson;

    // 初始化Gson
    private void initGson(){
        gson = new GsonBuilder()
                .serializeNulls()
                .create();

    }

    // 获得Retrofit
    private Retrofit getRetrofit(){
        initGson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    private IRetrofitApi iRetrofitApi;

    // 获得IRetrofitApi
    public IRetrofitApi getIRApi(){
        iRetrofitApi = getRetrofit().create(IRetrofitApi.class);
        return iRetrofitApi;
    }

    /**
     * 获得对应的Bean对象
     * @param path 相对路径
     * @param hashMap 键值对
     * @param callback 封装的回调接口
     */
    public void getBean(String path, Map<String,String> hashMap, Callback<Bean> callback){
        Call<Bean> call = iRetrofitApi.getBean(path, hashMap);
        call.enqueue(callback);
    }




}
