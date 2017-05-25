package io.epiclabs.walldroid.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

/**
 * Created by adrian on 14/05/17.
 */

public class Utils {
    public static void showAlertDialog(final AppCompatActivity activity, String errorMssg, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        errorMssg = errorMssg.trim();
        if (errorMssg != null && errorMssg != "") {
            message += "<p>" + errorMssg + "</p>";

        }
        alertDialog.setMessage(Html.fromHtml(message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                });
        alertDialog.show();
    }
}
