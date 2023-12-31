package com.example.gongguhaejo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag1 extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GongguList> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private View view;

    FloatingActionButton btn_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);

        btn_add = (FloatingActionButton) view.findViewById(R.id.btn_add); // 공구 등록 버튼

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView); // 아이디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // GongguList 객체를 담을 어레이 리스트 (어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("GongguList"); //DB 테이블 연결
        //Query Thoroughfare = databaseReference.orderByChild("receive").equalTo("정문");
        //Query Thoroughfare = databaseReference.orderByChild("receive").startAt("신구").endAt("신구\uf8ff");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        //Thoroughfare.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 List를 추출해냄
                    GongguList gongguList = snapshot.getValue(GongguList.class); // 만들어뒀던 GongguList 객체에 데이터를 담는다.
                    arrayList.add(gongguList); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    adapter.notifyDataSetChanged(); // 리스트 저장 및 새로 고침
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던 중 에러 발생시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결


        return view;
    }
}
