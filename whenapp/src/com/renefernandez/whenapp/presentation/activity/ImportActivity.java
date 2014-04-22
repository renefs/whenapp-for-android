package com.renefernandez.whenapp.presentation.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.util.DialogHelper;

/**
 * Activity encargada de procesar la importación de los moments previamente
 * exportados. Los archivos importados deben tener la extensión .wna y ser
 * Moment (heredar de Serializable).
 * 
 * @author rene
 * 
 */
public class ImportActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import);

		Uri u = getIntent().getData();
		String scheme = u.getScheme();
		if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Log.v("rene", "Scheme Content: " + scheme);

			try {
				InputStream input = getContentResolver().openInputStream(u);
				ObjectInputStream in = new ObjectInputStream(input);
				Moment importedMoment = (Moment) in.readObject();

				Log.v("rene", "Moment: " + importedMoment.getTitle());

				Moment newMoment = new Moment(importedMoment.getTitle())
						.withDate(importedMoment.getDate()).withLocation(
								importedMoment.getLatitude(),
								importedMoment.getLongitude());

				MomentDao dao = new MomentDao(this);

				long id = dao.insert(newMoment);

				if (id < 0) {
					DialogHelper.displayAlertDialog("Error",
							"Moment could not be saved.", this);
					Log.e("rene", "Error saving moment in DB: Returned id was "
							+ id);
				} else {
					DialogHelper.displayAlertDialog("Done",
							"Moment was saved properly.", this);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.v("rene", "File URI: " + u.toString());
		}
		System.out.println(u);
	}
}
