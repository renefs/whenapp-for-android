package com.renefernandez.whenapp.presentation;

import com.renefernandez.whenapp.constants.Constants;
import com.renefernandez.whenapp.constants.Country;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListFragment extends ListFragment {

	ListFragmentItemClickListener ifaceItemClickListener;

	/** An interface for defining the callback method */
	public interface ListFragmentItemClickListener {
		/**
		 * This method will be invoked when an item in the ListFragment is
		 * clicked
		 */
		void onListFragmentItemClick(int position);
	}

	/**
	 * A callback function, executed when this fragment is attached to an
	 * activity
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			/**
			 * This statement ensures that the hosting activity implements
			 * ListFragmentItemClickListener
			 */
			ifaceItemClickListener = (ListFragmentItemClickListener) activity;
		} catch (Exception e) {
			Toast.makeText(activity.getBaseContext(), "Exception",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		/** Data source for the ListFragment */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				inflater.getContext(), android.R.layout.simple_list_item_1,
				Country.name);

		/** Setting the data source to the ListFragment */
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		/**
		 * Invokes the implementation of the method onListFragmentItemClick in
		 * the hosting activity
		 */
		ifaceItemClickListener.onListFragmentItemClick(position);

	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MyListFragment newInstance(int sectionNumber) {
		MyListFragment fragment = new MyListFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

}
