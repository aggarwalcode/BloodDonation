package com.lifelineblood.lifelineblood.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.adapterclass.BloodNeedy;
import com.lifelineblood.lifelineblood.modelclass.BloodNeedyModel;

import java.util.ArrayList;
import java.util.List;

public class DonateBlood extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView rvBloodNeedy;
    BloodNeedy mAdapter;
    List<BloodNeedyModel> bloodNeedyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        rvBloodNeedy = findViewById(R.id.rvBloodNeedy);
        rvBloodNeedy.setLayoutManager(new LinearLayoutManager(this));
        rvBloodNeedy.addItemDecoration(new DividerItemDecoration(
                rvBloodNeedy.getContext(), DividerItemDecoration.VERTICAL));

        databaseReference = FirebaseDatabase.getInstance().getReference("bloodneedy");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    BloodNeedyModel bloodNeedyModel = postSnapshot.getValue(BloodNeedyModel.class);
                    bloodNeedyList.add(bloodNeedyModel);
                    mAdapter = new BloodNeedy(getApplicationContext(), bloodNeedyList);
                    rvBloodNeedy.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
