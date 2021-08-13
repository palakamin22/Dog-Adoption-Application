package com.app.adoptme.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.adapter.ApplicationAdapter;
import com.app.adoptme.databinding.ActivityViewApplicationBinding;
import com.app.adoptme.firebase.database.ApplicationHelper;
import com.app.adoptme.model.Application;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewApplicationActivity extends AppCompatActivity {

    private static final String TAG = "ViewApplicationActivity";
    private ActivityViewApplicationBinding binding;
    private List<Application> applicationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewApplicationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        Loading.showDialog(ViewApplicationActivity.this);
        String userId = Utility.getUser().getId();
        Log.e("userId",userId);
        ApplicationHelper.getApplicationList(userId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Loading.hideDialog();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, document.getId() + " => " + document.getData());

                        Application application = document.toObject(Application.class);
                        application.setId(document.getId());
                        //add item in post list
                        applicationList.add(application);
                    }

                    //Log.e(TAG, "Size List : " + postList.size());


                    ApplicationAdapter applicationAdapter = new ApplicationAdapter(ViewApplicationActivity.this, applicationList);
                    binding.listApplication.setAdapter(applicationAdapter);
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}