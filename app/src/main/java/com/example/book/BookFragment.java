package com.example.book;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class BookFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private BookListAdapter bookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BookList> arrayList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_book,container,false);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        bookListAdapter = new BookListAdapter(getContext(),getActivity());
        recyclerView.setAdapter(bookListAdapter);

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("book"); // DB 테이블 연결

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
                    for (int a = 0; a<bookList.size();a++){
                        String push = bookList.get(a).toString();
                        String bookname = dataSnapshot.child(push).child("bookname").getValue().toString();
                        String price = dataSnapshot.child(push).child("price").getValue().toString();
                        String publish = dataSnapshot.child(push).child("publish").getValue().toString();

                        bookListAdapter.addItem(new BookList(bookname, price, publish));
                    }
                    bookListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("Fraglike", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        return rootView;
    }
}
