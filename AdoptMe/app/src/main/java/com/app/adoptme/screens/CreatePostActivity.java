package com.app.adoptme.screens;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.databinding.ActivityCreatePostBinding;
import com.app.adoptme.firebase.database.PostHelper;
import com.app.adoptme.firebase.storage.UploadDogImage;
import com.app.adoptme.model.Post;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CreatePostActivity extends AppCompatActivity {

    private ActivityCreatePostBinding binding;
    private File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        binding.layoutSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(CreatePostActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .start();
            }
        });

        binding.btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFrom()){
                    uploadImage();
                }

            }
        });
    }


    private boolean validateFrom() {
        boolean isValid = true;
        if (file == null) {
            Utility.toast("Please select image");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtBreed)) {
            Utility.toast("Please enter breed");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtAge)) {
            Utility.toast("Please enter age");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtNature)) {
            Utility.toast("Please enter nature of dog");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtVaccination)) {
            Utility.toast("Please enter vaccination status");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtLocation)) {
            Utility.toast("Please enter location");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtName)) {
            Utility.toast("Please enter name");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtMedicalCondition)) {
            Utility.toast("Please enter medical condition");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtReason)) {
            Utility.toast("Please enter reason of adoption");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtSeparation)) {
            Utility.toast("Please enter separation anxiety issues");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtCurrentLiving)) {
            Utility.toast("Please enter current living");
            isValid = false;
        }
        return isValid;
    }


    private void uploadImage() {
        Loading.showDialog(CreatePostActivity.this);
        UploadDogImage.uploadImage(file, taskSnapshot -> {
            Loading.hideDialog();
            Log.e("File upload", "Name => " + taskSnapshot.getMetadata().getPath());
            submitPost(taskSnapshot.getMetadata().getPath());
        }, e -> {
            Loading.hideDialog();
            Utility.showAlert(CreatePostActivity.this, "Error", e.getMessage(), null);
        });
    }

    private void submitPost(String fileName) {
        Loading.showDialog(CreatePostActivity.this);
        Post post = new Post();
        post.setUserId(Utility.getUser().getId());
        post.setImage(fileName);
        post.setBreed(Utility.getText(binding.edtBreed));
        post.setAge(Utility.getText(binding.edtAge));
        post.setNature(Utility.getText(binding.edtNature));
        post.setVaccination(Utility.getText(binding.edtVaccination));
        post.setLocation(Utility.getText(binding.edtLocation));
        post.setName(Utility.getText(binding.edtName));
        post.setMedicalCondition(Utility.getText(binding.edtMedicalCondition));
        post.setReason(Utility.getText(binding.edtReason));
        post.setSeparation(Utility.getText(binding.edtSeparation));
        post.setCurrentLiving(Utility.getText(binding.edtCurrentLiving));

        PostHelper.addPost(post, documentReference -> {
            Loading.hideDialog();
            Log.e("File upload", "Success");
            Utility.showAlert(CreatePostActivity.this, "Success", "Post added successfully", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        }, e -> {
            Loading.hideDialog();
            Utility.showAlert(CreatePostActivity.this, "Error", e.getMessage(), null);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            file = ImagePicker.Companion.getFile(data);

            Picasso.get().load(file).into(binding.imgDog);
            binding.imgDog.setVisibility(View.VISIBLE);
            binding.imgPlaceHolder.setVisibility(View.GONE);


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Utility.toast(ImagePicker.Companion.getError(data));
        }
    }
}