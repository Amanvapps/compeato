package com.appinnovates.campeat.services.CloudNetwork;

import com.appinnovates.campeat.utils.Constant;
import com.parse.ParseUser;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.appinnovates.campeat.utils.Constant.BASE_URL;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ApiClientWithSessionId {
    private static Retrofit retrofit = null;



    public static Retrofit getClientWithSessionToken() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(chain -> {
                                Request original = chain.request();
                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader(Constant.SESSIONTOKEN, ParseUser.getCurrentUser().getSessionToken())
                                        .addHeader(Constant.APPLICATIONID, Constant.APPID)
                                        .method(original.method(), original.body());
                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            })
                            .addInterceptor(interceptor)
                            .build()).build();
        }
        return retrofit;
    }
}
