package com.example.dchikhaoui.myprojectmvvm.Viewmodel;

import com.example.dchikhaoui.myprojectmvvm.Model.books;

public interface BookContract {

    interface View {
        void showBook();

    }

    interface UserActionsListener {
        void AddOrBook(books book);
    }
}
