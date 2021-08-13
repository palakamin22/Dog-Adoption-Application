package com.app.adoptme.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.adoptme.AdoptMeApp;
import com.app.adoptme.R;
import com.app.adoptme.firebase.database.UserHelper;
import com.app.adoptme.model.User;
import com.app.adoptme.utils.Loading;
import com.app.adoptme.utils.Utility;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.app.adoptme.utils.Preference.IS_LOGIN;

public class FirebaseUI extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_u_i);

        createSignInIntent();
    }


    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()

                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                //new AuthUI.IdpConfig.TwitterBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_dog)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }


    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.e("ID", user.getUid());
                Log.e("Name", user.getDisplayName());
                Log.e("Email", user.getEmail());
                User user1 = new User();
                user1.setId(user.getUid());
                user1.setName(user.getDisplayName());
                user1.setEmail(user.getEmail());
                user1.setId(user.getUid());
                //Log.e("Mobile",user.getPhoneNumber());
                checkUserAlreadyExist(user1);
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Utility.showAlert(FirebaseUI.this, "Error", "Error while login or signup", null);
            }
        }
    }
    // [END auth_fui_result]

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_dog)      // Set logo drawable
                        .setTheme(R.style.Theme_AdoptMe)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }

    private void checkUserAlreadyExist(User user1) {
        Loading.showDialog(FirebaseUI.this);

        UserHelper.checkUserExist(user1.getId(), task -> {
            if (task.isSuccessful() && task.getResult().size() > 0) {
                Loading.hideDialog();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.e("CheckUserAlreadyExist", document.getId() + " => " + document.getData());
                    /*User user = document.toObject(User.class);
                    user.setId(document.getId());*/
                    Utility.setUser(user1);
                    //set login preference
                    AdoptMeApp.preference.putBoolean(IS_LOGIN, true);
                    //Open Home screen
                    Intent intent = new Intent(FirebaseUI.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            } else {
                UserHelper.addUser(user1, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Loading.hideDialog();


                        Utility.setUser(user1);
                        //set login preference
                        AdoptMeApp.preference.putBoolean(IS_LOGIN, true);
                        //Open Home screen
                        Intent intent = new Intent(FirebaseUI.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Loading.hideDialog();
                    }
                });
            }
        });
    }


}