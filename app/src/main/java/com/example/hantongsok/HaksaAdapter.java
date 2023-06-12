package com.example.hantongsok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HaksaAdapter extends RecyclerView.Adapter<HaksaAdapter.ViewHolder> {

    private List<Post> postList;

    public HaksaAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.haksa_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.tvPostNumber.setText(String.valueOf(post.getPostNumber()));
        holder.tvPostTitle.setText(post.getTitle());
        holder.tvPostAuthor.setText(post.getAuthor());
        holder.tvPostDate.setText(post.getDate());

        holder.tvPostTitle.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPostNumber, tvPostTitle, tvPostAuthor, tvPostDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPostNumber = itemView.findViewById(R.id.tvPostNumber);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvPostAuthor = itemView.findViewById(R.id.tvPostAuthor);
            tvPostDate = itemView.findViewById(R.id.tvPostDate);
        }
    }
}
