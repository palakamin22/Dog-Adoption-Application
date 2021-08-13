package com.app.adoptme.screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.databinding.ActivitySignUpBinding;
import com.app.adoptme.firebase.database.UserHelper;
import com.app.adoptme.model.User;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;

public class SignUpActivity extends AppCompatActivity {

    //private static final int GOOGLE_SIGN_IN = 100;

    private ActivitySignUpBinding binding;
    //private  GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }


    private void init() {
        binding.txtSignIn.setOnClickListener(view -> {
           finish();
        });


        binding.btnSignUp.setOnClickListener(view -> {

            if (validateFrom()) {
                checkUserAlreadyExist();
            }
        });

    }

    /*private void initGoogleLogin(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }*/

    private void checkUserAlreadyExist() {
        Loading.showDialog(SignUpActivity.this);

        UserHelper.checkUserExist(Utility.getText(binding.edtMobileNumber), task -> {
            if (task.isSuccessful() && task.getResult().size() > 0) {
                Loading.hideDialog();
                Utility.showAlert(SignUpActivity.this, "Error", "User already exist with this mobile number",null);
            } else {
                User user = new User();
                user.setName(Utility.getText(binding.edtName));
                /*user.setMobileNumber(Utility.getText(binding.edtMobileNumber));
                user.setPassword(Utility.getText(binding.edtPassword));*/
                signUpUser(user);
            }
        });
    }

    private void signUpUser(User user) {


        UserHelper.addUser(user, documentReference -> {
            Loading.hideDialog();
            Utility.showAlert(SignUpActivity.this, "Success", "User created successfully", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        }, e -> {
            Loading.hideDialog();
            Utility.showAlert(SignUpActivity.this, "Error", "User sign up fail",null);
        });
    }

    private boolean validateFrom() {
        boolean isValid = true;
        if (Utility.isEmpty(binding.edtName)) {
            Utility.toast("Please enter name");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtMobileNumber)) {
            Utility.toast("Please enter mobile number");
            isValid = false;
        } else if (Utility.isEmpty(binding.edtPassword)) {
            Utility.toast("Please enter password");
            isValid = false;
        }
        return isValid;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e( "Id" , account.getId());
                Log.e( "Name" , account.getDisplayName());
                Log.e( "Photo" , account.getPhotoUrl().toString());
                Log.e( "Token" , account.getIdToken());


                User user = new User();
                user.setLoginId(account.getIdToken());
                user.setName(account.getDisplayName());
                user.setProfilePicture(account.getPhotoUrl().toString());
                signUpUser(user);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Utility.showAlert(SignUpActivity.this,"Error","Google sign in failed",null);
            }
        }
    }*/

}