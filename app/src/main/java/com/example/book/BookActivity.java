package com.example.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class BookActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BookFragment bookFragment;
    private BookIdFragment bookIdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        fragmentManager = getSupportFragmentManager();

        bookFragment = new BookFragment();
        bookIdFragment = new BookIdFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bookFragment).commitAllowingStateLoss();

    }


    public void clickHandler(View view)
    {
        transaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.btn_fragmentA:
                transaction.replace(R.id.frameLayout, bookFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_fragmentB:
                transaction.replace(R.id.frameLayout, bookIdFragment).commitAllowingStateLoss();
                break;
        }
    }
}