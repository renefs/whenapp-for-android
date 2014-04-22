package com.renefernandez.whenapp.presentation.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.util.DialogHelper;
import com.renefernandez.whenapp.presentation.util.ImageHelper;

public class MomentDetailActivity extends ActionBarActivity {

	private Long momentId;

	private MapFragment mapFragment;
	private ImageView imageView;
	private TextView textViewTitle;
	private TextView textViewDate;

	private Drawable image;

	private Moment moment;

	private static String MOMENT_ID_ARGUMENT = "moment_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.moment_detail_activity);

		this.momentId = getIntent().getLongExtra(MOMENT_ID_ARGUMENT, 1);

		MomentDao dao = new MomentDao(this);

		Log.v("rene", "moment_id received: " + momentId);

		moment = dao.get(momentId);

		imageView = (ImageView) findViewById(R.id.imageView1);
		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.fragment1);
		textViewTitle = (TextView) findViewById(R.id.textView1);
		textViewDate = (TextView) findViewById(R.id.textView2);

		addWatchVideoButton();

	}

	private void addWatchVideoButton() {
		if (moment.getVideoPath() != null && !moment.getVideoPath().equals("")) {
			LinearLayout mainLayout = (LinearLayout) findViewById(R.id.moment_detail_main);

			Button watchVideo = new Button(this);
			watchVideo.setText(R.string.view_video);
			watchVideo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),
							VideoActivity.class);

					intent.putExtra("path", moment.getVideoPath());
					Log.v("rene", "Path: " + moment.getVideoPath());

					startActivity(intent);

				}
			});
			mainLayout.addView(watchVideo);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.moment_detail, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {

		case R.id.action_settings:
			Toast.makeText(this.getBaseContext(), "Settings",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_share_twitter:

			Toast.makeText(this.getBaseContext(), "Twitter", Toast.LENGTH_SHORT)
					.show();
			share("twitter", getMoment().getImagePath());

			return true;
		case R.id.action_share_facebook:

			Toast.makeText(this.getBaseContext(), "Facebook",
					Toast.LENGTH_SHORT).show();
			share("facebook", getMoment().getImagePath());

			return true;

		case R.id.action_share_email:

			Toast.makeText(this.getBaseContext(), "Email", Toast.LENGTH_SHORT)
					.show();

			shareByEmail();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void shareByEmail() {

		String filename = "export.wna";

		MomentDao dao = new MomentDao(this);

		Moment moment = dao.get(momentId);

		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				filename);

		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(moment);

			out.close();

			Uri u1 = Uri.fromFile(file);

			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_SUBJECT, "Your Whenapp moment");
			email.putExtra(Intent.EXTRA_TEXT,
					"Your moment backup made with Whenapp for Android.");
			email.putExtra(Intent.EXTRA_STREAM, u1);
			email.setType("message/rfc822");

			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	void share(String nameApp, String imagePath) {

		if (!isAppInstalled(nameApp)
				&& !isAppInstalled("com." + nameApp + ".android")
				&& !isAppInstalled("com." + nameApp + ".katana")) {
			DialogHelper.displayAlertDialog("Error", "The selected app "
					+ nameApp + " is not installed", this);
			return;
		}

		try {
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			Intent share = new Intent(android.content.Intent.ACTION_SEND);
			share.setType("image/jpeg");
			List<ResolveInfo> resInfo = getPackageManager()
					.queryIntentActivities(share, 0);
			if (!resInfo.isEmpty()) {
				for (ResolveInfo info : resInfo) {
					Intent targetedShare = new Intent(
							android.content.Intent.ACTION_SEND);
					targetedShare.setType("image/jpeg");
					Log.v("rene",
							"TWITTER: "
									+ (info.activityInfo.packageName
											.toLowerCase(Locale.US)));

					if (info.activityInfo.packageName.toLowerCase(Locale.US)
							.contains(nameApp)
							|| info.activityInfo.name.toLowerCase(Locale.US)
									.contains(nameApp)) {
						targetedShare.putExtra(Intent.EXTRA_SUBJECT,
								getMoment().getTitle());
						targetedShare.putExtra(Intent.EXTRA_TEXT,
								moment.getTitle() + " via Whenapp for Android");

						targetedShare.putExtra(Intent.EXTRA_STREAM,
								Uri.fromFile(new File(moment.getImagePath())));
						targetedShare.setPackage(info.activityInfo.packageName);
						targetedShareIntents.add(targetedShare);
					}
				}
				Intent chooserIntent = Intent.createChooser(
						targetedShareIntents.remove(0), "Select app to share");
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
						targetedShareIntents.toArray(new Parcelable[] {}));
				startActivity(chooserIntent);
			}
		}

		catch (Exception e) {
			Log.v("VM",
					"Exception while sending image on" + nameApp + " "
							+ e.getMessage());
		}
	}

	private Uri getMomentBitmapUri() {
		Bitmap bitmap = Bitmap.createBitmap(getImage().getIntrinsicWidth(),
				getImage().getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		getImage().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		getImage().draw(canvas);

		Uri bmpUri = null;
		try {
			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"share_image_" + System.currentTimeMillis() + ".png");
			file.getParentFile().mkdirs();
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}

	@Override
	public void onResume() {
		super.onResume();

		initMap();
		initImageView();
		initTitleTextView();
		initDateTextView();
	}

	private void initDateTextView() {
		textViewDate.setGravity(Gravity.CENTER);
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",
				Locale.ENGLISH);

		textViewDate.setText(dt1.format(moment.getDate()));
	}

	private void initTitleTextView() {
		textViewTitle.setGravity(Gravity.CENTER);
		textViewTitle.setTextColor(Color.WHITE);
		textViewTitle.setText(moment.getTitle());
		textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
	}

	private void initImageView() {
		// Printing image in view
		image = null;
		if (moment.getImagePath() == null) {
			image = getResources().getDrawable(R.drawable.ph_350x200);
			imageView.setImageDrawable(image);
		} else {

			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			int width = size.x;
			int height = size.y;

			Log.v("rene", "Photow and photo H: " + width + " " + height);

			imageView.setImageBitmap(ImageHelper.decodeSampledBitmapFromPath(
					moment.getImagePath(), width / 2, height / 2));
		}

	}

	private void initMap() {
		GoogleMap googleMap = mapFragment.getMap();

		if (googleMap != null) {

			googleMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(moment.getLatitude(), moment
									.getLongitude())).title(moment.getTitle())
					.draggable(false));

			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					moment.getLatitude(), moment.getLongitude()), 14));

			googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
		} else {
			Log.e("rene", "GoogleMap is null");
		}
	}

	public Moment getMoment() {
		return moment;
	}

	public Drawable getImage() {
		return image;
	}

	

	private boolean isAppInstalled(String packageName) {
		PackageManager pm = getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

}
