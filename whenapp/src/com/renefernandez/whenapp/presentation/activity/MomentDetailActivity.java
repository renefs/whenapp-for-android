package com.renefernandez.whenapp.presentation.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.fragment.MomentDetailFragment;

public class MomentDetailActivity extends ActionBarActivity {

	private MomentDetailFragment detailsFragment;
	private Long momentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.moment_detail_activity);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransacton = fragmentManager
				.beginTransaction();
		detailsFragment = new MomentDetailFragment();

		Bundle b = new Bundle();
		this.momentId = getIntent().getLongExtra("moment_id", 1);

		b.putLong("moment_id", momentId);
		detailsFragment.setArguments(b);

		fragmentTransacton.add(R.id.moment_detail_container, detailsFragment);

		fragmentTransacton.commit();

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
			share("twitter", this.detailsFragment.getMoment().getImage());

			return true;
		case R.id.action_share_facebook:

			Toast.makeText(this.getBaseContext(), "Facebook",
					Toast.LENGTH_SHORT).show();
			share("facebook", this.detailsFragment.getMoment().getImage());

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
			email.putExtra(Intent.EXTRA_SUBJECT, "Your moment");
			email.putExtra(Intent.EXTRA_TEXT, "Yout moment backup");
			email.putExtra(Intent.EXTRA_STREAM, u1);
			email.setType("message/rfc822");

			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	void share(String nameApp, byte[] image) {
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
					if (info.activityInfo.packageName.toLowerCase(Locale.US)
							.contains(nameApp)
							|| info.activityInfo.name.toLowerCase(Locale.US)
									.contains(nameApp)) {
						targetedShare.putExtra(Intent.EXTRA_SUBJECT,
								this.detailsFragment.getMoment().getTitle());
						targetedShare.putExtra(Intent.EXTRA_TEXT,
								"via Whenapp for Android");

						Uri bmpUri = getMomentBitmapUri();

						targetedShare.putExtra(Intent.EXTRA_STREAM, bmpUri);
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
		Bitmap bitmap = Bitmap.createBitmap(this.detailsFragment.getImage()
				.getIntrinsicWidth(), this.detailsFragment.getImage()
				.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		this.detailsFragment.getImage().setBounds(0, 0, canvas.getWidth(),
				canvas.getHeight());
		this.detailsFragment.getImage().draw(canvas);

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

}
