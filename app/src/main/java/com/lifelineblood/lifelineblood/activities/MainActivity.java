package com.lifelineblood.lifelineblood.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.lifelineblood.lifelineblood.R;
import com.lifelineblood.lifelineblood.fragments.RequestForm;
import com.lifelineblood.lifelineblood.fragments.RequestingForBlood;
import com.lifelineblood.lifelineblood.fragments.WantToDonate;
import com.lifelineblood.lifelineblood.fragments.HelpPage;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RequestingForBlood.OnFragmentInteractionListener,
        WantToDonate.OnFragmentInteractionListener,
        com.lifelineblood.lifelineblood.fragments.RequestForm.OnFragmentInteractionListener,
        com.lifelineblood.lifelineblood.fragments.HelpPage.OnFragmentInteractionListener{

    private static boolean isRegistered,isLogedin;
    SharedPreferences mPreferences;
    TextView textViewEidNav;
    public static String emailId;
    Context context;
    DatabaseReference databaseReference;

    private final static String TAG_FRAGMENT = "REQUEST_FORM_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*isRegistered = mPreferences.getBoolean("isRegistered",false);
        isLogedin = mPreferences.getBoolean("isLogedin",false);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context= getApplication();

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        bottom_navigation.setSelectedItemId(R.id.nav_home);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.nav_message){
                    Intent intent = new Intent(MainActivity.this,Messages.class);
                    startActivity(intent);
                }
                if(id==R.id.nav_help){
                    Fragment fragment = new HelpPage();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if(id==R.id.nav_account){
                    Intent intent = new Intent(MainActivity.this,MyAccount.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.bringToFront();
        drawer.requestLayout();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        textViewEidNav = (TextView) header.findViewById(R.id.textViewEidNav);
        mPreferences = context.getSharedPreferences("activity.SplashScreen", MODE_PRIVATE);
        emailId =mPreferences.getString("emailId",null);

        textViewEidNav.setText(emailId);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        else {
            super.onBackPressed();
        }

        final RequestingForBlood fragment = (RequestingForBlood) getSupportFragmentManager().
                findFragmentByTag("RequestingForBlood");
        try{
            fragment.allowBackPressed();
            getSupportFragmentManager().popBackStack();
            return;
        }catch (Exception e){

        }

        final RequestForm fragmentb = (RequestForm) getSupportFragmentManager().
                findFragmentByTag("RequestForm");
        try{
            fragmentb.allowBackPressed();
            getSupportFragmentManager().popBackStack();
            return;
        }catch (Exception e){

        }
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_request) {
            // Handle the camera action
        } else if (id == R.id.nav_donation) {

        } else if (id == R.id.logOut) {

            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("isLogedin", false);
            editor.putString("emailID",null);
            editor.apply();

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void donateBlood(View view) {

        /*SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isNeedy",true);
        editor.apply();*/
        Fragment fragment = new WantToDonate();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment,"WantToDonate");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void needBlood(View view) {
        /*SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("isDoner",true);
        editor.apply();*/

        Fragment fragment = new RequestingForBlood();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment,"RequestingForBlood");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}