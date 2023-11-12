package com.example.book;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class BookIdFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private BookFragAdapter bookFragAdapter;
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<BookList> items = new ArrayList<BookList>();

    public static Object bookIdfragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bookid, container, false);

        bookIdfragment = this;

        recyclerView = rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        bookFragAdapter = new BookFragAdapter(getContext(), getActivity());
        recyclerView.setAdapter(bookFragAdapter);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("person"); // DB 테이블 연결

        initdatabase();

        return rootView;
    }

    public void bt_cs(BookList position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

                        Toast.makeText(getActivity(),"clear",Toast.LENGTH_SHORT).show();
                        initdatabase();
                        bookFragAdapter.notifyDataSetChanged();
                    }
                });

        builder.setPositiveButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initdatabase();
                        Toast.makeText(getActivity(),"you canceled",Toast.LENGTH_SHORT).show();
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

                        bookFragAdapter.addItem(new BookList(push, bookname, price, publish));
                    }
                    bookFragAdapter.notifyDataSetChanged();
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
