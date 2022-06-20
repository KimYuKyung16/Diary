package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DiaryListClickActivity extends Activity {

    public static Activity diary_list_click_activity;

    TextView dl_title;
    TextView dl_content;
    TextView dl_date;
    ImageView dl_img, dl_weather, dl_mood;
    Button more_menu, music;


    String mtitle ="";
    String mcontent ="";
    String myear ="";
    String mmonth ="";
    String mday ="";

    String mimageUrl="";

    String mmood_id="";
    String mweather_id="";

    SQLiteDatabase dca_db;

    int mid;
    DBHelper dca_dbHelper;

    MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarylistclick_page);

        diary_list_click_activity = DiaryListClickActivity.this;



        dca_db = ((MainActivity)MainActivity.context_main).db;
        dca_dbHelper = ((MainActivity)MainActivity.context_main).dbHelper;

        dl_title = (TextView) findViewById(R.id.dl_title);
        dl_content = (TextView) findViewById(R.id.dl_content);
        dl_date = (TextView) findViewById(R.id.dl_date);
        dl_img = (ImageView) findViewById(R.id.dl_img);
        dl_mood = (ImageView) findViewById(R.id.dl_mood);
        dl_weather = (ImageView) findViewById(R.id.dl_weather);
        more_menu = (Button) findViewById(R.id.more_menu) ;
        music = (Button) findViewById(R.id.music) ;


        dl_content.setMovementMethod(new ScrollingMovementMethod());


        Bundle extras = getIntent().getExtras();

        mid = extras.getInt("id"); /////////////
        mtitle = extras.getString("title");
        mcontent = extras.getString("content");
        myear = extras.getString("year");
        mmonth = extras.getString("month");
        mday = extras.getString("day");
        mimageUrl = extras.getString("imageUrl");
        mmood_id = extras.getString("mood_id");
        mweather_id = extras.getString("weather_id");

        int iyear = Integer.parseInt(myear);
        int imonth = Integer.parseInt(mmonth);
        int iday = Integer.parseInt(mday);



        dl_date.setText(String.format("%d년 %d월 %d일", iyear, imonth, iday));

        dl_title.setText(mtitle);
        dl_content.setText(mcontent);


        if(mmood_id.equals("1")) {
            dl_mood.setImageResource(R.drawable.moon1);
        }
        else if(mmood_id.equals("2")) {
            dl_mood.setImageResource(R.drawable.moon2);
        }
        else if(mmood_id.equals("3")) {
            dl_mood.setImageResource(R.drawable.moon3);
        }
        else if(mmood_id.equals("4")) {
            dl_mood.setImageResource(R.drawable.moon4);
        }
        else if(mmood_id.equals("5")) {
            dl_mood.setImageResource(R.drawable.moon5);
        }


        if(mweather_id.equals("1")) {
            dl_weather.setImageResource(R.drawable.sunny);
        }
        else if(mweather_id.equals("2")) {
            dl_weather.setImageResource(R.drawable.cloudy);
        }
        else if(mweather_id.equals("3")) {
            dl_weather.setImageResource(R.drawable.snowy);
        }
        else if(mweather_id.equals("4")) {
            dl_weather.setImageResource(R.drawable.rainy);
        }


        more_menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder = new MenuBuilder(DiaryListClickActivity.this);
                MenuInflater inflater = new MenuInflater(DiaryListClickActivity.this);
                inflater.inflate(R.menu.more_menu, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(DiaryListClickActivity.this, menuBuilder, v);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        //Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

                        switch(item.getItemId())
                        {
                            //mood_menu 클릭했을 때
                            case R.id.delete:
                                //toast.setText("삭제");

                                if(mp!=null){
                                    mp.stop();
                                }

                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReferenceFromUrl("gs://fblogin-9d6c0.appspot.com").child("images/" + mimageUrl);
                                storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(getApplicationContext(), "삭제 완료!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Toast.makeText(getApplicationContext(), "삭제 실패!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dca_dbHelper.deleteDiary(mid);
                                ListActivity LA = (ListActivity) ListActivity.list_activity;
                                LA.finish();
                                MainActivity MA = (MainActivity) MainActivity.main_activity;
                                MA.finish();
                                Intent intent = new Intent(DiaryListClickActivity.this, MainActivity.class);
                                startActivity(intent);
                                Intent intent1 = new Intent(DiaryListClickActivity.this, ListActivity.class);
                                startActivity(intent1);
                                finish();
                                break;

                            case R.id.modify:
                                //toast.setText("수정");

                                if(mp!=null){
                                    mp.stop();
                                }

                                Intent intent2 = new Intent(DiaryListClickActivity.this, ModifyActivity.class);
                                intent2.putExtra("dl_id", mid);
                                intent2.putExtra("dl_title", mtitle);
                                intent2.putExtra("dl_content", mcontent);
                                intent2.putExtra("dl_year", myear);
                                intent2.putExtra("dl_month", mmonth);
                                intent2.putExtra("dl_day", mday);
                                intent2.putExtra("dl_imageUrl", mimageUrl);
                                intent2.putExtra("dl_mood_id", mmood_id);
                                intent2.putExtra("dl_weather_id", mweather_id);
                                startActivity(intent2);
                                break;
                        }
                        //toast.show();
                        return false;
                    }
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) { }
                });
                optionsMenu.show();
            }
        });







        music.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder1 = new MenuBuilder(DiaryListClickActivity.this);
                MenuInflater inflater1 = new MenuInflater(DiaryListClickActivity.this);
                inflater1.inflate(R.menu.music, menuBuilder1);
                MenuPopupHelper optionsMenu1 = new MenuPopupHelper(DiaryListClickActivity.this, menuBuilder1, v);
                optionsMenu1.setForceShowIcon(true);

                menuBuilder1.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        //Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

                        switch(item.getItemId())
                        {
                            //mood_menu 클릭했을 때
                            case R.id.music1:
                                if(mp==null) {
                                    //toast.setText("음악1 선택");
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music);
                                    mp.start();
                                }
                                else{
                                    mp.stop();
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music);
                                    mp.start();
                                }
                                break;

                            case R.id.music2:
                                if(mp==null) {
                                    //toast.setText("음악2 선택");
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music2);
                                    mp.start();
                                }
                                else{
                                    mp.stop();
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.background_music2);
                                    mp.start();
                                }
                                break;
                        }
                        //toast.show();
                        return false;
                    }
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) { }
                });
                optionsMenu1.show();
            }
        });





        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fblogin-9d6c0.appspot.com");
        storageRef.child("images/" + mimageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .override(250, 250)
                        .into(dl_img);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                //Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

        dl_img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryListClickActivity.this, BigPictureActivity.class);
                intent.putExtra("image", mimageUrl);
                startActivity(intent);
            }
        });



    }

    //뒤로가기할 때 노래꺼짐
    public void onBackPressed(){
        if(mp!=null){
            mp.stop();
        }
        finish();
    }




}
