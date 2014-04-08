package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.constants.TestData;
import com.renefernandez.whenapp.model.Moment;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MomentDetailFragment extends Fragment {

	private LinearLayout parent_lay;
	private ImageView lay_1;
	private MapFragment mapFragment;

	private int parent_height;
	private int lay_1_height;
	private int lay_2_heigth;

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

		/*
		 * if (lay_2_heigth > lay_1_height && lay_2_heigth > (parent_height /
		 * 2)) {
		 * 
		 * lay_1.setLayoutParams(new LinearLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, 0, 1));
		 * 
		 * lay_2.getView().setLayoutParams(new LinearLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, 0, 1));
		 * 
		 * } else if (lay_2_heigth < (parent_height / 2)) {
		 * lay_1.setLayoutParams(new LinearLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, (parent_height - lay_2_heigth)));
		 * 
		 * }
		 */

		/**
		 * Getting the bundle object passed from MainActivity ( in Landscape
		 * mode ) or from CountryDetailsActivity ( in Portrait Mode )
		 * */
		Bundle b = getArguments();

		moment = TestData.getTestMoments()[b.getInt("position")];

		/** Getting the textview object of the layout to set the details */
		// TextView textTitle = (TextView) v.findViewById(R.id.country_details);
		// TextView textDate = (TextView) v.findViewById(R.id.textViewDate);

		/*MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map_fragment);

		GoogleMap googleMap = mapFragment.getMap();

		if (googleMap != null) {

			googleMap.addMarker(new MarkerOptions()
					.position(moment.getCoordinates()).title(moment.getTitle())
					.draggable(true));

			// Move the camera instantly to hamburg with a zoom of 15.
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					moment.getCoordinates(), 14));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

		}*/

		/**
		 * Getting the clicked item's position and setting corresponding details
		 * in the textview of the detailed fragment
		 */
		
		Log.v("rene", "View Height: "+ v.getHeight() + " width: "+ v.getWidth());
		
		LinearLayout mainLayout=(LinearLayout) v.findViewById(R.id.moment_detail_main);
		
		LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		
		LinearLayout imageLayout= new LinearLayout(v.getContext());
		imageLayout.setGravity(Gravity.CENTER);
		imageLayout.setBackgroundColor(Color.parseColor("#000000"));

		ImageView imageView = new ImageView(v.getContext());
		imageView.setBackgroundResource(R.drawable.ph_350x200);
		imageView.setLayoutParams(lparams);
		imageLayout.addView(imageView);		
		mainLayout.addView(imageLayout);
		
		LinearLayout mapLayout= new LinearLayout(v.getContext());
		//MapView mapView = new MapView(v.getContext());
				
		
		/*GoogleMapOptions options = new GoogleMapOptions();
		options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
	    .compassEnabled(false)
	    .rotateGesturesEnabled(false)
	    .tiltGesturesEnabled(false);         
		MapView mapView = new MapView(v.getContext(), options);
		mapView.setLayoutParams(lparams);
		//linearLayout.addView(mMapView);
		
		mapLayout.addView(mapView);
		mainLayout.addView(mapLayout);*/
		
		LinearLayout titleLayout= new LinearLayout(v.getContext());
		titleLayout.setGravity(Gravity.CENTER);
		titleLayout.setBackgroundColor(Color.parseColor("#ff8800"));
		TextView tv = new TextView(v.getContext());
		tv.setLayoutParams(lparams);
		//tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);		
		tv.setTextColor(Color.WHITE);
		tv.setText(moment.getTitle());
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
		titleLayout.addView(tv);
		
		mainLayout.addView(titleLayout);
		
		LinearLayout dateLayout= new LinearLayout(v.getContext());
		dateLayout.setGravity(Gravity.CENTER);
		TextView dateLabel = new TextView(v.getContext());
		dateLabel.setLayoutParams(lparams);
		
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);
		
		dateLabel.setText(dt1.format(moment.getDate()));
		
		//dateLabel.setText(moment.getDate().toString());
		
		dateLayout.addView(dateLabel);
		mainLayout.addView(dateLayout);
		
		mapFragment = MapFragment.newInstance();
		 FragmentTransaction fragmentTransaction =
		         getFragmentManager().beginTransaction();
		 fragmentTransaction.add(v.getId(), mapFragment);
		 fragmentTransaction.commit();

		// textTitle.setText("Details of " + moment.getTitle());
		// textDate.setText(moment.getDate().toString());

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		GoogleMap googleMap = mapFragment.getMap();
		
		if (googleMap != null) {

			googleMap.addMarker(new MarkerOptions()
					.position(moment.getCoordinates()).title(moment.getTitle())
					.draggable(true));

			// Move the camera instantly to hamburg with a zoom of 15.
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					moment.getCoordinates(), 14));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
		}else{
			Log.e("rene", "GoogleMap is null");
		}
	}

}
