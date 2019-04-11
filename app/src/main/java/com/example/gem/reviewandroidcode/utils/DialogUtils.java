package com.example.gem.reviewandroidcode.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.gem.reviewandroidcode.R;

/**
 * Created by maboy on 11/4/19.
 */

public class DialogUtils {
  private static Dialog sDialog;

  public synchronized static void showLoadingDialog(Context context) {
    if (sDialog == null) {
      sDialog = new Dialog(context);
      sDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
      sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      sDialog.setContentView(R.layout.dialog_loading);
      sDialog.setCancelable(true);
    }

    if (!sDialog.isShowing()) {
      sDialog.show();
    }
  }

  public synchronized static void showLoadingDialog(Context context, DialogInterface.OnCancelListener onCancelListener) {
    if (sDialog == null) {
      sDialog = new Dialog(context);
      sDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
      sDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      sDialog.setContentView(R.layout.dialog_loading);
      sDialog.setCancelable(true);
      sDialog.setOnCancelListener(onCancelListener);
    }

    if (!sDialog.isShowing()) {
      sDialog.show();
    }
  }

  public static Dialog createLoadingDialog(Context context) {
    Dialog dialog = new Dialog(context);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setContentView(R.layout.dialog_loading);
    dialog.setCancelable(false);
    return dialog;
  }


  public synchronized static void dismissLoadingDialog() {
    if (sDialog != null && sDialog.isShowing()) {
      sDialog.dismiss();
      sDialog = null;
    }
  }

  public static Dialog createWarningDialog(Context context, String message,
                                           DialogInterface.OnClickListener onButtonOkClicked,
                                           DialogInterface.OnClickListener onButtonCancelClicked) {
    return new AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Ok", onButtonOkClicked)
            .setNegativeButton("Cancel", onButtonCancelClicked)
            .create();
  }

  public static Dialog createErrorDialog(Context context, String message,
                                         DialogInterface.OnClickListener onButtonOkClicked) {
    return new AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Ok", onButtonOkClicked)
            .create();
  }
}
