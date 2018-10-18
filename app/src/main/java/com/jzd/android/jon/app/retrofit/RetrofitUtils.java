package com.jzd.android.jon.app.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jzd.android.jon.app.base.Constant;
import com.jzd.android.jon.utils.JLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitUtils
 */

public class RetrofitUtils
{
    private static ApiService sApiService;

    public static ApiService getApi()
    {
        if(sApiService == null)
        {
            synchronized(RetrofitUtils.class)
            {
                if(sApiService == null)
                {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            //.addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build();
                    sApiService = retrofit.create(ApiService.class);
                }
            }
        }
        return sApiService;
    }

    private static ConcurrentHashMap<String, List<Cookie>> cookieStores = new ConcurrentHashMap<>();

    private static OkHttpClient getOkHttpClient()
    {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // Cookie
        builder.cookieJar(new CookieJar()
        {
            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies)
            {
                // 注册的接口返回的cookie不能用
                //                if(!url.toString()
                //                        .contains("createNewUser"))
                //                {
                cookieStores.put(url.host(), cookies);
                //                }
                JLog.d("saveFromResponse:" + url.host() + "----------------");
                for(Cookie cookie : cookies)
                {
                    JLog.d(cookie.toString());
                }
            }

            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url)
            {
                List<Cookie> cookieList = null;
                //                // 登入不能带cookies
                //                if(!url.toString()
                //                        .contains("logIn.json"))
                //                {
                cookieList = cookieStores.get(url.host());
                //                }
                JLog.d("loadForRequest:" + url.host() + "---------------");
                if(cookieList != null)
                {
                    for(Cookie cookie : cookieList)
                    {
                        JLog.d(cookie.toString());
                    }
                }
                return cookieList != null ? cookieList : new ArrayList<Cookie>();
            }
        });

        builder.addInterceptor(new Interceptor()
        {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException
            {
                Request.Builder newBuilder = chain.request()
                        .newBuilder();
                //                try
                //                {
                //                    if(MyApp.isLogin())
                //                    {
                //                        String uuid = UUID.randomUUID()
                //                                .toString();
                //                        String date = String.valueOf(new Date().getTime());
                //                        String openId = MyApp.getOpenId();
                //                        String secretKey = MyApp.getCurrentUser()
                //                                .getSecretKey();
                //                        Map<String, String> header = new HashMap<>();
                //                        newBuilder.addHeader("openId", openId);
                //                        newBuilder.addHeader("noncestr", uuid);
                //                        newBuilder.addHeader("timestamp", date);
                //                        newBuilder.addHeader("signature", Md5Util.getStringURLMosaic(openId,
                // uuid, date,
                //                                secretKey));
                //                    }
                //                } catch(Exception e)
                //                {
                //                    e.printStackTrace();
                //                }
                return chain.proceed(newBuilder.build());
            }
        })
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(@Nullable String message)
            {
                JLog.d(message);
            }
        };
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logger);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    public static String getFormData(Map<String, Object> params)
    {
        if(params == null)
        {
            params = new HashMap<>();
        }
        StringBuilder p = new StringBuilder("[");

        for(String key : params.keySet())
        {
            p.append("{\"name\":\"")
                    .append(key)
                    .append("\",\"value\":\"")
                    .append(params.get(key))
                    .append("\"},");
        }
        String str = p.substring(0, p.length() - 1);
        str += "]";

        return str;
    }

    public static RequestBody getBody(Map<String, Object> params)
    {
        if(params == null)
        {
            params = new HashMap<>();
        }
        if(!params.containsKey("mobileFlag"))
        {
            params.put("mobileFlag", true);
        }
        return getBody(new Gson().toJson(params));
    }

    private static RequestBody getBody(String toJson)
    {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), toJson);
    }

    public static void clearCookie()
    {
        cookieStores.clear();
    }
}
