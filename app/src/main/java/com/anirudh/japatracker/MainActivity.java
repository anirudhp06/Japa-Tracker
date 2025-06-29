package com.anirudh.japatracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
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

        try(SQLiteDatabase myDB = openOrCreateDatabase("mantras", Context.MODE_PRIVATE, null)){
            myDB.execSQL("Create table if not exists mantras (mantra_name varchar,times int);");

            Cursor cur=myDB.rawQuery("select * from mantras where name=? or name=?",new String[]{gayatri_text.getTag().toString(),raghavendra_text.getTag().toString()});
            int counter=0;
            while(cur.moveToNext()){
                String name=cur.getString(0);
                int times=cur.getInt(1);

                if (name.equals(gayatri_text.getTag().toString())) {
                    gayatri_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",times));
                } else if (name.equals(raghavendra_text.getTag().toString())) {
                    raghavendra_text.setText(String.format(Locale.ENGLISH,"Total Recited: %d",times));
                }
            }
            cur.close();
        }
        catch (SQLException e){
            Log.d("MainActivity",e.toString());
        }

        gayatri_button.setOnClickListener(v->CalculateRecite.count(this,gayatri_text));
        raghavendra_buton.setOnClickListener(v->CalculateRecite.count(this,raghavendra_text));

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