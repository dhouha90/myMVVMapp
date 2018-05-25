package com.example.dchikhaoui.myprojectmvvm.View.listBook;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dchikhaoui.myprojectmvvm.BookApplication;
import com.example.dchikhaoui.myprojectmvvm.Model.books;
import com.example.dchikhaoui.myprojectmvvm.R;
import com.example.dchikhaoui.myprojectmvvm.databinding.ItemBookAdaptaterBinding;

import java.util.ArrayList;
import java.util.List;

public class BooksListAdaptater extends RecyclerView.Adapter<BooksListAdaptater.BookHolder> {
    List<books> mDatas = new ArrayList<>();
    Context context;

    public BooksListAdaptater() {
    }

    public void addItem(books data) {
        mDatas.add(data);
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemBookAdaptaterBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                (parent.getContext()), R.layout.item_book_adaptater, parent, false);
        return new BookHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(final BookHolder holder, int position) {
        holder.feedItemBinding.setBook(mDatas.get(position));
        holder.feedItemBinding.setBookPresenter(BookApplication.getBookViewModel());
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class BookHolder extends RecyclerView.ViewHolder {
        private ItemBookAdaptaterBinding feedItemBinding;

        public BookHolder(ItemBookAdaptaterBinding inflate) {
            super(inflate.getRoot());
            feedItemBinding = inflate;
        }
    }

}


