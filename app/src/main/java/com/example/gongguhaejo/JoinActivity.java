package com.example.gongguhaejo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";
    private EditText etFoodname, etFoodprice;
    private TextView tvRestname, tvFooddeliveryprice, tvReceive;
    private Button btnMatch;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // UI의 EditText에 받아온 데이터를 설정합니다.
        TextView tvRestname = findViewById(R.id.tv_restname);
        EditText etFoodname = findViewById(R.id.et_foodname);
        EditText etFoodprice = findViewById(R.id.et_foodprice);
        TextView tvReceive = findViewById(R.id.tv_receive);
        TextView tvFooddeliveryprice = findViewById(R.id.tv_fooddeliveryprice);
        Button btnMatch = findViewById(R.id.btn_match);

        // Intent에서 데이터를 받아옵니다.
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        // 데이터베이스에서 정보를 읽어오기 위해 DatabaseReference를 생성
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("GongguList").child(key);

        // ValueEventListener를 사용하여 데이터베이스의 변경사항을 읽어옴
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 데이터 스냅샷에서 필요한 정보를 가져와서 화면에 표시하는 로직 작성
                if (dataSnapshot.exists()) {
                    // 데이터 스냅샷에서 필요한 정보를 가져오는 예시
                    String restName = dataSnapshot.child("rest_name").getValue(String.class);
                    //String foodName = dataSnapshot.child("food_name").getValue(String.class);
                    //int foodPrice = dataSnapshot.child("food_price").getValue(Integer.class);
                    int foodDeliveryPrice = dataSnapshot.child("food_deliveryprice").getValue(Integer.class);
                    String receive = dataSnapshot.child("receive").getValue(String.class);

                    // 가져온 정보를 활용하여 화면에 표시하는 로직 작성
                    tvRestname.setText(restName);
//                    etFoodname.setText(foodName);
//                    etFoodprice.setText(String.valueOf(foodPrice));
                    tvReceive.setText(String.valueOf(foodDeliveryPrice));
                    tvFooddeliveryprice.setText(receive);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터 읽기 실패 시 동작할 로직 작성
                Log.e(TAG, "Failed to read data.", databaseError.toException());
            }
        });

        tvRestname.setEnabled(false); // 가게 이름 수정 불가능
        tvReceive.setEnabled(false); // 배달 수령지 수정 불가능
        tvFooddeliveryprice.setEnabled(false); // 배달비 수정 불가능

        // 텍스트 색상을 검정색으로 설정
        tvRestname.setTextColor(Color.BLACK);
        tvReceive.setTextColor(Color.BLACK);
        tvFooddeliveryprice.setTextColor(Color.BLACK);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 뒤로가기 버튼 활성화
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 활성화
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow); // 뒤로가기 버튼 아이콘 설정
        }

        // "btnMatch" 버튼 클릭 시 정보 저장
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applymatch();
            }
        });
    }

    private void applymatch() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            String restName = tvRestname.getText().toString().trim();
            String foodName = etFoodname.getText().toString().trim();
            int foodPrice = Integer.parseInt(etFoodprice.getText().toString().trim());
            int foodDeliveryPrice = Integer.parseInt(tvFooddeliveryprice.getText().toString().trim());
            String receive = tvReceive.getText().toString().trim();

            // GongguList 객체 생성
            GongguList gongguList = new GongguList();
            gongguList.setUserId(userId);
            gongguList.setRest_name(restName);
            gongguList.setFood_name(foodName);
            gongguList.setFood_price(foodPrice);
            gongguList.setFood_deliveryprice(foodDeliveryPrice);
            gongguList.setReceive(receive);

            // Firebase Realtime Database에 저장
            DatabaseReference gongguRef = databaseReference.child("GongguList").push();
            gongguRef.setValue(gongguList);

            // 입력 필드 초기화
            tvRestname.setText("");
            etFoodname.setText("");
            etFoodprice.setText("");
            tvFooddeliveryprice.setText("");
            tvReceive.setText("");

            Toast.makeText(JoinActivity.this, "공구 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
