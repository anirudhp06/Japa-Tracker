package com.anirudh.japatracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.text.InputType;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private ImageButton login,reset_button;
    private Button gayatri_button,raghavendra_buton;
    private TextView gayatri_text,raghavendra_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Login Button
        login=(ImageButton) findViewById(R.id.login_fab);

        //Reset Button
        reset_button=(ImageButton) findViewById(R.id.resetValues);

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


        DBHelper dbHelper=new DBHelper(this);
        int get_count=dbHelper.getMantraCount(gayatri_text.getTag().toString());
        gayatri_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",get_count));

        get_count=dbHelper.getMantraCount(raghavendra_text.getTag().toString());
        raghavendra_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",get_count));

        gayatri_button.setOnClickListener(v->CalculateRecite.count(this,gayatri_text));
        raghavendra_buton.setOnClickListener(v->CalculateRecite.count(this,raghavendra_text));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion!");
        builder.setMessage("Are you sure you want to proceed? THIS CANNOT BE UNDONE!!");

        builder.setPositiveButton("Okay", (dialog, which) -> {
            dbHelper.deleteAll();
            gayatri_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",dbHelper
                    .getMantraCount(gayatri_text.getTag().toString())));
            raghavendra_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",dbHelper
                    .getMantraCount(raghavendra_text.getTag().toString())));

            Toast.makeText(MainActivity.this, "Deleted all records", Toast.LENGTH_SHORT)
                    .show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(MainActivity.this, "Cancelled Deletion", Toast.LENGTH_SHORT)
                    .show();
            dialog.dismiss();
        });
        reset_button.setOnClickListener(v->builder.show());

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