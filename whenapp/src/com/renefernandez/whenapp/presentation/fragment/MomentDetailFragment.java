package com.renefernandez.whenapp.presentation.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.constants.TestData;
import com.renefernandez.whenapp.model.Moment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MomentDetailFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * Inflating the layout country_details_fragment_layout to the view
		 * object v
		 */
		View v = inflater.inflate(R.layout.moment_detail_fragment, null);

		/**
		 * Getting the bundle object passed from MainActivity ( in Landscape
		 * mode ) or from CountryDetailsActivity ( in Portrait Mode )
		 * */
		Bundle b = getArguments();

		Moment moment = TestData.getTestMoments()[b.getInt("position")];

		/** Getting the textview object of the layout to set the details */
		TextView textTitle = (TextView) v.findViewById(R.id.country_details);
		TextView textDate = (TextView) v.findViewById(R.id.textViewDate);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
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

		}

		/**
		 * Getting the clicked item's position and setting corresponding details
		 * in the textview of the detailed fragment
		 */

		textTitle.setText("Details of " + moment.getTitle());
		textDate.setText(moment.getDate().toString());

		return v;
	}
}
