package com.anirudh.japatracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private ImageButton login;
    private Button gayatri_button,raghavendra_buton;
    private TextView gayatri_text,raghavendra_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login Button
        login=(ImageButton) findViewById(R.id.login_fab);

        //Counting Buttons
        gayatri_button =(Button) findViewById(R.id.GayatriMantra);
        raghavendra_buton =(Button) findViewById(R.id.RaghavendraMantra);

        //Text Views
        gayatri_text=(TextView) findViewById(R.id.gayatri_text);
        raghavendra_text=(TextView) findViewById(R.id.raghavendra_text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Will proceed to login",Snackbar.LENGTH_LONG)
                        .setAnchorView((R.id.login_fab))
                        .setAction("Login_Button",null).show();
            }
        });

        gayatri_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateRecite.count(gayatri_text);
            }
        });
        raghavendra_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateRecite.count(raghavendra_text);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
            }, REQUEST_CODE);
        } else {
            // Below Android 13
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CODE);
        }

    }
    }