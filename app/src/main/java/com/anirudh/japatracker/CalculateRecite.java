package com.anirudh.japatracker;
import android.widget.TextView;


import java.util.Locale;

public class CalculateRecite {
    public static void count(TextView textView){
        String[] info=textView.getText().toString().split(":");
        int num=Integer.parseInt(info[1].trim());
        num++;
        String message=String.format(Locale.ENGLISH,"%s: %d",info[0],num);
        textView.setText(message);
    }
}
