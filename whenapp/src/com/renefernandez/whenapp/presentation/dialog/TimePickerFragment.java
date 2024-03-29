package com.renefernandez.whenapp.presentation.dialog;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Utilizada para obtener horas y minutos dentro de un diálogo
 * @author rene
 *
 */
public class TimePickerFragment extends DialogFragment {

	public TimePickerFragment() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(), (OnTimeSetListener) getActivity(), hour, minute, true);
	}

}
