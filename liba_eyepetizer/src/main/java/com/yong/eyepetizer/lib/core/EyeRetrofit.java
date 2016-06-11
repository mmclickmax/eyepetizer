package com.yong.eyepetizer.lib.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <b>Project:</b> com.yong.eyepetizer.lib.core <br>
 * <b>Create Date:</b> 2016/6/10 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b> 生成EyeService <br>
 * https://drakeet.me/retrofit-2-0-okhttp-3-0-config
 */
public class EyeRetrofit {

    private static EyeService service;

    private static final int[] sLock = new int[0];

    /**
     * 得到EyeService
     *
     * @return EyeService
     */
    public static EyeService getService() {
        if (null == service) {
            synchronized (sLock) {
                if (null == service) {
                    service = new EyeRetrofit().getEyeService();
                }
            }
        }
        return service;
    }

    /**
     * This interceptor compresses the HTTP request body. Many webservers can't handle this!
     */
    public static final class GzipRequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (!TextUtils.isEmpty(originalRequest.header("Accept-Encoding"))) {
                return chain.proceed(originalRequest);
            }
            Request gzip = originalRequest.newBuilder()
                    .header("Accept-Encoding", "gzip")
                    .build();
            return chain.proceed(gzip);
        }

    }

    private EyeRetrofit() {
        // OkHttp
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // GzipRequestInterceptor gzipRequestInterceptor = new GzipRequestInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // .addInterceptor(gzipRequestInterceptor)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();

        // gson
        Gson gson = new GsonBuilder().create();

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EyeService.API_URL)
                .client(okHttpClient)
                // 暂时不用rxjava
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(EyeService.class);
    }

    public EyeService getEyeService() {
        return service;
    }

}
