package com.anirudh.japatracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="mantras.db";
    private static final int DB_VERSION=1;

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists mantras (mantra_name varchar,times int);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Empty to silent compiler
    }

    public void updateMantra(String name,int count){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("mantra_name",name);
        values.put("times",count);

        db.insertWithOnConflict("mantras",null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int getMantraCount(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cur=db.rawQuery("Select times from mantras where mantra_name=?",new String[]{name});
        int count=0;
        if(cur.moveToFirst()){
            count=cur.getInt(0);
        }
        cur.close();
        return count;
    }

    public int deleteAll(){
        SQLiteDatabase db=getWritableDatabase();
        try{
            db.execSQL("delete from mantras");
        } catch (SQLException e) {
            return 1;
        }
        return 0;
    }
}
