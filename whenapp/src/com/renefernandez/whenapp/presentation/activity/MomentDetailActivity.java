package com.renefernandez.whenapp.presentation.activity;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.fragment.MomentDetailFragment;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MomentDetailActivity extends ActionBarActivity {

	private MomentDetailFragment detailsFragment;
	private Long momentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting the layout for this activity */
		setContentView(R.layout.moment_detail_activity);

		/** Getting the fragment manager for fragment related operations */
		FragmentManager fragmentManager = getFragmentManager();

		/**
		 * Getting the fragmenttransaction object, which can be used to add,
		 * remove or replace a fragment
		 */
		FragmentTransaction fragmentTransacton = fragmentManager
				.beginTransaction();

		/** Instantiating the fragment CountryDetailsFragment */
		detailsFragment = new MomentDetailFragment();

		/**
		 * Creating a bundle object to pass the data(the clicked item's
		 * position) from the activity to the fragment
		 */
		Bundle b = new Bundle();

		/** Setting the data to the bundle object from the Intent */
		// b.putInt("position", getIntent().getIntExtra("position", 0));

		this.momentId=getIntent().getLongExtra("moment_id", 1);
		
		b.putLong("moment_id", momentId);

		/** Setting the bundle object to the fragment */
		detailsFragment.setArguments(b);

		/** Adding the fragment to the fragment transaction */
		fragmentTransacton.add(R.id.moment_detail_container, detailsFragment);

		/** Making this transaction in effect */
		fragmentTransacton.commit();

	}

	/*
	 * public void displaySelectImageDialog() { AlertDialog.Builder
	 * alertDialogBuilder = new AlertDialog.Builder(this);
	 * 
	 * // set title alertDialogBuilder.setTitle("Select sharing method")
	 * 
	 * .setPositiveButton("Email", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int id) {
	 * Toast.makeText(null, "Email", Toast.LENGTH_SHORT).show(); } })
	 * 
	 * .setNegativeButton("Facebook", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int id) {
	 * Toast.makeText(null, "Facebook", Toast.LENGTH_SHORT).show(); } })
	 * .setNeutralButton("Twitter", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int id) {
	 * Toast.makeText(null, "Twitter", Toast.LENGTH_SHORT).show(); } });
	 * 
	 * // create alert dialog AlertDialog alertDialog =
	 * alertDialogBuilder.create();
	 * 
	 * // show it alertDialog.show(); }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.moment_detail, menu);

		return super.onCreateOptionsMenu(menu);
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
		case R.id.action_share_twitter:

			Toast.makeText(this.getBaseContext(), "Twitter", Toast.LENGTH_SHORT)
					.show();
			return true;
		case R.id.action_share_facebook:

			Toast.makeText(this.getBaseContext(), "Facebook", Toast.LENGTH_SHORT)
					.show();
			return true;

		case R.id.action_share_email:

			Toast.makeText(this.getBaseContext(), "Email", Toast.LENGTH_SHORT)
					.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void shareByEmail(){
		
		String filename = "export.wna";
		
		MomentDao dao = new MomentDao(this);
		
		Moment moment = dao.get(momentId);
		
		// save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(moment);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    
		
	}

}
