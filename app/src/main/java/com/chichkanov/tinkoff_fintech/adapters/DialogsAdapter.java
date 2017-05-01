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

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

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
        String userName = dataset.get(position).getTitle();
        holder.title.setText(userName);
        holder.desc.setText(dataset.get(position).getDesc());
        holder.date.setText(dataset.get(position).getDate());

        IImageLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(holder.avatarView, "Тут будет ссылка на аватар", userName);
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

        AvatarView avatarView;
        public TextView title;
        public TextView desc;
        public TextView date;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_dialog_title);
            desc = (TextView) view.findViewById(R.id.tv_dialog_desc);
            date = (TextView) view.findViewById(R.id.tv_dialog_date);
            avatarView = (AvatarView) view.findViewById(R.id.av_dialog_photo);
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
