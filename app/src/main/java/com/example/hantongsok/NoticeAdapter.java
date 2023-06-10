package com.example.hantongsok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private List<NoticeItem> noticeItems;

    public NoticeAdapter(List<NoticeItem> noticeItems) {
        this.noticeItems = noticeItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoticeItem noticeItem = noticeItems.get(position);
        holder.textTitle.setText(noticeItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return noticeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
        }
    }
}
