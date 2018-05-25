package com.example.dchikhaoui.myprojectmvvm.View.listBookDetailled;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dchikhaoui.myprojectmvvm.BookApplication;
import com.example.dchikhaoui.myprojectmvvm.Model.books;
import com.example.dchikhaoui.myprojectmvvm.R;
import com.example.dchikhaoui.myprojectmvvm.databinding.ItemBookSelectedAdaptaterBinding;
import com.example.dchikhaoui.myprojectmvvm.databinding.ListBookActivityBinding;

import java.util.List;

public class BookListDetailAdaptater extends RecyclerView.Adapter<BookListDetailAdaptater.BookHolderSelected> {
    List<books> mDatas;
    LifecycleOwner context;

    public BookListDetailAdaptater(List<books> mDatas, LifecycleOwner context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public BookHolderSelected onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemBookSelectedAdaptaterBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                (parent.getContext()), R.layout.item_book_selected_adaptater, parent, false);


        ListBookActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from
                (parent.getContext()), R.layout.list_book_activity, parent, false);


        return new BookHolderSelected(viewDataBinding, binding);
    }

    @Override
    public void onBindViewHolder(BookHolderSelected holder, int position) {
        holder.mInflate.setBook(mDatas.get(position));
        holder.mInflate.setDetailBokkAdaptater(BookApplication.getDetailBookViewDetail());
        holder.mDetailInflate.setActivityBook(mDatas.get(position));

        final Observer<books> nameObserver = new Observer<books>() {
            @Override
            public void onChanged(@Nullable books books) {

                holder.mInflate.count.setText(books.getCount());
                if (books.getCount().compareTo("0") == 0) {
                    mDatas.remove(position);
                    notifyDataSetChanged();
                }else{
                    mDatas.get(position).setQt(books.getQt());
                }
            }
        };
        BookApplication.getDetailBookViewDetail().getBookLData().observe(context, nameObserver);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public List<books> getlistBook(){
        return mDatas;
    }

    class BookHolderSelected extends RecyclerView.ViewHolder {
        ItemBookSelectedAdaptaterBinding mInflate;
        ListBookActivityBinding mDetailInflate;

        public BookHolderSelected(ItemBookSelectedAdaptaterBinding mInflate, ListBookActivityBinding mDetailInflate) {
            super(mInflate.getRoot());
            this.mInflate = mInflate;
            this.mDetailInflate = mDetailInflate;
        }

    }
}
