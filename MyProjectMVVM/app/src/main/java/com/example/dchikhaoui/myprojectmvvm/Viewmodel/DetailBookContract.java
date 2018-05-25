package com.example.dchikhaoui.myprojectmvvm.Viewmodel;

import android.widget.TextView;

import com.example.dchikhaoui.myprojectmvvm.Model.books;

public interface DetailBookContract {

    interface UserActionsListener {


        void increaseQuantityBook( books book);

        void decreaseQuantityBook( books book);

    }



}
