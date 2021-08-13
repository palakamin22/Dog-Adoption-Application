package com.app.adoptme.firebase.storage;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadDogImage {

    private static FirebaseStorage getInstance() {
        return FirebaseStorage.getInstance();
    }


    public static void uploadImage(File file, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener) {
        StorageReference storageRef = getInstance().getReference();
        StorageReference imageStorageReference = storageRef.child("images/" + file.getName());

        imageStorageReference.putFile(Uri.fromFile(file)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onSuccessListener.onSuccess(taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onFailureListener.onFailure(e);
            }
        });
    }


    public static void getDogImage(String imageName, OnSuccessListener<Uri> onSuccessListener) {
        StorageReference storageRef = getInstance().getReference();
        StorageReference imageStorageReference = storageRef.child(imageName);
        imageStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                onSuccessListener.onSuccess(uri);
            }
        });
    }

}
