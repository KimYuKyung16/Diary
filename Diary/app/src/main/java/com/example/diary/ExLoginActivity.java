package com.example.diary;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExLoginActivity extends AppCompatActivity {
    EditText login_id;
    EditText login_pw;
    Button btn_login;

    TextView mResigettxt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exlogin_page);

        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);
        btn_login = findViewById(R.id.btn_login);

        mResigettxt = findViewById(R.id.register_t2);

        firebaseAuth =  FirebaseAuth.getInstance();

        //가입 버튼이 눌리면
        mResigettxt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(ExLoginActivity.this,RegisterActivity.class));

            }
        });




        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String mail = login_id.getText().toString().trim();
                String pwd = login_pw.getText().toString().trim();

                if(mail.getBytes().length<=0 || pwd.getBytes().length<=0){
                    Toast.makeText(getApplicationContext(), "입력되지않은 부분이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(mail, pwd)
                            .addOnCompleteListener(ExLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        String uid = firebaseAuth.getUid();

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Users").child(uid).child("name");

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String name = snapshot.getValue().toString();
                                                Intent intent = new Intent(ExLoginActivity.this, ChatActivity.class);
                                                intent.putExtra("id", String.valueOf(name));
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(ExLoginActivity.this, "잘못 입력된 부분이 있습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


    }
}