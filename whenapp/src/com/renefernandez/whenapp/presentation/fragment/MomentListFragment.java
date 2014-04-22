package com.renefernandez.whenapp.presentation.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.activity.MomentDetailActivity;

/**
 * Clase encargada de gestionar la pestaña de listado de Moments de la
 * aplicación.
 * 
 * 
 * @author rene
 * 
 */
public class MomentListFragment extends ListFragment {

	private List<Moment> moments;

	/** An interface for defining the callback method */
	public interface ListFragmentItemClickListener {
		/**
		 * This method will be invoked when an item in the ListFragment is
		 * clicked
		 */
		void onListFragmentItemClick(int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MomentDao dao = new MomentDao(this.getActivity());
		moments = dao.listAll();

		setListAdapter(new MomentListAdapter(this.getActivity(),
				moments.toArray(new Moment[moments.size()])));

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	/**
	 * Al hacer clic en un elemento del listado, se muestran los detalles del evento.
	 * Para ello, se le pasa a la MomentDetailActivity el id del Moment seleccionado.
	 */
	public void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent(this.getActivity(),
				MomentDetailActivity.class);

		Log.v("rene", "Moment ID: " + moments.get(position).getId());

		intent.putExtra("moment_id", moments.get(position).getId());

		startActivity(intent);

	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MomentListFragment newInstance(int sectionNumber) {
		MomentListFragment fragment = new MomentListFragment();
		return fragment;
	}

}
