package com.renefernandez.whenapp.presentation.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.renefernandez.whenapp.R;

/**
 * Clase encargada de controlar la vista de vídeo. Reproduce un vídeo asociado a
 * un Moment.
 * 
 * @author rene
 * 
 */
public class VideoActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);

		// create new VideoView instance
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);

		String path = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			path = extras.getString("path");
		}

		Log.v("rene", "Path in video Activity: " + path);

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

}
