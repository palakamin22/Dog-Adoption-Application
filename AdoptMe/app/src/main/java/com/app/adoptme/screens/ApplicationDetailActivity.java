package com.app.adoptme.screens;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.databinding.ActivityApplicationDetailBinding;
import com.app.adoptme.firebase.storage.UploadDogImage;
import com.app.adoptme.model.Application;
import com.squareup.picasso.Picasso;

import static com.app.adoptme.utils.Constant.APPLICATION_DATA;

public class ApplicationDetailActivity extends AppCompatActivity {

    private ActivityApplicationDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityApplicationDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        Application application = (Application) getIntent().getSerializableExtra(APPLICATION_DATA);



        UploadDogImage.getDogImage(application.getPostImage(), uri -> {
            String imageUrl = uri.toString();
            Picasso.get().load(imageUrl).into(binding.imgDog);
        });
        binding.txtDogName.setText(application.getPostName());
        binding.txtUserName.setText("Name : "+application.getUserName());
        binding.txtUserEmail.setText("Email : "+application.getUserEmail());

        binding.ans1.setText("Answer : "+application.getAns1());
        binding.ans2.setText("Answer : "+application.getAns2());
        binding.ans3.setText("Answer : "+application.getAns3());
        binding.ans4.setText("Answer : "+application.getAns4());
        binding.ans5.setText("Answer : "+application.getAns5());
        binding.ans6.setText("Answer : "+application.getAns6());
        binding.ans7.setText("Answer : "+application.getAns7());
        binding.ans8.setText("Answer : "+application.getAns8());
        binding.ans9.setText("Answer : "+application.getAns9());
        binding.ans10.setText("Answer : "+application.getAns10());
        binding.ans11.setText("Answer : "+application.getAns11());
        binding.ans12.setText("Answer : "+application.getAns12());
        binding.ans13.setText("Answer : "+application.getAns13());
        binding.ans14.setText("Answer : "+application.getAns14());
        binding.ans15.setText("Answer : "+application.getAns15());
        binding.ans16.setText("Answer : "+application.getAns16());
        binding.ans17.setText("Answer : "+application.getAns17());
        binding.ans18.setText("Answer : "+application.getAns18());
        binding.ans19.setText("Answer : "+application.getAns19());
        binding.ans20.setText("Answer : "+application.getAns20());
        binding.ans21.setText("Answer : "+application.getAns21());
        binding.ans22.setText("Answer : "+application.getAns22());
        binding.ans23.setText("Answer : "+application.getAns23());
    }


}