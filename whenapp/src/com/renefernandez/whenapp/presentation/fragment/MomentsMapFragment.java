package com.renefernandez.whenapp.presentation.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.constants.Constants;
import com.renefernandez.whenapp.constants.TestData;
import com.renefernandez.whenapp.model.Moment;

public class MomentsMapFragment extends SupportMapFragment {

	private List<MarkerOptions> markers;
	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Log.v("rene", "MomentsMapFragment: Oncreate");

		// ArrayList<Parcelable> list =
		// getArguments().getParcelableArrayList("markers");
		ArrayList<Moment> list = new ArrayList<Moment>(Arrays.asList(TestData
				.getTestMoments()));

		// map = this.getMap();

		markers = new ArrayList<MarkerOptions>(list.size());
		for (Moment moment : list) {
			markers.add(new MarkerOptions().position(
					new LatLng(moment.getLatitude(), moment.getLongitude()))
					.title(moment.getTitle()));
		}

		for (Moment moment : list) {
			markers.add(new MarkerOptions().position(
					new LatLng(moment.getLatitude(), moment.getLongitude()))
					.title(moment.getTitle()));
		}

		/*
		 * for (MarkerOptions marker : markers) { map.addMarker(marker); }
		 */

	}

	@Override
	public void onResume() {
		super.onResume();
		map = super.getMap();
		int currentMarker = 0;
		// add the markers
		if (map != null) {
			for (MarkerOptions marker : markers) {
				map.addMarker(marker);
				currentMarker++;
				if (currentMarker == markers.size()) {
					// Move the camera instantly to hamburg with a zoom of 15.
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							marker.getPosition(), 12));

					// Zoom in, animating the camera.
					map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000,
							null);
				}
			}
		}
	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MomentsMapFragment newInstance(int sectionNumber) {
		MomentsMapFragment fragment = new MomentsMapFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

}
