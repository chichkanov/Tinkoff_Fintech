package com.chichkanov.tinkoff_fintech.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.tinkoff_fintech.R;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MESSAGE_YOU = 0;
    private static final int MESSAGE_MATE = 1;

    private List<ConversationItem> dataset;

    public ConversationAdapter(List<ConversationItem> dataset) {
        this.dataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MESSAGE_YOU:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_you, parent, false);
                return new YourMessageViewHolder(view);
            case MESSAGE_MATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_mate, parent, false);
                return new MateMessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConversationItem object = dataset.get(position);
        if (object != null) {
            switch (object.getType()) {
                case MESSAGE_MATE:
                    ((MateMessageViewHolder) holder).message.setText(object.getMessage());
                    ((MateMessageViewHolder) holder).date.setText(object.getDate());
                    break;
                case MESSAGE_YOU:
                    ((YourMessageViewHolder) holder).message.setText(object.getMessage());
                    ((YourMessageViewHolder) holder).date.setText(object.getDate());
                    break;
            }
        }
    }

    public void addDialog(ConversationItem conversationItem) {
        dataset.add(conversationItem);
        notifyItemInserted(dataset.size());
    }

    public void setItems(List<ConversationItem> conversationItem) {
        dataset = conversationItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        ConversationItem object = dataset.get(position);
        if (object != null) return object.getType();
        return 0;
    }

    public void addItem(String message, int type) {
        dataset.add(new ConversationItem(message, type));
        this.notifyItemInserted(dataset.size() - 1);
    }

    public static class YourMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView date;

        public YourMessageViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.tv_conversation_you);
            date = (TextView) itemView.findViewById(R.id.tv_conversation_you_date);
        }
    }

    public static class MateMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView date;

        public MateMessageViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.tv_conversation_mate);
            date = (TextView) itemView.findViewById(R.id.tv_conversation_mate_date);
        }
    }
}
