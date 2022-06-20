package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class WriteActivity extends Activity {


    CalendarView calView;
    TextView tvDate;
    Button btn_calendar, weather, mood, btn_save, btnAddFile;
    View calendar_layout;
    EditText title, content;




    DBHelper w_dbHelper;
    SQLiteDatabase w_db;



    int m_year, m_month, m_day; //날짜값 따로 저장할 변수
    int mood_id, weather_id;
    String _mood_id, _weather_id;

    ImageView img;;

    private Uri filePath;
    String filename;


    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_page);


        w_dbHelper = ((MainActivity)MainActivity.context_main).dbHelper;
        w_db = ((MainActivity)MainActivity.context_main).db;



        tvDate = (TextView) findViewById(R.id.tvDate);
        calView = (CalendarView) findViewById(R.id.calendarView);
        btn_calendar = (Button) findViewById(R.id.btn_calendar);
        calendar_layout = findViewById(R.id.calendar_layout);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        weather = (Button) findViewById(R.id.weather);
        registerForContextMenu(weather);
        mood = (Button) findViewById(R.id.mood);
        registerForContextMenu(mood);
        btn_save = (Button) findViewById(R.id.btn_save);
        btnAddFile = (Button) findViewById(R.id.btnAddFile);

        img = (ImageView) findViewById(R.id.img);


        firebaseAuth =  FirebaseAuth.getInstance();

        _mood_id = "1";
        _weather_id = "1";

        firebaseAuth.getFirebaseAuthSettings();


        weather.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                MenuBuilder menuBuilder = new MenuBuilder(WriteActivity.this);
                MenuInflater inflater = new MenuInflater(WriteActivity.this);
                inflater.inflate(R.menu.weather_menu, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(WriteActivity.this, menuBuilder, v);
                optionsMenu.setForceShowIcon(true);


                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                                //Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

                                switch (item.getItemId())
                                {
                                    //weather_menu 클릭했을 때
                                    case R.id.sunny:
                                        //toast.setText("Select Menu1");
                                        weather_id=1;
                                        _weather_id = String.valueOf(weather_id);
                                        weather.setBackgroundResource(R.drawable.sunny);
                                        break;
                                    case R.id.cloudy:
                                        //toast.setText("Select Menu2");
                                        weather_id=2;
                                        _weather_id = String.valueOf(weather_id);
                                        weather.setBackgroundResource(R.drawable.cloudy);
                                        break;
                                    case R.id.snowy:
                                        //toast.setText("Select Menu3");
                                        weather_id=3;
                                        _weather_id = String.valueOf(weather_id);
                                        weather.setBackgroundResource(R.drawable.snowy);
                                        break;
                                    case R.id.rainy:
                                        //toast.setText("Select Menu4");
                                        weather_id=4;
                                        _weather_id = String.valueOf(weather_id);
                                        weather.setBackgroundResource(R.drawable.rainy);
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

        mood.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder = new MenuBuilder(WriteActivity.this);
                MenuInflater inflater = new MenuInflater(WriteActivity.this);
                inflater.inflate(R.menu.mood_menu, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(WriteActivity.this, menuBuilder, v);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                                //Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

                                switch(item.getItemId())
                                {
                                    //mood_menu 클릭했을 때
                                    case R.id.moon1:
                                        //toast.setText("Select Menu1");
                                        mood_id=1;
                                        _mood_id = String.valueOf(mood_id);
                                        mood.setBackgroundResource(R.drawable.moon1);
                                        break;
                                    case R.id.moon2:
                                        //toast.setText("Select Menu2");
                                        mood_id=2;
                                        _mood_id = String.valueOf(mood_id);
                                        mood.setBackgroundResource(R.drawable.moon2);
                                        break;
                                    case R.id.moon3:
                                        //toast.setText("Select Menu3");
                                        mood_id=3;
                                        _mood_id = String.valueOf(mood_id);
                                        mood.setBackgroundResource(R.drawable.moon3);
                                        break;
                                    case R.id.moon4:
                                        //toast.setText("Select Menu4");
                                        mood_id=4;
                                        _mood_id = String.valueOf(mood_id);
                                        mood.setBackgroundResource(R.drawable.moon4);
                                        break;
                                    case R.id.moon5:
                                        //toast.setText("Select Menu5");
                                        mood_id=5;
                                        _mood_id = String.valueOf(mood_id);
                                        mood.setBackgroundResource(R.drawable.moon5);
                                        break;
                                }
                                //toast.show();
                                return false;
                            }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionsMenu.show();
            }
        });


        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
        tvDate.setText(sdf.format(date));

        m_year=Integer.parseInt(sdf_year.format(date));
        m_month=Integer.parseInt(sdf_month.format(date));
        m_day=Integer.parseInt(sdf_day.format(date));




        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                month=month+1;
                m_year = year;
                m_month = month;
                m_day = day;


                tvDate.setText(String.format("%d년 %d월 %d일", year, month, day));


            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(calendar_layout.getVisibility()==View.GONE)
                    calendar_layout.setVisibility(View.VISIBLE);
                else
                    calendar_layout.setVisibility(View.GONE);
            }
        });




        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                String _title = title.getText().toString();
                String _content = content.getText().toString();

                String _year = String.valueOf(m_year);
                String _month = String.valueOf(m_month);
                String _day = String.valueOf(m_day);



                if(_title.trim().getBytes().length<=0 || _content.trim().getBytes().length<=0){
                    Toast.makeText(getApplicationContext(), "입력되지않은 부분이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadFile();

                    String _imageUrl = filename;

                    w_db.execSQL("INSERT INTO tableName (title, content, year, month, day, mood_id, imageUrl, weather_id) VALUES ('" + _title + "', '" + _content + "', '" + _year + "','" + _month + "','" + _day + "','" + _mood_id + "','" + _imageUrl + "','" + _weather_id + "');");

                    //Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();

                    title.setText("");
                    content.setText("");

                    MainActivity ma = (MainActivity)MainActivity.main_activity;
                    ma.finish();

                    Intent intent = new Intent(WriteActivity.this, DiarycountActivity.class);
                    startActivity(intent);

                    if(filePath == null){
                        finish();
                    }


                }


            }
        });

        btnAddFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."),0);
            }
        });






    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            filename = formatter.format(now) + ".png";
            StorageReference storageRef = storage.getReferenceFromUrl("gs://fblogin-9d6c0.appspot.com").child("images/" + filename);
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            //Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }



}


