package com.lifelineblood.lifelineblood.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.modelclass.CircleTransform;
import com.lifelineblood.lifelineblood.modelclass.UserCredientials;
import com.squareup.picasso.Picasso;

import static com.lifelineblood.lifelineblood.activities.LoginActivity.auth;

public class MyAccount extends AppCompatActivity {

    DatabaseReference databaseReference;
    UserCredientials userCredientials;

    Button saveButton;
    EditText nameView,ageView,genderView,emailView,mobileNumView,locationView;
    private Spinner bloodGrpSpnView;
    private ProgressBar progressBar;
    RadioGroup radioGrpView;
    RadioButton radioButtonVal,radioMaleView,radioFemaleView;
    ImageView myDisplayPicView;

    String sexVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        saveButton = (Button) findViewById(R.id.save_myAcc);
        nameView = (EditText) findViewById(R.id.nameAMA);
        ageView = (EditText) findViewById(R.id.ageAMA);
        emailView = (EditText) findViewById(R.id.emailAMA);
        mobileNumView = (EditText) findViewById(R.id.mobileNoAMA);
        locationView = (EditText) findViewById(R.id.locationAMA);
        myDisplayPicView = (ImageView) findViewById(R.id.myDisplayPicAMA);
        radioGrpView = (RadioGroup) findViewById(R.id.radioGrpAMA);
        radioMaleView = (RadioButton) findViewById(R.id.radioMaleAMA);
        radioFemaleView = (RadioButton) findViewById(R.id.radioFemaleAMA);
        bloodGrpSpnView=(Spinner) findViewById(R.id.bloodgroupAMA);

        String[] bloodGroupsArr = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapterBgp = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, bloodGroupsArr
        );
        bloodGrpSpnView.setAdapter(adapterBgp);

        userCredientials = new UserCredientials();
        databaseReference = FirebaseDatabase.getInstance().
                getReference().child("users").child(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userCredientials = dataSnapshot.getValue(UserCredientials.class);
                nameView.setText(userCredientials.getName());
                ageView.setText((String.valueOf(userCredientials.getAge())));
                emailView.setText(userCredientials.getEmail());
                mobileNumView.setText(userCredientials.getContactNum());
                locationView.setText(userCredientials.getAddress());

                String bg = userCredientials.getBloodgroup();
                ArrayAdapter myAdap = (ArrayAdapter) bloodGrpSpnView.getAdapter();
                int pos = myAdap.getPosition(bg);
                bloodGrpSpnView.setSelection(pos);

                String ch = userCredientials.getSex();
                switch (ch){
                    case "male":
                        radioGrpView.check(R.id.radioMaleAMA);
                        break;
                    case "female":
                        radioGrpView.check(R.id.radioFemaleAMA);
                        break;

                        default:
                        break;
                }
                /*if (userCredientials.getSex().equals("male")){
                    radioGrpView.check(R.id.radioMaleAMA);
                }
                else if (userCredientials.getSex().equals("female")){
                    radioGrpView.check(R.id.radioFemaleAMA);
                }
                else {}*/

                Picasso.get()
                        .load("https://pikmail.herokuapp.com/"+userCredientials.getEmail()+"?size=50")
                        .transform(new CircleTransform())
                        .into(myDisplayPicView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameVal;
                int ageVal;
                String contactNumVal;
                String emailVal;
                String addressVal;
                String bloodGrp;

                nameVal= String.valueOf(nameView.getText());
                contactNumVal = String.valueOf(mobileNumView.getText());
                emailVal = String.valueOf(emailView.getText());
                addressVal = String.valueOf(locationView.getText());
                ageVal = Integer.parseInt(String.valueOf(ageView.getText()));
                bloodGrp = bloodGrpSpnView.getSelectedItem().toString();

                int id = radioGrpView.getCheckedRadioButtonId();
                if(id>0) {
                    RadioButton radioButton = findViewById(id);
                    sexVal = radioButton.getText().toString().trim().toLowerCase();
                }
                databaseReference.child("name").setValue(nameVal);
                databaseReference.child("age").setValue(ageVal);
                databaseReference.child("sex").setValue(sexVal);
                databaseReference.child("contactNum").setValue(contactNumVal);
                databaseReference.child("email").setValue(emailVal);
                databaseReference.child("address").setValue(addressVal);
                databaseReference.child("bloodgroup").setValue(bloodGrp);
                finish();
            }
        });
    }
}