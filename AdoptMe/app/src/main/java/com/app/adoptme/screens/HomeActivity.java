package com.app.adoptme.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.adapter.PostAdapter;
import com.app.adoptme.databinding.ActivityHomeBinding;
import com.app.adoptme.firebase.database.PostHelper;
import com.app.adoptme.model.Post;
import com.app.adoptme.utils.Loading;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;

    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {

        binding.txtCreatePost.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });

        binding.txtApplication.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ViewApplicationActivity.class);
            startActivity(intent);
        });

        binding.imgLogout.setOnClickListener(view -> {
            signOut();
        });
    }


    public void signOut() {
        Loading.showDialog(HomeActivity.this);
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Loading.hideDialog();
                        Intent intent = new Intent(HomeActivity.this, FirebaseUI.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                });
        // [END auth_fui_signout]
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPostList();
    }

    private void getPostList() {
        Loading.showDialog(HomeActivity.this);
        postList = new ArrayList<>();
        PostHelper.getPostList(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Loading.hideDialog();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, document.getId() + " => " + document.getData());

                        Post post = document.toObject(Post.class);
                        post.setId(document.getId());
                        //add item in post list
                        postList.add(post);
                    }

                    Log.e(TAG, "Size List : " + postList.size());


                    postAdapter = new PostAdapter(HomeActivity.this, postList);
                    binding.listPost.setAdapter(postAdapter);
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }
}