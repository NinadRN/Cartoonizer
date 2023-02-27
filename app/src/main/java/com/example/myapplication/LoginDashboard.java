package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class LoginDashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ImageButton imageButton_1,imageButoon_2,imageButoon_3;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dashboard);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        imageButton_1 = (ImageButton) findViewById(R.id.cartoon1);
        imageButoon_2 = (ImageButton) findViewById(R.id.cartoon2);
        imageButoon_3 = (ImageButton) findViewById(R.id.cartoon3);


        //next activity


        imageButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Choose_Activity.class));
                finish();
            }
        });
        imageButoon_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Choose_Activity.class));
                finish();
            }
        });
        imageButoon_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Choose_Activity.class));
                finish();
            }
        });


        //action bar
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.Closedrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.optHome) {

                } else if (id == R.id.optSticker) {

                } else if (id == R.id.optMessage) {

                } else if (id == R.id.optAbout) {


                } else if (id == R.id.optother) {

                } else if (id == R.id.optRate) {

                } else {


                }


                return true;
            }
        });


    }
}
