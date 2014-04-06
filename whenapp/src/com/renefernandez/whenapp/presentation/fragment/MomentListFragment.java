package com.renefernandez.whenapp.presentation.fragment;

import com.renefernandez.whenapp.constants.Constants;
import com.renefernandez.whenapp.constants.TestData;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.presentation.activity.MomentDetailActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MomentListFragment extends ListFragment {

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
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			
//			 //This statement ensures that the hosting activity implements
//			 //ListFragmentItemClickListener
//			 
//			ifaceItemClickListener = (ListFragmentItemClickListener) activity;
//		} catch (Exception e) {
//			Toast.makeText(activity.getBaseContext(), "Exception",
//					Toast.LENGTH_SHORT).show();
//		}
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		/// Data source for the ListFragment
		/*ArrayAdapter<Moment> adapter = new ArrayAdapter<Moment>(
				inflater.getContext(), android.R.layout.simple_list_item_1,
				TestData.getTestMoments());

		// Setting the data source to the ListFragment
		setListAdapter(adapter);*/
		
		setListAdapter(new MomentListAdapter(this.getActivity(), TestData.getTestMoments()));

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		/**
		 * Invokes the implementation of the method onListFragmentItemClick in
		 * the hosting activity
		 */
		//ifaceItemClickListener.onListFragmentItemClick(position);
		
		/** Getting the orientation ( Landscape or Portrait ) of the screen */
        //int orientation = getResources().getConfiguration().orientation;
 
		/** Portrait Mode or Square mode */
		/** Creating an intent object to start the CountryDetailActivity */
		Intent intent = new Intent(this.getActivity(), MomentDetailActivity.class);

		/** Setting data ( the clicked item's position ) to this intent */
		intent.putExtra("position", position);

		/** Starting the activity by passing the implicit intent */
		startActivity(intent);

	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MomentListFragment newInstance(int sectionNumber) {
		MomentListFragment fragment = new MomentListFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

}
