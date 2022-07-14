package com.example.a2048test;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        mainActivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = findViewById(R.id.tv_currentscore);

    }

    public void clearScore() {
        score = 0;
    }

    public void showScore() {
        tvScore.setText(score+"");

    }

    public void addScore(int s) {
        score += s;
        showScore();
    }


    private int score = 0;
    private TextView tvScore;
    private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity() {
        return mainActivity;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnhelp:
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setTitle("hey,guy！");
                dialog2.setMessage("这么简单的游戏你确定需要帮助？");
                dialog2.setNegativeButton("继续玩~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog2.show();
                break;
            case R.id.btnquit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("提示：");
                dialog.setMessage("你确定要离开吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            case R.id.btnrestart:
                AlertDialog.Builder dialog3 = new AlertDialog.Builder(this);
                dialog3.setTitle("提示：");
                dialog3.setMessage("你确定重新开始吗？");
                dialog3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog3.show();
                break;
            default:
                break;
        }
    }
}