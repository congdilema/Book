package com.example.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText et_user_name;
    private Button bt_save;
    private Button bt_next;
    private Button bt_frag;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_save = (Button) findViewById(R.id.bt_save);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_frag = (Button) findViewById(R.id.bt_frag);
        et_user_name = (EditText) findViewById(R.id.et_user_name);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = et_user_name.getText().toString();
                databaseReference.child("new").push().child("message").setValue(string);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent1);
            }
        });
        bt_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });
    }
}