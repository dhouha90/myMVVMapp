package com.example.dchikhaoui.myprojectmvvm.View.listBookDetailled;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.dchikhaoui.myprojectmvvm.View.listBook.listBookActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class listBookSelectedActivity extends AppCompatActivity implements LifecycleObserver {
    View mView;
    TextView mTextViewSomme;
    String mSomme;
    BookListDetailAdaptater mAdaptater;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_book_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mView = findViewById(R.id.view_res);
        mTextViewSomme = findViewById(R.id.all_somme);
        final RecyclerView rv = findViewById(R.id.list_book);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            ArrayList<books> mListBooks = arguments.getParcelableArrayList(listBookActivity.BOOK_EXTRA);
            mAdaptater = new BookListDetailAdaptater(mListBooks, this);
            rv.setAdapter(mAdaptater);
        }
        Observer<String> mSommeObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mSomme = s;
                mView.setVisibility(View.VISIBLE);
                mTextViewSomme.setText(getResources().getString(R.string.somme_a_payer, s));
            }
        };

        BookApplication.getDetailBookViewDetail().getmPaiAll().observe(this, mSommeObserver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.payment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_payment:
                pai();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pai() {
        BookApplication.getDetailBookViewDetail().paiAll(mAdaptater.getlistBook());
    }


    public void openPaypal(View view) {
        String metadataId = PayPalConfiguration.getClientMetadataId(getApplicationContext());
        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
                .clientId(metadataId);
        PayPalPayment payment = new PayPalPayment(new BigDecimal(mSomme), "EUR", "sample item",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivity(intent);

    }


}
