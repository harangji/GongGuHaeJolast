package com.example.gongguhaejo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private ImageView imageView;
    private TextView tv_restname, tv_foodname, tv_foodprice, tv_fooddeliveryprice, tv_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 뒤로가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 활성화
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow); // 뒤로가기 버튼 아이콘 설정
        }

        // UI 요소 초기화
        // imageView = findViewById(R.id.image_view);
        tv_restname = findViewById(R.id.tv_restname);
        tv_foodname = findViewById(R.id.tv_foodname);
        tv_foodprice = findViewById(R.id.tv_foodprice);
        tv_fooddeliveryprice = findViewById(R.id.tv_fooddeliveryprice);
        tv_receive = findViewById(R.id.tv_receive);

        // 인텐트에서 아이템 정보 받아오기
        GongguList item = (GongguList) getIntent().getSerializableExtra("gongguItem");

        // 아이템 정보를 UI에 표시
        if (item != null) {
            // imageView.setImageResource(item.getImageResource()); // 이미지 리소스 설정
            tv_restname.setText(item.getRest_name()); // 식당 이름
            tv_foodname.setText(item.getFood_name()); // 구매 메뉴
            tv_foodprice.setText(String.valueOf(item.getFood_price())); // 구매 메뉴 가격
            tv_fooddeliveryprice.setText(String.valueOf(item.getFood_deliveryprice())); // 배달비
            tv_receive.setText(item.getReceive()); // 수령장소
        } // 나중에 지움


        // 데이터베이스에서 정보를 읽어오기 위해 DatabaseReference를 생성
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("GongguList").child(item.getKey()); // 오류 발생

        // ValueEventListener를 사용하여 데이터베이스의 변경사항을 읽어옴
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 데이터 스냅샷에서 필요한 정보를 가져와서 화면에 표시하는 로직 작성
                if (dataSnapshot.exists()) {
                    // 데이터 스냅샷에서 필요한 정보를 가져오는 예시
                    String restName = dataSnapshot.child("rest_name").getValue(String.class);
                    String foodName = dataSnapshot.child("food_name").getValue(String.class);
                    int foodPrice = dataSnapshot.child("food_price").getValue(Integer.class);
                    int foodDeliveryPrice = dataSnapshot.child("food_deliveryprice").getValue(Integer.class);
                    String receive = dataSnapshot.child("receive").getValue(String.class);

                    // 가져온 정보를 활용하여 화면에 표시하는 로직 작성
                    tv_restname.setText(restName);
                    tv_foodname.setText(foodName);
                    tv_foodprice.setText(String.valueOf(foodPrice));
                    tv_fooddeliveryprice.setText(String.valueOf(foodDeliveryPrice));
                    tv_receive.setText(receive);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 읽기 실패 시 동작할 로직 작성
                Log.e(TAG, "Failed to read data.", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // 액티비티 종료
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}