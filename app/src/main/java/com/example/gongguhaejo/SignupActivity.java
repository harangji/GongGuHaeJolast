package com.example.gongguhaejo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gongguhaejo.googlemap.googlemap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    Toolbar tb;
    private FirebaseAuth mFriebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail, mEtPw, mEtPwck, mEtName, mEtNick, mEtTel, mEtAcc, mEtLoca, mEtCkim; //회원 가입 입력 필드
    private Button BtnSignup,mBtnLoca, mBtnCkimage, btn_back; // 위치입력버튼, 회원가입 버튼
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //btn_back = findViewById(R.id.btn_back);
        mFriebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gghj");

        tb = findViewById(R.id.toolbar);
        mEtEmail = findViewById(R.id.et_email);
        mEtPw = findViewById(R.id.et_pw);
        mEtPwck = findViewById(R.id.et_pwck);
        mEtName = findViewById(R.id.et_name);
        mEtNick = findViewById(R.id.et_nick);
        mEtTel = findViewById(R.id.et_tel);
        mEtAcc = findViewById(R.id.et_acc);
        mEtLoca = findViewById(R.id.et_loca);
        mEtCkim = findViewById(R.id.et_ckimage);

        mBtnLoca = findViewById(R.id.btn_loca);
        mBtnCkimage = findViewById(R.id.btn_ckimage);
        BtnSignup = findViewById(R.id.btn_signup);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        mBtnLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, com.example.gongguhaejo.googlemap.googlemap.class);
                startActivityForResult(intent,0);
            }
        });

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPw = mEtPw.getText().toString();
                //String strPwck = mEtPwck.getText().toString();
                String strName = mEtName.getText().toString();
                String strNick = mEtNick.getText().toString();
                String strTel = mEtTel.getText().toString();
                String strAcc = mEtAcc.getText().toString();
                String strLoca = mEtLoca.getText().toString();

                //Firebase Auth 진행
                mFriebaseAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(SignupActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // 가입성공 했을 시
                            FirebaseUser firebaseUser = mFriebaseAuth.getCurrentUser(); // firebaseUser란 객체를 만들어 회원가입이 된 유저정보를 가지고 옴
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPw);
                            account.setName(strName);
                            account.setNick(strNick);
                            account.setTel(strTel);
                            account.setAcc(strAcc);
                            account.setLoca(strLoca);

                            // setValue : database에 insert (삽입) 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // 회원가입 액티비티 종료

                            Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ) {
            String loca = data.getStringExtra("loca");
            mEtLoca.setText(loca);
        }
    }
}