package com.example.wangpengyu.retrofitstudy.http;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by wangpengyu on 16/10/17.
 */
public class FileConverterFactory extends Converter.Factory {
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FileConverter();
    }
    class FileConverter implements Converter<File, RequestBody> {
        @Override
        public RequestBody convert(File value) throws IOException {
            RequestBody requestBody = new MultipartBody.Builder()
                                            .addFormDataPart(
                                                    value.getName(),
                                                    value.getName(),
                                                    MultipartBody.create(
                                                            MultipartBody.FORM,
                                                            value
                                                    )
                                            ).build();
            return requestBody;
        }
    }
}
