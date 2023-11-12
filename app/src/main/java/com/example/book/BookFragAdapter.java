package com.example.book;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookFragAdapter extends RecyclerView.Adapter<BookFragAdapter.CustomViewHolder> {

    private ArrayList<BookList> items = new ArrayList<BookList>();
    private Context context;
    private Activity activity;
    private BookFragAdapter bookFragAdapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    BookIdFragment bookIdFragment = (BookIdFragment) BookIdFragment.bookIdfragment;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public BookFragAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void addItem(BookList data){
        items.add(data);
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public BookFragAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistid_item, parent, false);
        BookFragAdapter.CustomViewHolder holder = new BookFragAdapter.CustomViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookFragAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.onBind(items.get(position));

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        bookFragAdapter = new BookFragAdapter(context, activity);

        holder.bt_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookList posi = items.get(position);
                items.clear();
                bookIdFragment.bt_cs(posi);

            }
        });


    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (items != null ? items.size() : 0);
        //return data.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView book_name;
        TextView book_price;
        TextView book_publish;
        Button bt_dialog;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.book_profile);
            this.book_name = itemView.findViewById(R.id.booklist_name2);
            this.book_price = itemView.findViewById(R.id.booklist_price2);
            this.book_publish = itemView.findViewById(R.id.booklist_publish2);
            this.bt_dialog = itemView.findViewById(R.id.bt_listdialog2);
        }
        @SuppressLint("SetTextI18n")
        public void onBind(BookList data){
            iv_profile.setImageResource(R.drawable.dog);
            book_name.setText(data.getBookname());
            book_price.setText(data.getPrice());
            book_publish.setText(data.getPublish());
        }
    }
}
