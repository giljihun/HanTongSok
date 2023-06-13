package com.example.hantongsok;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HaksaAdapter extends RecyclerView.Adapter<HaksaAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;

    public HaksaAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.haksa_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.postNumberTextView.setText(String.valueOf(post.getPostNumber()));
        holder.titleTextView.setText(post.getTitle());
        holder.authorTextView.setText(post.getAuthor());
        holder.dateTextView.setText(post.getDate());

        // Add separator line between list items
        if (position < getItemCount() - 1) {
            holder.separatorView.setVisibility(View.VISIBLE);
        } else {
            holder.separatorView.setVisibility(View.GONE);
        }

        // 타이틀 클릭 이벤트 처리
        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(post);
            }
        });
    }

    private void showDialog(Post post) {
        // 다이얼로그 생성
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_content);

        // 다이얼로그 내용 설정
        TextView titleTextView = dialog.findViewById(R.id.dialog_title);
        TextView slash1 = dialog.findViewById(R.id.slash1);
        TextView authorTextView = dialog.findViewById(R.id.dialog_author);
        TextView dateTextView = dialog.findViewById(R.id.dialog_date);

        slash1.setText(" | ");
        titleTextView.setText(post.getTitle());
        authorTextView.setText(post.getAuthor());
        dateTextView.setText(post.getDate());

        // "Dismiss" 버튼 클릭 이벤트 처리
        ImageButton dismissButton = dialog.findViewById(R.id.noButton);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

        // 다이얼로그 표시
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postNumberTextView;
        TextView titleTextView;
        TextView authorTextView;
        TextView dateTextView;
        View separatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postNumberTextView = itemView.findViewById(R.id.tvPostNumber);
            titleTextView = itemView.findViewById(R.id.tvPostTitle);
            authorTextView = itemView.findViewById(R.id.tvPostAuthor);
            dateTextView = itemView.findViewById(R.id.tvPostDate);
            separatorView = itemView.findViewById(R.id.separatorView);

            titleTextView.setSelected(true);
        }
    }
}

