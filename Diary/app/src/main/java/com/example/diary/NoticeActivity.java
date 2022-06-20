package com.example.diary;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NoticeActivity extends Activity {

    TextView notice_time;
    Switch notice_setting;
    Button btn_notice_time, btn_ok, btn_cancel;
    TimePicker timepicker;
    View alarm_layout, tp_layout;
    SharedPreferences sharedPreferences, sharedPreferences1, sharedPreferences2;

    String check, x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_page);

        notice_time = (TextView) findViewById(R.id.notice_time);
        notice_setting = (Switch) findViewById(R.id.notice_setting);
        btn_notice_time = (Button) findViewById(R.id.btn_notice_time);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        timepicker = (TimePicker) findViewById(R.id.timepicker);
        alarm_layout = findViewById(R.id.alarm_layout);
        tp_layout = findViewById(R.id.tp_layout);


        PackageManager pm = NoticeActivity.this.getPackageManager();
        ComponentName receiver = new ComponentName(NoticeActivity.this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(NoticeActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoticeActivity.this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);





        sharedPreferences = getSharedPreferences("n_setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        sharedPreferences1 = getSharedPreferences("bool", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();

        sharedPreferences2 = getSharedPreferences("text", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();




        x = sharedPreferences.getString("inputText","");

        if(x.equals("1")){
            notice_setting.setChecked(true);
            String time_text = sharedPreferences2.getString("text","");
            notice_time.setText(time_text);
        }
        else{
            notice_setting.setChecked(false);
            editor2.putString("text", "설정이 되어있지 않습니다");
            editor2.commit();
            String time_text = sharedPreferences2.getString("text","");
            notice_time.setText(time_text);



        }


        notice_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (notice_setting.isChecked() == true) {
                    check = String.valueOf(1);
                    editor.putString("inputText", check);
                    editor.commit();
                    editor1.putBoolean("bool", true);
                    editor1.commit();
                }
                else {
                    tp_layout.setVisibility(View.INVISIBLE);
                    check = String.valueOf(0);
                    editor.putString("inputText", check);
                    editor.commit();
                    editor1.putBoolean("bool", false);
                    editor1.commit();
                    notice_time.setText("설정이 되어있지 않습니다");

                    if(PendingIntent.getBroadcast(NoticeActivity.this, 0, alarmIntent, 0) != null && alarmManager != null){
                        alarmManager.cancel(pendingIntent);
                    }
                    pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);


                }
            }
        });



        btn_notice_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current = sharedPreferences.getString("inputText","");

                if(current.equals("1")){
                    if (tp_layout.getVisibility() == View.VISIBLE) {
                        tp_layout.setVisibility(View.INVISIBLE);
                    } else {
                        tp_layout.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(NoticeActivity.this, "알림여부를 먼저 설정해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });







        timepicker.setIs24HourView(true);

        SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);


        //Toast.makeText(NoticeActivity.this, "알람 설정 유지중", Toast.LENGTH_SHORT).show();



        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());



        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

        if(Build.VERSION.SDK_INT >= 23){
            timepicker.setHour(pre_hour);
            timepicker.setMinute(pre_minute);
        }
        else{
            timepicker.setCurrentHour(pre_hour);
            timepicker.setCurrentMinute(pre_minute);
        }



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, hour_24, minute;
                String am_pm;
                if(Build.VERSION.SDK_INT>=23){
                    hour_24=timepicker.getHour();
                    minute=timepicker.getMinute();
                }
                else{
                    hour_24=timepicker.getCurrentHour();
                    minute=timepicker.getCurrentMinute();
                }
                if(hour_24>12){
                    am_pm="PM";
                    hour=hour_24-12;
                }
                else{
                    hour=hour_24;
                    am_pm="AM";
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);

                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("a hh시 mm분", Locale.getDefault()).format(currentDateTime);

                notice_time.setText(date_text);

                editor2.putString("text", date_text);
                editor2.commit();


                Toast.makeText(NoticeActivity.this, "알람이 설정되었습니다", Toast.LENGTH_SHORT).show();


                SharedPreferences.Editor editor = getSharedPreferences("alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();



                Boolean dailyNotify = sharedPreferences1.getBoolean("bool", Boolean.parseBoolean(""));

                if(dailyNotify) {
                    if (alarmManager != null) {
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        }
                    }
                    pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                }



            }



        });

    }


}
