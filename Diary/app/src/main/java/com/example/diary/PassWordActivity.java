package com.example.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PassWordActivity extends AppCompatActivity {

    Button btn[]=new Button[10];
    Button cancel, check;
    EditText password;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_page);



        btn[0] = (Button) findViewById(R.id.num1);
        btn[1] = (Button) findViewById(R.id.num2);
        btn[2] = (Button) findViewById(R.id.num3);
        btn[3] = (Button) findViewById(R.id.num4);
        btn[4] = (Button) findViewById(R.id.num5);
        btn[5] = (Button) findViewById(R.id.num6);
        btn[6] = (Button) findViewById(R.id.num7);
        btn[7] = (Button) findViewById(R.id.num8);
        btn[8] = (Button) findViewById(R.id.num9);
        btn[9] = (Button) findViewById(R.id.num0);

        cancel = (Button) findViewById(R.id.cancel);
        check = (Button) findViewById(R.id.check);

        password = (EditText) findViewById(R.id.password);


        initListener();


        SharedPreferences sharedPreferences = getSharedPreferences("pw_change", MODE_PRIVATE);;
        String x = sharedPreferences.getString("pw_change","");


        check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                text = password.getText().toString();

                if(text.equals(x)){
                    Intent intent = new Intent(PassWordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(PassWordActivity.this, "틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void initListener(){
        for(int i=0;i<10;i++){
            btn[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Button btn = (Button) v;
                    password.append(btn.getText().toString());
                }
            });
        }
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                password.setText("");
            }
        });
    }

}
