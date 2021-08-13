package com.app.adoptme.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.app.adoptme.R;

public class Loading {

    public static Dialog dialog;

    public static void showDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
    }

    public static boolean isDialogShowing() {
        return dialog.isShowing();
    }

    public static void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
