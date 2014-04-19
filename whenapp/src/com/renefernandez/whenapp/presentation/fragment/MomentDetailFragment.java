package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MomentDetailFragment extends Fragment {

	private MapFragment mapFragment;
	private ImageView imageView;
	private TextView textViewTitle;
	private TextView textViewDate;
	
	private Drawable image;

	private View v;

	private Moment moment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * Inflating the layout country_details_fragment_layout to the view
		 * object v
		 */
		v = inflater.inflate(R.layout.moment_detail_fragment, null);

		/**
		 * Getting the bundle object passed from MainActivity ( in Landscape
		 * mode ) or from CountryDetailsActivity ( in Portrait Mode )
		 * */
		Bundle b = getArguments();

		MomentDao dao = new MomentDao(this.getActivity());

		long momentId = b.getLong("moment_id");

		Log.v("rene", "moment_id received: " + momentId);

		moment = dao.get(momentId);
		
		/** Getting the textview object of the layout to set the details */
		imageView = (ImageView) v.findViewById(R.id.imageView1);
		mapFragment = (MapFragment) this.getActivity().getFragmentManager()
				.findFragmentById(R.id.fragment1);
		textViewTitle = (TextView) v.findViewById(R.id.textView1);
		textViewDate = (TextView) v.findViewById(R.id.textView2);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
		if (moment.getImage() == null)
			image = getResources().getDrawable(R.drawable.ph_350x200);
		else {
			image = new BitmapDrawable(this.getResources(),
					BitmapFactory.decodeByteArray(moment.getImage(), 0,
							moment.getImage().length));
		}
		imageView.setImageDrawable(image);
	}

	private void initMap() {
		GoogleMap googleMap = mapFragment.getMap();

		if (googleMap != null) {

			googleMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(moment.getLatitude(), moment
									.getLongitude())).title(moment.getTitle())
					.draggable(true));

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

	
	
}
