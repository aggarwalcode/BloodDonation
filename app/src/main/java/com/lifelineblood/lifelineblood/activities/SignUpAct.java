package com.lifelineblood.lifelineblood.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lifelineblood.lifelineblood.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpAct extends AppCompatActivity {

    private EditText inputEmail, inputPassword, contactNum, nameView;
    private Button btnSignIn, btnRegister, btnResetPassword;
    Spinner bloodGroupSpn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    String email,nameVal,mobNum,bloodGrp;
    static String fBaseId;
    boolean allowSubmit;
    SimpleDateFormat timestamp;
    private DatabaseReference databaseReference;
    String[] bloodGrpArr = new String[]{"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance

        btnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (Button) findViewById(R.id.email_register);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnResetPassword = (Button) findViewById(R.id.reset_pass);
        contactNum = (EditText) findViewById(R.id.mobileNo);
        nameView = (EditText) findViewById(R.id.name);
        bloodGroupSpn = (Spinner) findViewById(R.id.bloodgroup);

        ArrayAdapter<String> adapterBgp = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, bloodGrpArr
        );
        bloodGroupSpn.setAdapter(adapterBgp);

        auth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAct.this, ResetPassAct.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAct.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpAct.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(SignUpAct.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpAct.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            fBaseId = auth.getCurrentUser().getUid();

                            addUser();
                            startActivity(new Intent(SignUpAct.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void addUser() {

        allowSubmit = true;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(fBaseId);

        email = inputEmail.getText().toString().trim();
        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))
        {
            allowSubmit = false;
            inputEmail.setError("Enter Correct Email");
        }

        nameVal = nameView.getText().toString().trim();
        if (TextUtils.isEmpty(nameVal)){

            allowSubmit = false;
            nameView.setError(getString(R.string.blank_field));
        }

        mobNum = contactNum.getText().toString().trim();
        if (TextUtils.isEmpty(mobNum)&&(mobNum.length()<10)){

            allowSubmit = false;
            nameView.setError(getString(R.string.mobNum_size));
        }

        bloodGrp = bloodGroupSpn.getSelectedItem().toString().trim();
        if (bloodGroupSpn.getSelectedItem().toString()=="Select Blood Group"){

            allowSubmit = false;
            ((TextView)bloodGroupSpn.getSelectedView()).setError("None Selected");
        }

        timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String sdf = timestamp.format(Calendar.getInstance().getTime());

        Map newUser = new HashMap();
        newUser.put("name",nameVal);
        newUser.put("email",email);
        newUser.put("mobileNo",mobNum);
        newUser.put("bloodGroup",bloodGrp);
        newUser.put("timestamp",sdf);
        newUser.put("fBaseId",fBaseId);

        if(allowSubmit){
            databaseReference.setValue(newUser);
            Toast.makeText(getApplicationContext(), "Registeration Successful", Toast.LENGTH_SHORT).show();

            SharedPreferences mPreferences = getSharedPreferences("activity.SplashScreen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("isLogedin", true);
            editor.putBoolean("isRegistered", true);
            editor.apply();
        }
        else {
            Toast.makeText(getApplicationContext(), "Some Error",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
