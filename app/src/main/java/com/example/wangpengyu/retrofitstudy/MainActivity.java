package com.example.wangpengyu.retrofitstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.wangpengyu.retrofitstudy.bean.Bean;
import com.example.wangpengyu.retrofitstudy.config.UrlConfig;
import com.example.wangpengyu.retrofitstudy.http.RetrofitHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;

    private float currentPer;

    private int len;

    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.pb);
        findViewById(R.id.btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getStreaming();
                    }
                }
        );


    }

    private void getBean() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            // 放入键值对
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put(UrlConfig.Param.PAGE,UrlConfig.DefaultValue.PAGE);
            hashMap.put(UrlConfig.DefaultValue.PRE_NUM,UrlConfig.DefaultValue.PRE_NUM);

            RetrofitHelper.getInstance().getBean(UrlConfig.Path.PATH, hashMap, new Callback<Bean>() {
                @Override
                public void onResponse(Call<Bean> call, Response<Bean> response) {
                    Bean bean = response.body();
                    // Bean对象
                    Log.e("Tag",String.valueOf(bean.getData().getList().size()));
                    // 主线程
                    Log.e("Tag",Thread.currentThread().getName());
                }

                @Override
                public void onFailure(Call<Bean> call, Throwable t) {

                }
            });

            }
        }).start();
    }

    // 获得流,从流中获取图片
    private void getStreaming() {
        // 获得流的.execute方法走当前线程,所以开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> streaming = RetrofitHelper.getInstance()
                        .getIRApi()
                        .getStreaming(UrlConfig.Path.PATH);
                BufferedInputStream bis = null;
                ByteArrayOutputStream baos = null;
                try {
                    Response<ResponseBody> response = streaming.execute();
                    InputStream inputStream = response.body().byteStream();
                    bis = new BufferedInputStream(inputStream);
                    // 获得内容总长
                    long contentLength = response.body().contentLength();
                    byte[] data = new byte[1024];
                    int i = 0;
                    baos = new ByteArrayOutputStream();
                    // 循环读取数组
                    while ((len = bis.read(data)) != -1){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        i += len*100;
                        currentPer = i/contentLength;
                        p = (int) currentPer;
                        // 在主线程中操作UI 控件
                        runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        // 设置进度条
                                        pb.setProgress(p);
                                    }
                                }
                        );
                        baos.write(data,0,len);
                        baos.flush();
                    }
                    byte[] bytes = baos.toByteArray();
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    // 设置图片
                                    ((ImageView) findViewById(R.id.iv)).setImageBitmap(bitmap);
                                }
                            }
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭流
                    if(bis != null){
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(baos != null){
                        try {
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
