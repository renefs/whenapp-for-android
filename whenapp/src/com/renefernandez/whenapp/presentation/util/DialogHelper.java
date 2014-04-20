package com.renefernandez.whenapp.presentation.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHelper {

	public static void displayAlertDialog(String title, String message, Context context) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set title
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message).setNegativeButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
		
		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}
	
}
