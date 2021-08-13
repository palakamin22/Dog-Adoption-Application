package com.app.adoptme.firebase.database;

import androidx.annotation.NonNull;

import com.app.adoptme.model.Application;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ApplicationHelper {

    private static final String TAG = "ApplicationHelper";

    private static final String APPLICATION = "application";

    public static CollectionReference getInstance() {
        return FirebaseFirestore.getInstance().collection(APPLICATION);
    }

    public static void addApplication(Application application, OnSuccessListener<DocumentReference> successCallback, OnFailureListener failureCallback) {
        getInstance().add(application)
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

    public static void getApplicationList(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getInstance().whereEqualTo("postUserId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                onCompleteListener.onComplete(task);
            }
        });
    }


}
