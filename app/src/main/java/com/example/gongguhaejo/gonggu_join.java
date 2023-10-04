//package com.example.gongguhaejo;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//public class gonggu_join extends AppCompatActivity {
//
//    Toolbar tb;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gonggu_join);
//
//        tb = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(tb);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
//                // 액티비티 이동
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
