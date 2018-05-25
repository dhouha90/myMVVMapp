package com.example.dchikhaoui.myprojectmvvm.service;

import android.util.Log;

import com.example.dchikhaoui.myprojectmvvm.Model.AllOffers;
import com.example.dchikhaoui.myprojectmvvm.Model.books;
import com.example.dchikhaoui.myprojectmvvm.Utils.UtilsBook;
import com.example.dchikhaoui.myprojectmvvm.View.listBook.BooksListAdaptater;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HttpResponse {
    private final String TAG = this.getClass().getName();

    public void getListBooks(final BooksListAdaptater mBookListAdaptater) {
        HttpService.getInterface().getListBooks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<books>, ObservableSource<books>>() {
                    @Override
                    public ObservableSource<books> apply(List<books> books) {
                        return Observable
                                .just(books)
                                .flatMap(new Function<List<books>, ObservableSource<books>>() {
                                    @Override
                                    public ObservableSource<books> apply(List<books> books) {
                                        return Observable.fromIterable(books);
                                    }
                                });

                    }
                }).subscribe(new Observer<books>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(books books) {
                Log.i(TAG, "onNext: " + books.getIsbn());
                mBookListAdaptater.addItem(books);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                mBookListAdaptater.notifyDataSetChanged();
            }
        });
    }

    public Observable<AllOffers> getListOffers(String mIsbnList) {

        return HttpService.getInterface().getOffers(mIsbnList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<AllOffers, ObservableSource<AllOffers>>() {
                    @Override
                    public ObservableSource<AllOffers> apply(AllOffers allOffers) throws Exception {
                        return io.reactivex.Observable.just(allOffers);
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                });
    }
}
