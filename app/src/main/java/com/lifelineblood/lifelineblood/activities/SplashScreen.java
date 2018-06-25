package com.lifelineblood.lifelineblood.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static boolean isRegistered;
    private static boolean isLogedin;
    public static SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences("activity.SplashScreen", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.putBoolean("isRegistered", false);
        editor.putBoolean("isLogedin", false);
        editor.commit();

        isRegistered = mPreferences.getBoolean("isRegistered",false);
        isLogedin = mPreferences.getBoolean("isLogedin",false);

        if(!isLogedin){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if(!isRegistered){
            Intent intent = new Intent(this, RegisterationAct.class);
            startActivity(intent);
            finish();
        }

        else if(isRegistered==true && isLogedin==true){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        /*SharedPreferences mPreferences = getSharedPreferences("activity.SplashScreen", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isLogedin", null);
        editor.putBoolean("isRegistered", null);
        editor.commit();*/
    }
}
