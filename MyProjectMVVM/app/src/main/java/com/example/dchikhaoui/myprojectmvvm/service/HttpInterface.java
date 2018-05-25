package com.example.dchikhaoui.myprojectmvvm.service;

import com.example.dchikhaoui.myprojectmvvm.Model.AllOffers;
import com.example.dchikhaoui.myprojectmvvm.Model.books;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpInterface {
    @GET("/books")
    Observable<List<books>> getListBooks();

    @GET("/books/{isbns}/commercialOffers")
    Observable<AllOffers> getOffers(@Path("isbns") String isbns);
}
