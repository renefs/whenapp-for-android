package com.renefernandez.whenapp.presentation.activity;

import java.io.ByteArrayOutputStream;

import com.renefernandez.whenapp.business.pictures.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
import com.renefernandez.whenapp.business.location.GPSTracker;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.dialog.DatePickerFragment;
import com.renefernandez.whenapp.presentation.dialog.TimePickerFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;
import android.provider.MediaStore;
import android.provider.Settings;

public class AddNewActivity extends ActionBarActivity implements
		OnDateSetListener, OnTimeSetListener {

	//MAP
	// http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html#maps_markers
	static final LatLng EUITIO = new LatLng(43.35560534, -5.850938559);	
	private GoogleMap googleMap;
	private Marker marker;
	private GPSTracker gps;
	
	//MEDIA
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static int ACTION_LOAD_IMAGE = 2;
	private static final int ACTION_TAKE_VIDEO = 3;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	
	private Bitmap mImageBitmap;

	private static final String VIDEO_STORAGE_KEY = "viewvideo";
	private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
	private VideoView mVideoView;
	private Uri mVideoUri;

	private String mCurrentPhotoPath;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	//Fields
	private EditText textTitle;

	private EditText textDate;
	private EditText textTime;
	private ImageView imageView;

	///////////////////////////////////
	///// Methods
	//////////////////////////////////
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map_fragment);

		textTitle = (EditText) findViewById(R.id.editText1);
		textDate = (EditText) findViewById(R.id.editText2);
		textTime = (EditText) findViewById(R.id.editText3);

		imageView = (ImageView) findViewById(R.id.imgView);
		mVideoView = (VideoView) findViewById(R.id.videoView1);


		if (mapFragment == null)
			Log.e("AddNewActivity", "MAPFRAGMENT ES NULL");

		googleMap = mapFragment.getMap();

		if (googleMap != null) {

			gps = new GPSTracker(this);
			if (gps.canGetLocation()) { // gps enabled

				setMarkerInPosition(
						new LatLng(gps.getLatitude(), gps.getLongitude()), true);

			} else {
				showSettingsAlert();
			}

		}
		
		Calendar calendar = Calendar.getInstance(); 
		this.setDateText(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		this.setTimeText(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
	}

	protected void onResume() {
		super.onResume();

		Log.d("rene", "AddNewActivity onResume");
		if (gps != null)
			if (gps.canGetLocation())
				setMarkerInPosition(
						new LatLng(gps.getLatitude(), gps.getLongitude()), true);
	}

	protected void onPause() {
		super.onPause();
		if (gps != null)
			gps.stopUsingGPS();
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
		// Take appropriate action for each action item click
		switch (id) {

		case R.id.action_settings:
			Toast.makeText(this.getBaseContext(), "Settings",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_save:

			Toast.makeText(this.getBaseContext(), "Saving...",
					Toast.LENGTH_SHORT).show();

			this.addNewMoment();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addNewMoment() {

		if(!momentIsValid()){
			return;
		}

		String title = this.textTitle.getText().toString();
		Double latitude = this.marker.getPosition().latitude;
		Double longitude = this.marker.getPosition().longitude;

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy-hh:mm", Locale.US);
		String dateInString = this.textDate.getText().toString() + "-"
				+ this.textTime.getText().toString();

		Date date = null;
		try {

			date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (date == null) {
			this.displayAlertDialog("Error", "The date format was incorrect.");
			return;
		}
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		
		byte[]  img=null;
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
		
		if(bitmap!=null){
			Log.v("rene", "Salvando imagen del moment");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			img=bos.toByteArray();
		}else{
			Log.e("rene", "La imagen es null");
		}
		
		//Insertando

		Moment newMoment = new Moment(title, date, latitude, longitude);
		if(img!=null){
			newMoment.setImage(img);
			Log.v("rene", "Imagen salvada");
		}
		MomentDao dao = new MomentDao(this);

		long id = dao.insert(newMoment);

		if (id < 0) {
			this.displayAlertDialog("Error", "Moment could not be saved.");
			Log.e("rene", "Error saving moment in DB: Returned id was " + id);
			return;
		}

		Intent intent = new Intent(this, MainActivity.class);

		/** Starting the activity by passing the implicit intent */
		startActivity(intent);

		Toast.makeText(this.getBaseContext(), "Moment was added successfully!",
				Toast.LENGTH_SHORT).show();

	}

	private boolean momentIsValid() {
		//Precondiciones
		if (this.textTitle.getText().toString() == null
				|| this.textTitle.getText().toString().equals("")) {
			this.displayAlertDialog("Error", "Moment title cannot be empty");
			return false;
		}
		if (this.textDate.getText().toString() == null
				|| this.textDate.getText().toString().equals("")) {
			this.displayAlertDialog("Error",
					"Yout must set a date for the moment");
			return false;
		}

		if (this.textTime.getText().toString() == null
				|| this.textTime.getText().toString().equals("")) {
			this.displayAlertDialog("Error",
					"Yout must set a time for the moment");
			return false;
		}

		if (this.marker == null) {
			this.displayAlertDialog("Error",
					"Yout must set a location for the moment");
			return false;
		}
		return true;
	}

	private void displayAlertDialog(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public void displaySelectImageDialog(View view) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder
				.setTitle("Select image source")

				.setPositiveButton("Take photo",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Log.v("rene", "displaySelectImageDialog: ACTION_TAKE_PHOTO_B");
								dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
							}
						})

				.setNegativeButton("Select from Media",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

								startActivityForResult(i, ACTION_LOAD_IMAGE);
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

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
		this.setDateText(year, monthOfYear, dayOfMonth);
	}

	private void setDateText(int year, int monthOfYear, int dayOfMonth) {
		String outputYear = String.valueOf(year);
		String outputMonth = String.valueOf(monthOfYear);
		String outputDay = String.valueOf(dayOfMonth);

		if (year < 999)
			outputYear = "0" + outputYear;
		if (monthOfYear < 10)
			outputMonth = "0" + outputMonth;
		if (dayOfMonth < 10)
			outputDay = "0" + outputDay;

		textDate.setText(outputDay + "/" + outputMonth + "/" + outputYear);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Log.v("DTE", "Time selected: " + hourOfDay + "/" + minute);
		this.setTimeText(hourOfDay, minute);
	}
	
	private void setTimeText(int hourOfDay, int minute){
		String outputHour = String.valueOf(hourOfDay);
		String outputMinute = String.valueOf(minute);

		if (hourOfDay < 10)
			outputHour = "0" + outputHour;
		if (minute < 10)
			outputMinute = "0" + outputMinute;

		textTime.setText(outputHour + ":" + outputMinute);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == RESULT_OK) {
				Log.v("rene", "onActivityResult: ACTION_TAKE_PHOTO_B");
				handleBigCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B

//		case ACTION_TAKE_PHOTO_S: {
//			if (resultCode == RESULT_OK) {
//				handleSmallCameraPhoto(data);
//			}
//			break;
//		} // ACTION_TAKE_PHOTO_S

//		case ACTION_TAKE_VIDEO: {
//			if (resultCode == RESULT_OK) {
//				handleCameraVideo(data);
//			}
//			break;
//		} // ACTION_TAKE_VIDEO
		} // switch
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
		imageView.setImageBitmap(mImageBitmap);
		imageView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);
		mVideoView.setVideoURI(mVideoUri);
		mVideoView.setVisibility(
				savedInstanceState.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);

		savedInstanceState.putString("title", this.textTitle.getText().toString());
		savedInstanceState.putString("date", this.textDate.getText().toString());
		savedInstanceState.putString("time", this.textTime.getText().toString());
		savedInstanceState.putDouble("latitude", this.marker.getPosition().latitude);
		savedInstanceState.putDouble("longitude", this.marker.getPosition().longitude);
	}

	@Override
	public void onRestoreInstanceState(Bundle b) {


		if (b.getString("title") != null)
			this.textTitle.setText(b.getString("title"));

		if (b.getString("date") != null)
			this.textDate.setText(b.getString("date"));

		if (b.getString("time") != null)
			this.textTime.setText(b.getString("time"));

		setMarkerInPosition(
				new LatLng(b.getDouble("latitude"), b.getDouble("longitude")),
				true);
		
		b.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		b.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
		b.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
		b.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
		super.onSaveInstanceState(b);

	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.delete);

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						setMarkerInPosition(EUITIO, true);
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	private void setMarkerInPosition(LatLng position, boolean withZoom) {

		if (marker == null) {
			marker = googleMap.addMarker(new MarkerOptions().position(position)
					.title("Your Location").draggable(true));
		} else {
			marker.setPosition(position);
		}

		if (withZoom == true) {

			// Move the camera instantly to hamburg with a zoom of
			// 15.
			googleMap.moveCamera(CameraUpdateFactory
					.newLatLngZoom(position, 17));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

		}

	}
	
	
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.US).format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		
		return f;
	}

	private void setPic() {

		Log.v("rene", "setPic");
		
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = imageView.getWidth();
		int targetH = imageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		imageView.setImageBitmap(bitmap);
		mVideoUri = null;
		imageView.setVisibility(View.VISIBLE);
		mVideoView.setVisibility(View.INVISIBLE);
	}

	private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Log.v("rene", "dispatchTakePictureIntent: ACTION_IMAGE_CAPTURE");
		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}

	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}

	private void handleBigCameraPhoto() {
		Log.v("rene", "handleBigCameraPhoto:"+ mCurrentPhotoPath);
		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			//mCurrentPhotoPath = null;
		}

	}

	private void handleCameraVideo(Intent intent) {
		mVideoUri = intent.getData();
		mVideoView.setVideoURI(mVideoUri);
		mImageBitmap = null;
		mVideoView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.INVISIBLE);
	}

}
