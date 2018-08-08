package com.lifelineblood.lifelineblood.adapterclass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.activities.Messages;
import com.lifelineblood.lifelineblood.modelclass.ChatAttributes;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ChatsViewHolder> {

    Context context;
    List<ChatAttributes> chatList;

    public MessagesAdapter(Messages messages, List<ChatAttributes> chatList) {
        this.context = messages;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chats_layout, viewGroup, false);
        return new ChatsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder chatsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder {
        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
