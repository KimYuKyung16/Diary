package com.example.diary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class SettingActivity extends Activity {

    Button button1, button2, button3, button4, button5, button6, btn_initialize;
    View main_view;


    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        main_view = ((MainActivity)MainActivity.context_main).main_layout;


        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        btn_initialize = (Button) findViewById(R.id.btn_initialize);

        sharedPreferences = getSharedPreferences("background", MODE_PRIVATE);

        SharedPreferences.Editor editor= sharedPreferences.edit();



        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.cat);
                String num1 = String.valueOf(1);
                editor.putString("background",num1);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.spring);
                String num2 = String.valueOf(2);
                editor.putString("background",num2);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.sky);
                String num3 = String.valueOf(3);
                editor.putString("background",num3);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //.setBackgroundResource(R.drawable.cd);
                String num4 = String.valueOf(4);
                editor.putString("background",num4);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.flower);
                String num5 = String.valueOf(5);
                editor.putString("background",num5);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.purple);
                String num6 = String.valueOf(6);
                editor.putString("background",num6);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });

        btn_initialize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_view.setBackgroundResource(R.drawable.background_first);
                String num7 = String.valueOf(7);
                editor.putString("background",num7);
                editor.commit();
                Toast.makeText(SettingActivity.this, "테마가 선택되었습니다. 앱을 다시 실행해야 적용됩니다", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
