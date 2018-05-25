package com.example.dchikhaoui.myprojectmvvm;

import android.app.Application;
import android.content.Context;

import com.example.dchikhaoui.myprojectmvvm.Viewmodel.BookViewModel;
import com.example.dchikhaoui.myprojectmvvm.Viewmodel.DetailBookViewModel;

public class BookApplication extends Application {
    private static Context mApp;
 static    BookViewModel bookViewModel;
 static DetailBookViewModel detailBookViewModel;

    public BookApplication() {
        mApp=this;
    }

    public static Context getApp() {
        return mApp;
    }

    public static BookViewModel getBookViewModel(){
        if (bookViewModel == null){
         bookViewModel= new BookViewModel();}
         return bookViewModel;
    }
    public static DetailBookViewModel getDetailBookViewDetail(){
        if (detailBookViewModel == null){
            detailBookViewModel= new DetailBookViewModel();
        }
        return detailBookViewModel;
    }
}
