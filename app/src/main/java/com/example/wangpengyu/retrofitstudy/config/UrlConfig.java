package com.example.wangpengyu.retrofitstudy.config;

/**
 * Created by wangpengyu on 16/10/17.
 */
public class UrlConfig {

//    http://m.zfkx.com.cn/app/home/sanban
//    public static final String BASE_URL = "http://m.zfkx.com.cn/";
    public static final String BASE_URL = "http://img1.dwstatic.com/";

    public static class Path{
//        public static final String PATH = "app/home/sanban";
        public static final String PATH = "300/1406/267200901034/267200901052.jpg";
    }
    public static class Param{
        public static final String PAGE = "page";
        public static final String PRE_NUM = "pre_num";
    }
    public static class DefaultValue{
        public static final String PAGE = "0";
        public static final String PRE_NUM = "10";
    }
}
