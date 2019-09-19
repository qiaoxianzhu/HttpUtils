package com.test.httputils;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Joe
 * @date 2019/9/18.
 * description：Retrofit封装类
 * 如何取消请求？
 * Rx方式和Call方式
 */
public class HttpHeper {
    private Retrofit retrofit;
    private Object tag;


    private HttpHeper(Object object, String url) {
        Log.d("HttpHeper", "HttpHeper: " + url + "===" + object);
        retrofit = new Retrofit.Builder()
                .baseUrl(url) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        tag = object;
    }

    public <T> ObservableTransformer<T, T> getThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 调用方式伪代码：
     * HttpHeper.from(this).xxxApi.xxx(x,y)
     * return : call  or observble
     * 手动取消 自动 取消
     * 判断是call 还是 observble
     */
    public static HttpHeper get(String Url) {
        HttpHeper httpHeper = new HttpHeper(new Object(), Url);
        return httpHeper;
    }


//    public void setBaseUrl(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }


//    public UserService userService() {
//        return create(UserService.class);
//    }


    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }


    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //添加公共的请求头
        BasicParamsInterceptor.Builder ParamBuilder = new BasicParamsInterceptor.Builder();

        ParamBuilder.addParam("version", BuildConfig.VERSION_NAME);
        ParamBuilder.addParam("device ", "1");
        builder.addInterceptor(ParamBuilder.build())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS);
        return builder.build();
    }

    //上传图片
    public FileService updateFileService() {
        return create(FileService.class);
    }

}
