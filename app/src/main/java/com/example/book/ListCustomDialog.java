package com.example.book;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.security.AccessControlContext;

public class ListCustomDialog extends Dialog {

    private Context context;
    private CustomDialogClicklistner customDialogClickListener;
    private TextView tvTitle,tvNegative,tvPositive;

    public ListCustomDialog(@NonNull Context context, CustomDialogClicklistner customDialogClickListener) {
        super(context);
        this.context =context;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_custdiaolg);

        tvTitle = findViewById(R.id.option_codetype_dialog_title_tv);
        tvPositive = findViewById(R.id.option_codetype_dialog_positive);
        tvNegative = findViewById(R.id.option_codetype_dialog_negative);

        tvPositive.setOnClickListener(v -> {
            // 저장버튼 클릭
            this.customDialogClickListener.onPositiveClick();
            dismiss();
        });
        tvNegative.setOnClickListener(v -> {
            // 취소버튼 클릭
            this.customDialogClickListener.onNegativeClick();
            dismiss();
        });

    }
}
