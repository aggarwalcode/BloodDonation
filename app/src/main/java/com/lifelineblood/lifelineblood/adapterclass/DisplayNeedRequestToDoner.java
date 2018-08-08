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
import com.lifelineblood.lifelineblood.modelclass.BloodRequesteeDetails;
import com.lifelineblood.lifelineblood.modelclass.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DisplayNeedRequestToDoner extends RecyclerView.Adapter<DisplayNeedRequestToDoner.NeedyViewHolder>{

    public Context context;

    public static List<BloodRequesteeDetails> mBloodNeedyLi;

    public DisplayNeedRequestToDoner() {
    }

    public DisplayNeedRequestToDoner(Context applicationContext, List<BloodRequesteeDetails> bloodNeedyList) {
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
        BloodRequesteeDetails mBloodNeedyI = mBloodNeedyLi.get(i);

        needyViewHolder.nameOfNeedy.setText(mBloodNeedyI.getName());
        needyViewHolder.locationOfNeedy.setText(mBloodNeedyI.getAddress());
        needyViewHolder.blood_Group.setText(mBloodNeedyI.getBloodgroup());
        //needyViewHolder.displayPic.setImageResource(R.drawable.dp);
        /*Picasso.get().load("https://lh3.googleusercontent.com/-pjLD_WmdDXY/AAAAAAAAAAI/AAAAAAAAAAA/YDhZJ6ieBLg/s64-c/100166102678933298897.jpg?size=50")
                .into(needyViewHolder.displayPic);*/
        Picasso.get()
                .load("https://pikmail.herokuapp.com/"+mBloodNeedyI.getEmail()+"?size=50")
                .transform(new CircleTransform())
                .into(needyViewHolder.displayPic);


        /*needyViewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mBloodNeedyLi.size();
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
