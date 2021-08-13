package com.app.adoptme.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.databinding.ActivityPostDetailsBinding;
import com.app.adoptme.firebase.storage.UploadDogImage;
import com.app.adoptme.model.Post;
import com.squareup.picasso.Picasso;

import static com.app.adoptme.utils.Constant.POST_DATA;

public class PostDetailsActivity extends AppCompatActivity {

    private ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
       Post post = (Post) getIntent().getSerializableExtra(POST_DATA);

        /*binding.txtUserName.setText(post.getUserName());
        binding.txtDescription.setText(post.getDescription());*/
        //Load image icon
        UploadDogImage.getDogImage(post.getImage(), uri -> {
            String imageUrl = uri.toString();
            Picasso.get().load(imageUrl).into(binding.imgDog);
        });

        binding.txtBreed.setText("Breed : "+post.getBreed());
        binding.txtAge.setText("Age : "+post.getAge());
        binding.txtNature.setText("Nature : "+post.getNature());
        binding.txtVaccination.setText("Vaccination status : "+post.getVaccination());
        binding.txtLocation.setText("Location : "+post.getLocation());
        binding.txtName.setText("Name : "+post.getName());
        binding.txtMedicalCondition.setText("Medical condition : "+post.getMedicalCondition());
        binding.txtReason.setText("Reason for giving up for adoption : "+post.getReason());
        binding.txtSeparation.setText("Separation anxiety issues : "+post.getSeparation());
        binding.txtCurrentLiving.setText("Current living situation : "+post.getCurrentLiving());

        binding.txtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetailsActivity.this,ApplicationActivity.class);
                intent.putExtra(POST_DATA,post);
                startActivity(intent);
            }
        });
    }
}