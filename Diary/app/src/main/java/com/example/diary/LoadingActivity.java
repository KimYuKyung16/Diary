package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences1 = getSharedPreferences("pw_setting", MODE_PRIVATE);; ///5.10
                String x1 = sharedPreferences1.getString("pw_setting","");

                if(x1.equals("1")){
                    Intent intent = new Intent(LoadingActivity.this, PassWordActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        },1800);


    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                finish();
            }
        }, 2000);
    }
}
