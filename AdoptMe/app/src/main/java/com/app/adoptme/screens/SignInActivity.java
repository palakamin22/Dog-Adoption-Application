package com.app.adoptme.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.AdoptMeApp;
import com.app.adoptme.databinding.ActivitySignInBinding;
import com.app.adoptme.firebase.database.UserHelper;
import com.app.adoptme.model.User;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.app.adoptme.utils.Preference.IS_LOGIN;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        //Get login preference
        if (AdoptMeApp.preference.getBoolean(IS_LOGIN)) {
            //Open Home screen
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFrom()) {
                    checkUserAlreadyExist();
                }
            }
        });
    }

    private void checkUserAlreadyExist() {
        Loading.showDialog(SignInActivity.this);

        UserHelper.signInUser(Utility.getText(binding.edtMobileNumber), Utility.getText(binding.edtPassword), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Loading.hideDialog();
                if (task.isSuccessful() && task.getResult().size() > 0) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e("CheckUserAlreadyExist", document.getId() + " => " + document.getData());
                        User user = document.toObject(User.class);
                        user.setId(document.getId());
                        Utility.setUser(user);
                        //set login preference
                        AdoptMeApp.preference.putBoolean(IS_LOGIN, true);
                        //Open Home screen
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Utility.showAlert(SignInActivity.this, "Error", "Wrong mobile number or password",null);
                }
            }
        });
    }

    private boolean validateFrom() {
        boolean isValid = true;
        if (Utility.isEmpty(binding.edtMobileNumber)) {
            Utility.toast("Please enter mobile number");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtPassword)) {
            Utility.toast("Please enter password");
            isValid = false;
        }
        return isValid;
    }
}