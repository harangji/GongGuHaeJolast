package com.example.gongguhaejo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFriebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPw, mEtPwck, mEtName, mEtNick, mEtTel, mEtAcc, mEtLoca, mEtCkim; //로그인 입력 필드



    EditText id, pw;
    Button btn_login, btn_forget, btn_signup;
    View.OnClickListener cl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFriebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gghj");

        mEtEmail = findViewById(R.id.et_email);
        mEtPw = findViewById(R.id.et_pw);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_signup = (Button) findViewById(R.id.btn_signup);


        cl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch ( view.getId() ){
                    case R.id.btn_login: // 로그인 요청
                        String strEmail = mEtEmail.getText().toString();
                        String strPw = mEtPw.getText().toString();

                        mFriebaseAuth.signInWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(LoginActivity.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공 !!!
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // 현재 액티비티 파괴
                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인 실패...!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case R.id.btn_signup: //회원가입 화면으로 이동
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        btn_login.setOnClickListener(cl);
        btn_forget.setOnClickListener(cl);
        btn_signup.setOnClickListener(cl);
    }
}