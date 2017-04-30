package com.chichkanov.tinkoff_fintech.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chichkanov.tinkoff_fintech.models.DialogsItem;
import com.chichkanov.tinkoff_fintech.OnItemClickListener;
import com.chichkanov.tinkoff_fintech.R;

import java.util.List;

public class DialogsAdapter extends RecyclerView.Adapter<DialogsAdapter.ViewHolder> {

    private List<DialogsItem> dataset;
    private OnItemClickListener clickListener;

    public DialogsAdapter(List<DialogsItem> dataset, OnItemClickListener clickListener) {
        this.dataset = dataset;
        this.clickListener = clickListener;
    }

    @Override
    public DialogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_dialog, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(DialogsAdapter.ViewHolder holder, int position) {
        holder.title.setText(dataset.get(position).getTitle());
        holder.desc.setText(dataset.get(position).getDesc());
        holder.date.setText(dataset.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addDialog(DialogsItem dialogItem) {
        dataset.add(dialogItem);
        notifyItemInserted(dataset.size());
    }

    public void setItems(List<DialogsItem> dialogItems) {
        dataset = dialogItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView title;
        public TextView desc;
        public TextView date;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_dialog_title);
            desc = (TextView) view.findViewById(R.id.tv_dialog_desc);
            date = (TextView) view.findViewById(R.id.tv_dialog_date);
            imageView = (ImageView) view.findViewById(R.id.iv_dialog_photo);
            setListener(listener);
        }

        private void setListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
