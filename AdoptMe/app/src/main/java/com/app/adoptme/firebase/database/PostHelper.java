package com.app.adoptme.firebase.database;

import androidx.annotation.NonNull;

import com.app.adoptme.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PostHelper {

    private static final String TAG = "PostHelper";

    private static final String POST = "post";

    public static CollectionReference getInstance() {
        return FirebaseFirestore.getInstance().collection(POST);
    }

    public static void addPost(Post post, OnSuccessListener<DocumentReference> successCallback, OnFailureListener failureCallback) {
        getInstance().add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        successCallback.onSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureCallback.onFailure(e);
                    }
                });
    }

    public static void getPostList(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getInstance().orderBy("time", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                onCompleteListener.onComplete(task);
            }
        });
    }


}
