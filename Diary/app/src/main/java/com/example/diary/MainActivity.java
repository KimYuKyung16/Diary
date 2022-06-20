package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    public static Activity main_activity;
    Button main_menu;
    TextView diary_num_all, diary_num_all_count, diary_need, diary_need_count;
    public static View main_layout;
    BottomNavigationView bottom_navigation;

    public static Context context_main;

    public static DBHelper dbHelper;
    public static SQLiteDatabase db = null;

    int idiary_num_all_count;
    String sdiary_num_all_count;
    String sdiary_num1, sdiary_num2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        main_activity = MainActivity.this;

        context_main = this;

        main_layout=findViewById(R.id.main_layout);
        main_menu=(Button) findViewById(R.id.main_menu);

        bottom_navigation=findViewById(R.id.bottomNavigationView);

        diary_num_all = (TextView) findViewById(R.id.diary_num_all);
        diary_num_all_count = (TextView) findViewById(R.id.diary_num_all_count);
        diary_need = (TextView) findViewById(R.id.diary_need);
        diary_need_count = (TextView) findViewById(R.id.diary_need_count);



        dbHelper = new DBHelper(this, 4); //
        db = dbHelper.getWritableDatabase(); //

        Cursor cursor = db.rawQuery("SELECT * FROM tableName ", null); //DESC는 내림차순 정렬
        idiary_num_all_count = cursor.getCount();
        sdiary_num_all_count = String.valueOf(idiary_num_all_count);
        diary_num_all_count.setText(sdiary_num_all_count);


        sdiary_num1 = String.valueOf(5-idiary_num_all_count);
        sdiary_num2 = String.valueOf(10-idiary_num_all_count);

        if(idiary_num_all_count<=5){
            diary_need_count.setText(sdiary_num1);
        }
        else if(idiary_num_all_count>5 && idiary_num_all_count<=10){
            diary_need_count.setText(sdiary_num2);
        }
        else{
            diary_need_count.setText("0");
        }




        SharedPreferences sharedPreferences = getSharedPreferences("background", MODE_PRIVATE); ///5.10
        String x = sharedPreferences.getString("background","");

        if(x==null){
            main_layout.setBackgroundResource(R.drawable.background_first);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.background_second);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.background_third);
            }
        }

        if(x.equals("1")){
            main_layout.setBackgroundResource(R.drawable.cat);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.cat2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.cat3);
            }
        }
        else if(x.equals("2")){
            main_layout.setBackgroundResource(R.drawable.sky_night);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.sky_night2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.sky_night3);
            }
        }
        else if(x.equals("3")){
            main_layout.setBackgroundResource(R.drawable.sky);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.sky2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.sky3);
            }
        }
        else if(x.equals("4")){
            main_layout.setBackgroundResource(R.drawable.cd);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.cd2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.cd3);
            }
        }
        else if(x.equals("5")){
            main_layout.setBackgroundResource(R.drawable.flower);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.flower2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.flower3);
            }
        }
        else if(x.equals("6")){
            main_layout.setBackgroundResource(R.drawable.purple);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.purple2);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.purple3);
            }
        }
        else if(x.equals("7")){
            main_layout.setBackgroundResource(R.drawable.background_first);
            if(idiary_num_all_count>=5){
                main_layout.setBackgroundResource(R.drawable.background_second);
            }
            if(idiary_num_all_count>=10){
                main_layout.setBackgroundResource(R.drawable.background_third);
            }
        }






        //메인메뉴 아이콘을 클릭했을 때 이벤트
        main_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bottom_navigation.setVisibility(View.VISIBLE);
                main_menu.setVisibility(View.GONE);
                diary_num_all.setVisibility(View.VISIBLE); //
                diary_num_all_count.setVisibility(View.VISIBLE); //
                diary_need.setVisibility(View.VISIBLE); //
                diary_need_count.setVisibility(View.VISIBLE); //

            }
        });




        //바탕화면을 터치했을 때 이벤트
        main_layout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();


                if(action==MotionEvent.ACTION_DOWN){
                    if(main_menu.getVisibility()==View.VISIBLE) {
                        main_menu.setVisibility(View.GONE);
                        bottom_navigation.setVisibility(View.VISIBLE);
                        diary_num_all.setVisibility(View.VISIBLE);
                        diary_num_all_count.setVisibility(View.VISIBLE);
                        diary_need.setVisibility(View.VISIBLE);
                        diary_need_count.setVisibility(View.VISIBLE);

                    }
                    else {
                        main_menu.setVisibility(View.VISIBLE);
                        bottom_navigation.setVisibility(View.GONE);
                        diary_num_all.setVisibility(View.INVISIBLE);
                        diary_num_all_count.setVisibility(View.INVISIBLE);
                        diary_need.setVisibility(View.INVISIBLE);
                        diary_need_count.setVisibility(View.INVISIBLE);

                    }
                }
                return true;
            }
        });


        //하단 네비게이션바에 있는 메뉴들을 선택했을 때
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()){
                    case R.id.write1:
                        Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.vertical_enter,R.anim.none);
                        break;
                    case R.id.list1:
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.chatting1:
                        intent = new Intent(MainActivity.this, ExLoginActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.setting1:
                        intent = new Intent(MainActivity.this, MainSettingActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });




    }




}

