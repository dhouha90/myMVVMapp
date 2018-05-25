package com.example.dchikhaoui.myprojectmvvm.Viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dchikhaoui.myprojectmvvm.Model.books;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel implements BookContract.UserActionsListener {

    ArrayList<books> mListBooks = new ArrayList<>();
    MutableLiveData<List<books>> bookLiveData = new MutableLiveData<>();


    @Override
    public void AddOrBook(books book) {
        if (mListBooks.contains(book)) {
            mListBooks.remove(book);
        } else {
            mListBooks.add(book);
        }

        getBookLiveData().setValue(mListBooks);
    }

    public MutableLiveData<List<books>> getBookLiveData() {
        return bookLiveData;
    }
    public ArrayList<books> getListBook(){
        return mListBooks;
    }

}
