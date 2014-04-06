package com.renefernandez.whenapp.presentation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.constants.Constants;
/**
 * http://www.vogella.com/tutorials/AndroidListView/article.html
 * 
 * @author rene
 *
 */
public class MyMapFragment extends SupportMapFragment {

    private List<MarkerOptions> mMarkers;

    public static MyMapFragment create(GoogleMapOptions options, ArrayList<MarkerOptions> markers) {
        MyMapFragment fragment = new MyMapFragment();

        Bundle args = new Bundle();
        args.putParcelable("MapOptions", options); //obtained by decompiling google-play-services.jar
        args.putParcelableArrayList("markers", markers);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hacky and ugly but it works
        ArrayList<Parcelable> list = getArguments().getParcelableArrayList("markers");
        mMarkers = new ArrayList<MarkerOptions>(list.size());
        for (Parcelable parcelable : list) {
            mMarkers.add((MarkerOptions) parcelable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GoogleMap mMap = super.getMap();
        //add the markers
        if (mMap != null) {
            for (MarkerOptions marker : mMarkers) {
                mMap.addMarker(marker);
            }
        }
    }
    
    /**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MyMapFragment newInstance(int sectionNumber) {
		MyMapFragment fragment = new MyMapFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
}