package com.example.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "diary.db";



    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tableName (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, year TEXT NOT NULL, month TEXT NOT NULL, day TEXT NOT NULL, mood_id TEXT NOT NULL, imageUrl TEXT NOT NULL, weather_id TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tableName");
        onCreate(db);
    }

    //SELECT 문 (목록들을 조회)
    public ArrayList<DiaryItem> getDiaryList(){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db_db = ((MainActivity)MainActivity.context_main).db;

        Cursor cursor = db_db.rawQuery("SELECT * FROM DiaryList ", null); //DESC는 내림차순 정렬
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

    public void deleteDiary(int _id){
        SQLiteDatabase db_db = ((MainActivity)MainActivity.context_main).db;
        db_db.execSQL("DELETE FROM tableName WHERE id = '" + _id + "'");
    }


    public void UpdateDiary(int _id, String _title, String _content, String _year, String _month, String _day, String _mood_id, String _imageURL, String weather_id) {
        SQLiteDatabase db_db = ((MainActivity) MainActivity.context_main).db;
        db_db.execSQL("UPDATE tableName SET title='" + _title + "', content='" + _content + "', year ='" + _year + "', month ='" + _month + "', day ='" + _day + "', mood_id ='" + _mood_id + "', imageUrl='" + _imageURL + "', weather_id='" + weather_id + "' WHERE id ='" + _id + "'");

    }



}
