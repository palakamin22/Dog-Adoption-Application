package com.app.adoptme.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.app.adoptme.AdoptMeApp;
import com.app.adoptme.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import static com.app.adoptme.utils.Preference.USER;


public class Utility {

    public static void toast(String value) {
        Toast.makeText(AdoptMeApp.context, value, Toast.LENGTH_LONG).show();
    }

    public static void log(String key, String value) {
        Log.e(key, value);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AdoptMeApp.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void dialogNoInternet(Activity activity) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity)
                .setTitle("No Internet")
                .setMessage("Please check your internet connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }

    public static void showAlert(Activity activity, String title, String message, DialogInterface.OnClickListener onClickListener) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", onClickListener);
        dialog.show();
    }

    public static String getText(EditText editText) {
        return editText.getText().toString();
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public static void setUser(User user) {
        String userData = new Gson().toJson(user);
        AdoptMeApp.preference.putString(USER, userData);
    }

    public static User getUser() {
        String value = AdoptMeApp.preference.getString(USER);
        User user = new Gson().fromJson(value, User.class);
        return user;
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


}
