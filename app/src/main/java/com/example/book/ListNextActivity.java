package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListNextActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private BookNextAdapter bookNextAdapter;
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<BookList> items = new ArrayList<BookList>();

    public static Object listNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_next);

        listNextActivity = this;

        recyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        bookNextAdapter = new BookNextAdapter(getBaseContext(), ListNextActivity.this);
        recyclerView.setAdapter(bookNextAdapter);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("person"); // DB 테이블 연결

        initdatabase();

    }

    public void bt_cs(BookList position){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListNextActivity.this);
        builder.setTitle("삭제 확인");
        builder.setMessage("삭제하시겠습니까?");

        builder.setNegativeButton("예",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String push = position.getPush();

                        databaseReference.child(push).child("bookname").setValue(null);
                        databaseReference.child(push).child("price").setValue(null);
                        databaseReference.child(push).child("publish").setValue(null);
                        databaseReference.child(push).child("push").setValue(null);

                        Toast.makeText(ListNextActivity.this,"clear",Toast.LENGTH_SHORT).show();
                        initdatabase();
                        bookNextAdapter.notifyDataSetChanged();
                    }
                });

        builder.setPositiveButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListNextActivity.this,"you canceled",Toast.LENGTH_SHORT).show();
                    }
                });

        builder.show();
        builder.setCancelable(true);
    }

    public void initdatabase(){
        databaseReference = database.getReference("person"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator iterator = dataSnapshot.getChildren().iterator();
                    ArrayList<String> bookList = new ArrayList<>();

                    while (iterator.hasNext()) {
                        bookList.add(((DataSnapshot) iterator.next()).getKey());
                    }
                    for (int a = 0; a < bookList.size(); a++) {
                        String push = bookList.get(a).toString();
                        String bookname = dataSnapshot.child(push).child("bookname").getValue().toString();
                        String price = dataSnapshot.child(push).child("price").getValue().toString();
                        String publish = dataSnapshot.child(push).child("publish").getValue().toString();

                        bookNextAdapter.addItem(new BookList(push, bookname, price, publish));
                    }
                    bookNextAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("Fraglike", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }

}