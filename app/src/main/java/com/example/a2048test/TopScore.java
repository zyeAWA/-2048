package com.example.a2048test;

import android.content.Context;
import android.content.SharedPreferences;

public class TopScore { private SharedPreferences sp;
    public TopScore(Context context){
        sp = context.getSharedPreferences("TopScore", context.MODE_PRIVATE);
    }
    public int getTopScore(){
        int TopScore = sp.getInt("TopScore", 0);
        return TopScore;
    }
    public void setTopScore(int TopScore){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("TopScore", TopScore);
        editor.commit();
    }
}