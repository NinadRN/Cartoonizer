package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose_Activity extends AppCompatActivity {

    Button mChoosephoto, mroundnutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mChoosephoto = findViewById(R.id.ChoosePht);
        mroundnutton = findViewById(R.id.roundbutton);


        mChoosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                finish();
            }
        });

        mroundnutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                finish();
            }
        });
    }
}