package com.example.dchikhaoui.myprojectmvvm.View.listBook;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dchikhaoui.myprojectmvvm.BookApplication;
import com.example.dchikhaoui.myprojectmvvm.Model.books;
import com.example.dchikhaoui.myprojectmvvm.R;
import com.example.dchikhaoui.myprojectmvvm.View.listBookDetailled.listBookSelectedActivity;
import com.example.dchikhaoui.myprojectmvvm.Viewmodel.BookContract;
import com.example.dchikhaoui.myprojectmvvm.service.HttpResponse;

import java.util.List;

public class listBookActivity extends AppCompatActivity implements BookContract.View, LifecycleObserver {

    BooksListAdaptater mBookListAdaptater;
    public TextView MenuCount;
    private LifecycleRegistry mLifecycleRegistry;
    public final static String BOOK_EXTRA = "BOOK_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_book_activity);
        final RecyclerView rv = findViewById(R.id.list_book);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mBookListAdaptater = new BooksListAdaptater();
        rv.setAdapter(mBookListAdaptater);
        showBook();

        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        final Observer<List<books>> nameObserver = listBook -> MenuCount.setText(String.valueOf(listBook.size()));
        BookApplication.getBookViewModel().getBookLiveData().observe(this, nameObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_buy);
        item.setActionView(R.layout.actionbar_badge_layout);
        MenuCount = item.getActionView().findViewById(R.id.actionbar_textview);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showBook() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.getListBooks(mBookListAdaptater);
    }


    public void openDetailed(View view) {
        if (BookApplication.getBookViewModel().getBookLiveData().getValue() == null || BookApplication.getBookViewModel().getBookLiveData().getValue().size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(view, R.string.empty_list_product, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            Intent myIntent = new Intent(this, listBookSelectedActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putParcelableArrayList(BOOK_EXTRA, BookApplication.getBookViewModel().getListBook());
            myIntent.putExtras(mBundle);
            startActivity(myIntent);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
