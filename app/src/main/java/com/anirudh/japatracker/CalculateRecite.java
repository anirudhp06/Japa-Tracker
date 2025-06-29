package com.anirudh.japatracker;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

public class CalculateRecite {
    static int stat_num=0;
    public static void count(Context ctx,TextView textView){
        String[] info=textView.getText().toString().split(":");
        int num=Integer.parseInt(info[1].trim());
        num++;
        stat_num=num;
        String message=String.format(Locale.ENGLISH,"%s: %d",info[0],num);
        textView.setText(message);
        calculateDB(ctx,textView.getTag().toString());
    }
    static void calculateDB(Context ctx,String tag){
        try (SQLiteDatabase myDB = ctx.openOrCreateDatabase("mantras", MODE_PRIVATE, null)) {
            myDB.execSQL("Create table if not exists mantras (mantra_name varchar,times int);");
            Log.d("CalculateRecite", "Created | exists 'mantras' table");

            Cursor cur=myDB.rawQuery("Select * from mantras where mantra_name=?",new String[]{tag});
            if(cur.moveToFirst()){
                myDB.execSQL("Update mantras set times =? where mantra_name=?",new String[]{String.valueOf(stat_num),tag});
                Log.d("CalculateRecite",String.format(Locale.ENGLISH,"%s is recited %d times.",tag,stat_num));
            }
            else{
                myDB.execSQL("Insert into mantras(mantra_name,times) values(?,?)",new String[]{tag,String.valueOf(stat_num)});
                Log.d("CalculateRecite",String.format(Locale.ENGLISH,"%s is new record which got inserted with recite %d times.",tag,stat_num));

            }
            cur.close();
        }
        catch(SQLException e) {
            Log.d("CalculateRecite",e.toString());
        }
    }
}
