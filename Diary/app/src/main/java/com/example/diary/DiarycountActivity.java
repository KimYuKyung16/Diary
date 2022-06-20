package com.example.diary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiarycountActivity extends Activity {

    TextView diary_count, diary_need_count;
    Button btn_ok;
    SQLiteDatabase dca_db;
    int diary_num;
    String sdiary_num, sdiary_num1, sdiary_num2;

    public static Context context_dca;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarycount_page);

        context_dca = this; //따로 추가

        diary_count = (TextView) findViewById(R.id.diary_count);
        diary_need_count = (TextView) findViewById(R.id.diary_need_count);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        dca_db = ((MainActivity)MainActivity.context_main).db;

        Cursor cursor = dca_db.rawQuery("SELECT * FROM tableName ", null); //DESC는 내림차순 정렬
        diary_num = cursor.getCount();
        cursor.close();

        sdiary_num = String.valueOf(diary_num);
        diary_count.setText(sdiary_num);

        sdiary_num1 = String.valueOf(5-diary_num);
        sdiary_num2 = String.valueOf(10-diary_num);

        if(diary_num<=5){
            diary_need_count.setText(sdiary_num1);
        }
        else if(diary_num>5 && diary_num<=10){
            diary_need_count.setText(sdiary_num2);
        }
        else{
            diary_need_count.setText("0");
        }


        btn_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiarycountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
