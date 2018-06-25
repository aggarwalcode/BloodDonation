package com.lifelineblood.lifelineblood.adapterclass;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.modelclass.BloodNeedyModel;

import java.util.List;

public class BloodNeedy extends RecyclerView.Adapter<BloodNeedy.NeedyViewHolder>{

    public Context context;

    public static List<BloodNeedyModel> mBloodNeedyLi;

    public BloodNeedy() {
    }

    public BloodNeedy(Context applicationContext, List<BloodNeedyModel> bloodNeedyList) {
        this.context = applicationContext;
        this.mBloodNeedyLi=bloodNeedyList;
    }

    @NonNull
    @Override
    public NeedyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.blood_needy_layout, viewGroup, false);

        return new NeedyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NeedyViewHolder needyViewHolder, int i) {
        BloodNeedyModel mBloodNeedyI = mBloodNeedyLi.get(i);

        needyViewHolder.nameOfNeedy.setText("mBloodNeedyI.getName()");
        needyViewHolder.locationOfNeedy.setText("mBloodNeedyI.getAddress()");
        needyViewHolder.blood_Group.setText("mBloodNeedyI.getBloodgroup()");
        needyViewHolder.displayPic.setImageResource(R.drawable.dp);

        /*needyViewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class NeedyViewHolder extends RecyclerView.ViewHolder {
        ImageView displayPic;
        TextView nameOfNeedy,locationOfNeedy,blood_Group;
        Button buttona,buttonb,buttonc;

        public NeedyViewHolder(@NonNull View itemView) {

            super(itemView);
            nameOfNeedy=itemView.findViewById(R.id.nameofNeedy);
            locationOfNeedy=itemView.findViewById(R.id.locationOfNeedy);
            blood_Group=itemView.findViewById(R.id.blood_Group);
            displayPic = itemView.findViewById(R.id.displayPic);
        }
    }
}
