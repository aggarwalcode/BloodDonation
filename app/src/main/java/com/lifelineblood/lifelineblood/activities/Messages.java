package com.lifelineblood.lifelineblood.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.adapterclass.MessagesAdapter;
import com.lifelineblood.lifelineblood.modelclass.ChatAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.lifelineblood.lifelineblood.activities.LoginActivity.auth;

public class Messages extends AppCompatActivity {

    MessagesAdapter messagesAdapter;
    RecyclerView rvMessagesAct;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        List<ChatAttributes> chatList = new ArrayList<>();

        rvMessagesAct = findViewById(R.id.rvBloodNeedy);
        rvMessagesAct.setLayoutManager(new LinearLayoutManager(this));
        rvMessagesAct.addItemDecoration(new DividerItemDecoration(
                rvMessagesAct.getContext(), DividerItemDecoration.VERTICAL));

        messagesAdapter = new MessagesAdapter(this, chatList);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("chatServer").child(auth.getUid());
    }
}
