package com.example.alex.mybooks.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.alex.mybooks.R;

/**
 * Created by v0cn115 on 2015/1/22.
 */
public class AlertMessageHelper {

    private Context _context;

    public AlertMessageHelper(Context context){
        this._context = context;
    }

    public void ShowAlertMessage(String message,String title){
        AlertDialog messageDialog = new AlertDialog.Builder(this._context).create();
        messageDialog.setTitle(title);
        messageDialog.setMessage(message);
        messageDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing when user close dialog
            }
        });
        messageDialog.show();
    }
}
