package com.example.dchikhaoui.myprojectmvvm.service;

import com.example.dchikhaoui.myprojectmvvm.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpService {
    private static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BOOK_WS_DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        return retrofit;
    }

    public static HttpInterface getInterface() {
        return getClient().create(HttpInterface.class);
    }
}
