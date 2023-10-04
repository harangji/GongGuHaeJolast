package com.example.gongguhaejo;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostDeletionWorker extends Worker{
    public PostDeletionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // 글 삭제 작업을 수행합니다.
        String postKey = getInputData().getString("postKey");

        // Firebase Realtime Database에 연결하고 게시물 삭제 로직을 수행
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("GongguList");// "GongguList" 경로로 설정
        databaseRef.child(postKey).removeValue(); // 해당 게시물 삭제

        return Result.success();
    }
}
