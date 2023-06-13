package com.example.hantongsok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HaksaAdapter extends RecyclerView.Adapter<HaksaAdapter.ViewHolder> {

    private List<Post> posts;

    public HaksaAdapter(List<Post> posts) {
        this.posts = posts;
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

