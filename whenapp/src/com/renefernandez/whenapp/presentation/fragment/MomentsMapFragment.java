package com.renefernandez.whenapp.presentation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;

/**
 * Clase controladora de la pestaña del fragmento de mapa. Se utiliza para
 * mostrar todos los pines de los momentos según su ubicación obtenida de la
 * base de datos.
 * 
 * @author rene
 * 
 */
public class MomentsMapFragment extends SupportMapFragment {

	private List<MarkerOptions> markers;
	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Log.v("rene", "MomentsMapFragment: Oncreate");

		setMarkersOnMap();

	}

	/**
	 * Obtiene los marcadores de la base de datos y los añade al mapa.
	 */
	private void setMarkersOnMap() {
		MomentDao dao = new MomentDao(this.getActivity());

		List<Moment> list = dao.listAll();

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
	}

	/**
	 * Al girar el dispositivo es necesario redibujar la vista y volver a
	 * establecer los marcadores en sus posiciones.
	 */
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

					map.moveCamera(CameraUpdateFactory.newLatLngZoom(
							marker.getPosition(), 12));

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
		return fragment;
	}

}
