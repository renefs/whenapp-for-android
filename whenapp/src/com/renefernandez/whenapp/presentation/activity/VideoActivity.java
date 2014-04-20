package com.renefernandez.whenapp.presentation.activity;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.R.id;
import com.renefernandez.whenapp.R.layout;
import com.renefernandez.whenapp.R.menu;
import com.renefernandez.whenapp.model.dao.MomentDao;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Build;
import android.provider.MediaStore;

public class VideoActivity extends ActionBarActivity {

	private VideoView video;
	private MediaController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		// create new VideoView instance
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);

		String path=null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    path= extras.getString("path");
		}
		
		Log.v("rene", "Path in video Activity: "+path);
		
		// link uri
		Uri uri = Uri.parse(path);
		videoView.setVideoURI(uri);

		// create and associate a MediaController
		MediaController mediaController = new MediaController(this);
		mediaController.setMediaPlayer(videoView);
		videoView.setMediaController(mediaController);
		// videoView.requestFocus();
		mediaController.show();
		videoView.start();

	}

	private long getVideoIdFromFilePath(String filePath,
			ContentResolver contentResolver) {

		long videoId;
		Log.d("rene", "Loading file " + filePath);

		// This returns us content://media/external/videos/media (or something
		// like that)
		// I pass in "external" because that's the MediaStore's name for the
		// external
		// storage on my device (the other possibility is "internal")
		Uri videosUri = MediaStore.Video.Media.getContentUri("external");

		Log.d("rene", "videosUri = " + videosUri.toString());

		String[] projection = { MediaStore.Video.VideoColumns._ID };

		// TODO This will break if we have no matching item in the MediaStore.
		Cursor cursor = contentResolver.query(videosUri, projection,
				MediaStore.Video.VideoColumns.DATA + " LIKE ?",
				new String[] { filePath }, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(projection[0]);
		videoId = cursor.getLong(columnIndex);

		Log.d("rene", "Video ID is " + videoId);
		cursor.close();
		return videoId;
	}

}
