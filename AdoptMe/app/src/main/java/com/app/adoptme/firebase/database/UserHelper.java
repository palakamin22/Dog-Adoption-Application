package com.app.adoptme.firebase.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.adoptme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserHelper {

    private static final String TAG = "UserHelper";

    private static final String USER = "user";

    public static CollectionReference getInstance() {
        return FirebaseFirestore.getInstance().collection(USER);
    }

    public static void signInUser(String mobileNumber,String password, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getInstance()
                .whereEqualTo("mobileNumber", mobileNumber)
                .whereEqualTo("password", password).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e(TAG, "QuerySnapshot => " + task);
                        onCompleteListener.onComplete(task);
                    }
                });
    }

    public static void checkUserExist(String id, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getInstance().whereEqualTo("id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.e(TAG, "QuerySnapshot => " + task);
                onCompleteListener.onComplete(task);
            }
        });
    }

    public static void addUser(User user, OnSuccessListener<DocumentReference> successCallback, OnFailureListener failureCallback) {
        getInstance().add(user)
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

    public static void getUser(String mobileNumber, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        getInstance().document(mobileNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Log.e(TAG, "Cached document data: " + document.getData());
                //onCompleteListener.onComplete(task);
            }
        });
    }


}
