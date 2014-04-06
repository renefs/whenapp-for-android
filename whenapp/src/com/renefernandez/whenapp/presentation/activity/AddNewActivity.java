package com.renefernandez.whenapp.presentation.activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.R.id;
import com.renefernandez.whenapp.R.layout;
import com.renefernandez.whenapp.R.menu;
import com.renefernandez.whenapp.presentation.dialog.DatePickerFragment;
import com.renefernandez.whenapp.presentation.dialog.TimePickerFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.provider.MediaStore;

public class AddNewActivity extends ActionBarActivity implements
		LocationListener, OnDateSetListener, OnTimeSetListener {

	// http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html#maps_markers
	static final LatLng EUITIO = new LatLng(43.35560534, -5.850938559);

	private static final int MY_DATE_DIALOG_ID = 3;
	private static int RESULT_LOAD_IMAGE = 1;

	private GoogleMap googleMap;
	private Marker marker;

	private int hour;
	private int minute;

	private EditText textDate;
	private EditText textTime;
	private ImageView imageView;

	private Button loadImageButton;

	private String picturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);

		// SupportMapFragment mapFragment = (SupportMapFragment)
		// getSupportFragmentManager().findFragmentById(R.id.map_fragment);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map_fragment);

		textDate = (EditText) findViewById(R.id.editText2);
		textTime = (EditText) findViewById(R.id.editText3);

		imageView = (ImageView) findViewById(R.id.imgView);

		loadImageButton = (Button) findViewById(R.id.buttonLoadPicture);

		loadImageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		if (mapFragment == null)
			Log.e("AddNewActivity", "MAPFRAGMENT ES NULL");

		googleMap = mapFragment.getMap();

		if (googleMap != null) {

			LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
			boolean enabled = service
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// check if enabled and if not send user to the GSP settings
			// Better solution would be to display a dialog and suggesting to
			// go to the settings
			/*
			 * if (!enabled) { Intent intent = new
			 * Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			 * startActivity(intent); }
			 */

			service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
					this);
			Location localizacion = service
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			LatLng posicion = null;

			if (localizacion != null) {

				posicion = new LatLng(localizacion.getLatitude(),
						localizacion.getLongitude());

			} else {
				posicion = EUITIO;
			}

			marker = googleMap.addMarker(new MarkerOptions().position(posicion)
					.title("Your Location").draggable(true));

			/*
			 * Marker kiel = googleMap.addMarker(new MarkerOptions()
			 * .position(KIEL) .title("Kiel") .snippet("Kiel is cool")
			 * .icon(BitmapDescriptorFactory
			 * .fromResource(R.drawable.ic_launcher)));
			 */

			// Move the camera instantly to hamburg with a zoom of 15.
			googleMap.moveCamera(CameraUpdateFactory
					.newLatLngZoom(posicion, 17));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

		}

		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }
	}

	public void onStart() {

		super.onStart();

		Log.e("AddNewActivity", "OnStart");

		// SupportMapFragment mapFragment = (SupportMapFragment)
		// getSupportFragmentManager().findFragmentByTag("map_add_new");

		/*
		 * if(mapFragment==null) Log.e("AddNewActivity", "MAPFRAGMENT ES NULL");
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_add_new,
	// container, false);
	// return rootView;
	// }
	// }

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Log.v("DTE", "Date selected: " + year + "/" + monthOfYear + "/"
				+ dayOfMonth);
		textDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);

	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Log.v("DTE", "Time selected: " + hourOfDay + "/" + minute);
		textTime.setText(hourOfDay + ":" + minute);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();

			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}

	}

	@Override
	public void onSaveInstanceState(Bundle b){
		b.putString("image", picturePath);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle b) {
		// you need to handle NullPionterException here.
		//Log.v("RESTORE", "PicturePath: " + picturePath);
		if (b.getString("image") != null)
			imageView.setImageBitmap(BitmapFactory.decodeFile(b.getString("image")));
			picturePath = b.getString("image");
	}

}