package com.example.dchikhaoui.myprojectmvvm.Viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dchikhaoui.myprojectmvvm.Model.AllOffers;
import com.example.dchikhaoui.myprojectmvvm.Model.books;
import com.example.dchikhaoui.myprojectmvvm.Model.offers;
import com.example.dchikhaoui.myprojectmvvm.service.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.Subscriber;
import rx.observables.MathObservable;

public class DetailBookViewModel extends ViewModel implements DetailBookContract.UserActionsListener {
    MutableLiveData<books> bookLiveData = new MutableLiveData<>();
    MutableLiveData<String> mPaiAll = new MutableLiveData<>();
    String mIsbnString = "";
    int mSommeFinal;
    List<Double> resOffers = new ArrayList<>();


    @Override
    public void increaseQuantityBook(books book) {

        int mCount = book.getQt() + 1;
        book.setQt(mCount);
        getBookLData().setValue(book);
    }

    @Override
    public void decreaseQuantityBook(books book) {

        int mCount = book.getQt() - 1;
        book.setQt(mCount);
        getBookLData().setValue(book);
    }

    public MutableLiveData<books> getBookLData() {
        return bookLiveData;
    }

    public MutableLiveData<String> getmPaiAll() {
        return mPaiAll;
    }


    public void paiAll(List<books> mListBook) {
        io.reactivex.Observable.just(mListBook)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<books>, ObservableSource<books>>) books -> Observable.fromIterable(books)).subscribe(new Observer<books>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(books books) {
                for (int i = 0; i < books.getQt(); i++) {
                    mIsbnString = mIsbnString + books.getIsbn().toString() + ",";
                    mSommeFinal = mSommeFinal + books.getPrice();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                mIsbnString = mIsbnString.substring(0, mIsbnString.length() - 1);
                HttpResponse httpResponse = new HttpResponse();
                io.reactivex.Observable<AllOffers> allOffers = httpResponse.getListOffers(mIsbnString);
                allOffers.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).flatMap((Function<AllOffers, ObservableSource<offers>>) allOffers1 -> Observable.fromIterable(allOffers1.getListOffers())).subscribe(new Observer<offers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(offers offers) {
                        if (offers.getType().equals(OfferType.percentage.toString())) {
                            double mPercent = ((double) offers.getValue() / 100) * mSommeFinal;
                            resOffers.add(mPercent);
                        }

                        if (offers.getType().equals(OfferType.minus.toString())) {
                            resOffers.add((double) offers.getValue());
                        }
                        if (offers.getType().equals(OfferType.slice.toString())) {
                            if (mSommeFinal >= offers.getSliceValue()) {
                                resOffers.add((double) offers.getSliceValue());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getBestOffers(resOffers);
                    }
                });
            }
        });

    }


    public void getBestOffers(List<Double> allOffers) {
        rx.Observable<Double> mAllListOffers = rx.Observable.from(allOffers);
        MathObservable.max(mAllListOffers).subscribe(new Subscriber<Double>() {
            @Override
            public void onCompleted() {
                getmPaiAll().setValue(mIsbnString);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Double res) {
                mIsbnString = String.valueOf(Double.valueOf(mSommeFinal) - res);
            }
        });

    }

    public enum OfferType {
        percentage,
        minus,
        slice
    }
}