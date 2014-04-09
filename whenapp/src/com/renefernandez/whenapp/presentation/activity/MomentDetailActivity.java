package com.renefernandez.whenapp.presentation.activity;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.presentation.fragment.MomentDetailFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
 
public class MomentDetailActivity  extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Setting the layout for this activity */
        setContentView(R.layout.moment_detail_activity);
 
        /** Getting the fragment manager for fragment related operations */
        FragmentManager fragmentManager = getFragmentManager();
 
        /** Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment */
        FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
 
        /** Instantiating the fragment CountryDetailsFragment */
        MomentDetailFragment detailsFragment = new MomentDetailFragment();
 
        /** Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
        Bundle b = new Bundle();
 
        /** Setting the data to the bundle object from the Intent*/
        //b.putInt("position", getIntent().getIntExtra("position", 0));
        
        b.putLong("moment_id", getIntent().getLongExtra("moment_id", 1));
 
        /** Setting the bundle object to the fragment */
        detailsFragment.setArguments(b);
 
        /** Adding the fragment to the fragment transaction */
        fragmentTransacton.add(R.id.moment_detail_container, detailsFragment);
 
        /** Making this transaction in effect */
        fragmentTransacton.commit();
 
    }
}
