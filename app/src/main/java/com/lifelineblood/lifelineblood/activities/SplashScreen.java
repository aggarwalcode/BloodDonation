package com.lifelineblood.lifelineblood.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.lifelineblood.lifelineblood.R;

public class SplashScreen extends AppCompatActivity {
    private static boolean isRegistered;
    private static boolean isLogedin;
    public static SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences("activity.SplashScreen", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        editor.putBoolean("isRegistered", false);
        editor.putBoolean("isLogedin", false);
        editor.putString("emailID",null);
        editor.apply();

        isRegistered = mPreferences.getBoolean("isRegistered",false);
        isLogedin = mPreferences.getBoolean("isLogedin",false);

        if(!isLogedin){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if(!isRegistered){
            Intent intent = new Intent(this, SignUpAct.class);
            startActivity(intent);
            finish();
        }

        else if(isRegistered && isLogedin){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            Toast.makeText(SplashScreen.this, R.string.error_splash, Toast.LENGTH_LONG).show();
        }

        /*SharedPreferences mPreferences = getSharedPreferences("activity.SplashScreen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isLogedin", null);
        editor.putBoolean("isRegistered", null);
        editor.commit();*/
    }
}
