package com.example.diary;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends Activity implements TextWatcher {


    public static Activity list_activity;
    public static Context context_list;
    private CustomAdapter mAdapter;
    private ArrayList<DiaryItem> mDiaryItems;
    public static RecyclerView mRv_diary;


    DBHelper l_dbHelper;
    SQLiteDatabase l_db;
    EditText search;
    ImageView search_img;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_list_page);

        list_activity = ListActivity.this;
        l_dbHelper = ((MainActivity)MainActivity.context_main).dbHelper;
        l_db = ((MainActivity)MainActivity.context_main).db;

        context_list = this; //따로 추가

        search_img = (ImageView) findViewById(R.id.search_img);
        mRv_diary = findViewById(R.id.rv_diary);
        search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(this);

        search_img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(search.getVisibility()==View.VISIBLE){
                    search.setText(null);
                    search.setVisibility(View.INVISIBLE);
                }
                else{
                    search.setVisibility(View.VISIBLE);
                }
            }
        });


        loadRecentDB();



    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }



    private void loadRecentDB(){
        mDiaryItems = getDiaryList();
        if(mAdapter == null){
            mAdapter = new CustomAdapter(mDiaryItems, this);
            mRv_diary.setHasFixedSize(true);
            mRv_diary.setAdapter(mAdapter);

            RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
            mRv_diary.addItemDecoration(spaceDecoration);
        }
    }


    public ArrayList<DiaryItem> getDiaryList(){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();

        Cursor cursor = l_db.rawQuery("SELECT * FROM tableName ", null); //DESC는 내림차순 정렬
        if(cursor.getCount() != 0){
            //조회온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String year = cursor.getString(cursor.getColumnIndex("year"));
                String month = cursor.getString(cursor.getColumnIndex("month"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                String mood_id = cursor.getString(cursor.getColumnIndex("mood_id"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String weather_id = cursor.getString(cursor.getColumnIndex("weather_id"));



                DiaryItem diaryItem = new DiaryItem();
                diaryItem.setId(id);
                diaryItem.setTitle(title);
                diaryItem.setContent(content);
                diaryItem.setYear(year);
                diaryItem.setMonth(month);
                diaryItem.setDay(day);
                diaryItem.setMood_id(mood_id);
                diaryItem.setImageUrl(imageUrl);
                diaryItem.setWeather_id(weather_id);
                diaryItems.add(diaryItem);

            }
        }
        cursor.close();

        return diaryItems;
    }







}
