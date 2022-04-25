package com.bphc.cryptopoll.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Progress {

    private static ProgressDialog progressDialog = null;

    public Progress() {
    }

    public static ProgressDialog getProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        return progressDialog;
    }

    public static void showProgress(boolean show, String message) {

        if (show)
            progressDialog.show();
        else
            progressDialog.hide();

        progressDialog.setMessage(message);

    }

    public static void dismissProgress(ProgressDialog progressDialog) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
