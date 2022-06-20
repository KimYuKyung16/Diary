package com.example.diary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LockActivity extends AppCompatActivity {

    EditText changepw, current_pw;
    Switch lock_setting;
    Button btn_password_change, btn_pw_save;
    View pw_change_layout;

    String changePW;

    String locksetting;
    String default_pw;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;

    String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_page);

        changepw = (EditText) findViewById(R.id.changepw);
        current_pw = (EditText) findViewById(R.id.current_pw);
        lock_setting = (Switch) findViewById(R.id.lock_setting);
        btn_password_change = (Button) findViewById(R.id.btn_password_change);
        btn_pw_save = (Button) findViewById(R.id.btn_pw_save);
        pw_change_layout = findViewById(R.id.pw_change_layout);


        sharedPreferences = getSharedPreferences("pw_setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        sharedPreferences1 = getSharedPreferences("pw_change", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();





        String x = sharedPreferences.getString("pw_setting","");

        if(x.equals("1")){
            lock_setting.setChecked(true);
        }




        lock_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (lock_setting.isChecked() == true) {

                    //잠금화면이 체크될 때
                    locksetting = String.valueOf(1);
                    p = sharedPreferences1.getString("pw_change", "1234");
                    if (p.equals("1234")) {
                        default_pw = "1234";
                        editor1.putString("pw_change", default_pw);
                        editor1.commit();
                    }

                    editor.putString("pw_setting", locksetting);
                    editor.commit();

                    Toast.makeText(LockActivity.this, "잠금화면이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    //잠금화면이 체크되어있지않을 때
                    pw_change_layout.setVisibility(View.INVISIBLE);
                    locksetting = String.valueOf(0);
                    editor.putString("pw_setting", locksetting);
                    editor.commit();
                    Toast.makeText(LockActivity.this, "잠금화면이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current = sharedPreferences.getString("pw_setting","");
                if(current.equals("1")){
                    if (pw_change_layout.getVisibility() == View.VISIBLE) {
                        pw_change_layout.setVisibility(View.INVISIBLE);
                    } else {
                        pw_change_layout.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(LockActivity.this, "잠금을 먼저 설정해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });






        btn_pw_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String m_current_pw = current_pw.getText().toString();
                String x1 = sharedPreferences1.getString("pw_change","");
                if(m_current_pw.equals(x1)){
                    changePW = changepw.getText().toString();
                    editor1.putString("pw_change", changePW);
                    editor1.commit();
                    current_pw.setText("");
                    changepw.setText("");
                    Toast.makeText(LockActivity.this, "패스워드가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    current_pw.setText("");
                    Toast.makeText(LockActivity.this, "기존 패스워드가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
