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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.CustomViewHolder> {

    private ArrayList<BookList> items = new ArrayList<BookList>();
    private Context context;
    private Activity activity;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public BookAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void addItem(BookList data){
        items.add(data);
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.onBind(items.get(position));

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        holder.bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ListNextActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 새롭게  액티비티 생성하게됨
                context.startActivity(intent);
            }
        });
        holder.bt_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListCustomDialog octDialog = new ListCustomDialog(activity, new CustomDialogClicklistner() {
                    @Override
                    public void onPositiveClick() {
                        //리스트 기능 추가
                        String bookname = items.get(position).getBookname();
                        String price = items.get(position).getPrice();
                        String publish = items.get(position).getPublish();

                        String push = FirebaseDatabase.getInstance().getReference().push().getKey();

                        databaseReference.child("person").child(push).child("bookname").setValue(bookname);
                        databaseReference.child("person").child(push).child("price").setValue(price);
                        databaseReference.child("person").child(push).child("publish").setValue(publish);
                        databaseReference.child("person").child(push).child("push").setValue(push);

                        Toast.makeText(context,"complete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegativeClick() {
                        Toast.makeText(context,"you canceled",Toast.LENGTH_SHORT).show();
                    }
                });
                octDialog.setCanceledOnTouchOutside(true);
                octDialog.setCancelable(true);
                octDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                octDialog.show();
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
        Button bt_list;
        Button bt_dialog;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.book_name = itemView.findViewById(R.id.book_name);
            this.book_price = itemView.findViewById(R.id.book_price);
            this.book_publish = itemView.findViewById(R.id.book_publish);
            this.bt_dialog = itemView.findViewById(R.id.bt_dialog);
            this.bt_list = itemView.findViewById(R.id.bt_list);
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
