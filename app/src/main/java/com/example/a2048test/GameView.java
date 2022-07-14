package com.example.a2048test;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();

    }

    public GameView(Context context) {
        super(context);
        initGameView();

    }

    //游戏初始化方法
    private void initGameView(){

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardWitch(),getCardWitch());
        setOnTouchListener(new View.OnTouchListener(){

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)){
                            if (offsetX < -5) {
                                swipeLeft();
                            }else if(offsetX > 5) {
                                swipeRight();
                            }
                        }else {
                            if (offsetY < -5) {
                                swipeUp();
                            }else if(offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }

                return true;
            }
        });
    }
    private int getCardWitch() {
        //声明屏幕对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();
        int cardWitch;
        //提取屏幕宽
        cardWitch = displayMetrics.widthPixels;
        return (cardWitch - 10) / 4;
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startGame();
    }


    //添加卡片的方法
    private void addCards(int  cardWidth, int cardHeight) {

        Card c;
        for (int y = 0; y < 4 ; y++) {
            for (int x = 0; x < 4 ; x++) {
                c = new Card(getContext());
                c.setNum(2); //给每张卡片赋值原本为0
                addView(c, cardWidth, cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }
    //开始游戏的方法
    public void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
                emptyPoint.add(new Point(x,y));
            }
        }
        addRandomNum();
        addRandomNum();
    }
    //添加随机数
    private void addRandomNum(){

        emptyPoint.clear();

        for (int y = 0; y < 4 ; y++) {
            for (int x = 0; x < 4 ; x++) {
                if(cardsMap[x][y].getNum() <= 0) {
                    emptyPoint.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoint.remove((int)(Math.random() * emptyPoint.size())) ;
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    //向左滑动
    public void swipeLeft(){
        checkFull();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x+1; x1 < 4; x1++) {
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            /*
                             *  将下标为（x，y）所在位置的卡片上的数字
                             * 设置为，坐标为(x1,y)所在位置的卡片上的值；
                             * 第二步，将坐标（x1，y）所在位置的卡片上的数字
                             * 设置为0
                             *    （即：变成空卡片）
                             */
                            cardsMap[x][y].setNum(
                                    cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;

                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().
                                    addScore(cardsMap[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        boolean Full = checkFull();
        if(Full == true) {

            checkComplete();

        }else
        {
            addRandomNum();
        }

    }
    private boolean checkFull() {

        boolean Full = true;
        jump1:
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum() == 0) {
                    Full = false;
                    break jump1;
                }
            }
        }
        return Full;
    }
    //向右滑动
    public void swipeRight(){
        boolean meger = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {
                for (int x1 = x-1; x1 >= 0; x1--) {
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            /*
                             *  将下标为（x，y）所在位置的卡片上的数字
                             * 设置为，坐标为(x1,y)所在位置的卡片上的值；
                             * 第二步，将坐标（x1，y）所在位置的卡片上的数字
                             * 设置为0
                             *    （即：变成空卡片）
                             */
                            cardsMap[x][y].setNum(
                                    cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            meger =true;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().
                                    addScore(cardsMap[x][y].getNum());
                            meger =true;
                        }break;
                    }
                }
            }
        }
        if(meger){
            addRandomNum();
            checkComplete();
        }
    }
    //向上滑动
    public void swipeUp(){
        boolean meger = false;
        for (int x= 0; x< 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y+1; y1 < 4; y1++) {
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            /*
                             *  将下标为（x，y）所在位置的卡片上的数字
                             * 设置为，坐标为(x1,y)所在位置的卡片上的值；
                             * 第二步，将坐标（x1，y）所在位置的卡片上的数字
                             * 设置为0
                             *    （即：变成空卡片）
                             */
                            cardsMap[x][y].setNum(
                                    cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            meger =true;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().
                                    addScore(cardsMap[x][y].getNum());
                            meger =true;
                        }
                        break;
                    }
                }
            }
        }
        if(meger){
            addRandomNum();
            checkComplete();
        }
    }
    //向下滑动
    public void swipeDown(){
        boolean meger = false;
        for (int x = 0; x< 4; x++) {
            for (int y = 3; y>= 0;y--) {
                for (int y1 = y-1; y1 >=0; y1--) {
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            /*
                             *  将下标为（x，y）所在位置的卡片上的数字
                             * 设置为，坐标为(x1,y)所在位置的卡片上的值；
                             * 第二步，将坐标（x1，y）所在位置的卡片上的数字
                             * 设置为0
                             *    （即：变成空卡片）
                             */
                            cardsMap[x][y].setNum(
                                    cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            meger =true;
                            break;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().
                                    addScore(cardsMap[x][y].getNum());
                            meger =true;
                        }
                        break;
                    }
                }
            }
        }
        if(meger){
            addRandomNum();
            checkComplete();
        }
    }
    //检查游戏是否结束的方法
    public void checkComplete(){
        boolean complete = true;//记录游戏的状态
        ALL:
        for (int y = 0; y <4; y++) {
            for (int x = 0; x <4; x++) {
                if(cardsMap[x][y].getNum() == 0 ||
                        (x>0 && cardsMap[x][y].equals(cardsMap[x-1][y])) ||
                        (x<3 && cardsMap[x][y].equals(cardsMap[x+1][y])) ||
                        (y>0 && cardsMap[x][y].equals(cardsMap[x][y-1])) ||
                        (y<3 && cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete =false;
                    break ALL;
                    //break 用于跳出循环，但是只能跳出一层循环
                    //break ALL:可跳出所有的循环
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext())
                    .setTitle("你好")
                    .setMessage("游戏已结束")
                    .setPositiveButton("重来", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog,
                                            int which) {
                            startGame();
                        }
                    })
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog,
                                            int which) {
                            MainActivity.getMainActivity().finish();
                        }
                    }).show();
        }
    }
    //存放卡片的二维数组,便于对卡片进行操作
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<Point>();
}