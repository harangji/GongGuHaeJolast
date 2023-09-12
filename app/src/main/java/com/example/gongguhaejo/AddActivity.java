package com.example.gongguhaejo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayAdapter<CharSequence> adapter;
    private EditText et_restname;
    private EditText et_foodname;
    private EditText et_foodprice;
    private TextView et_receive;
    private Spinner sp_foodcate, sp_person, sp_time;
    private EditText et_food_deliveryprice;
    private Button btn_save;
    private Toolbar tb;
    private ImageView img_food;
    private DatabaseReference databaseReference;
    private List<GongguList> gongguListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tb = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        gongguListItems = new ArrayList<>();

        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.food_categories,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerPerson = findViewById(R.id.sp_person);
        ArrayAdapter<CharSequence> personAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.item1_name,
                android.R.layout.simple_spinner_dropdown_item
        );
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerson.setAdapter(personAdapter);

        Spinner spinnerTime = findViewById(R.id.sp_time);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.item2_name,
                android.R.layout.simple_spinner_dropdown_item
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);

        Spinner spinnerFoodCate = findViewById(R.id.sp_foodcate);
        spinnerFoodCate.setAdapter(adapter);

        et_restname = findViewById(R.id.et_restname);
        et_foodname = findViewById(R.id.et_foodname);
        et_foodprice = findViewById(R.id.et_foodprice);
        et_receive = findViewById(R.id.et_receive);
        sp_foodcate = findViewById(R.id.sp_foodcate);
        sp_person = findViewById(R.id.sp_person);
        sp_time = findViewById(R.id.sp_time);
        et_food_deliveryprice = findViewById(R.id.et_fooddeliveryprice);
        btn_save = findViewById(R.id.btn_save);

        sp_foodcate.setAdapter(adapter);
        sp_person.setAdapter(personAdapter);
        sp_time.setAdapter(timeAdapter);

        et_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, com.example.gongguhaejo.googlemap.googlemap_addAct.class);
                startActivityForResult(intent,0);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGongguList();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ) {
            String loca = data.getStringExtra("loca");
            et_receive.setText(loca);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // 현재 액티비티 종료
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveGongguList() {
        String restName = et_restname.getText().toString().trim();
        String foodName = et_foodname.getText().toString().trim();
        String receive = et_receive.getText().toString().trim();
        String foodCate = sp_foodcate.getSelectedItem().toString().trim();
        String recruPersonString = sp_person.getSelectedItem().toString().trim();
        String recruTimeString = sp_time.getSelectedItem().toString().trim();
        int foodPrice = Integer.parseInt(et_foodprice.getText().toString().trim());
        int recruPerson = Integer.parseInt(recruPersonString.replaceAll("[^0-9]", ""));
        int recruTime = Integer.parseInt(recruTimeString.replaceAll("[^0-9]", ""));
        int deliveryPrice = Integer.parseInt(et_food_deliveryprice.getText().toString().trim());

        GongguList gongguList = new GongguList();
        gongguList.setRest_name(restName);
        gongguList.setFood_name(foodName);
        gongguList.setFood_cate(foodCate);
        gongguList.setFood_price(foodPrice);
        gongguList.setRecru_person(recruPerson);
        gongguList.setRecru_time(recruTime);
        gongguList.setReceive(receive);
        gongguList.setFood_deliveryprice(deliveryPrice);

        String key = databaseReference.child("GongguList").push().getKey();
        databaseReference.child("GongguList").child(key).setValue(gongguList);

        gongguListItems.add(gongguList);


        et_restname.setText("");
        et_foodname.setText("");
        et_foodprice.setText("");
        et_receive.setText("");
        et_food_deliveryprice.setText("");

        // 메인 액티비티로 돌아감
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}