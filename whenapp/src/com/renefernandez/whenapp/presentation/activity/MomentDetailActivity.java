package com.renefernandez.whenapp.presentation.activity;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.R.id;
import com.renefernandez.whenapp.R.layout;
import com.renefernandez.whenapp.presentation.fragment.CountryDetailFragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
 
public class MomentDetailActivity  extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Setting the layout for this activity */
        setContentView(R.layout.country_details_activity_layout);
 
        /** Getting the fragment manager for fragment related operations */
        FragmentManager fragmentManager = getSupportFragmentManager();
 
        /** Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment */
        FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();
 
        /** Instantiating the fragment CountryDetailsFragment */
        CountryDetailFragment detailsFragment = new CountryDetailFragment();
 
        /** Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
        Bundle b = new Bundle();
 
        /** Setting the data to the bundle object from the Intent*/
        b.putInt("position", getIntent().getIntExtra("position", 0));
 
        /** Setting the bundle object to the fragment */
        detailsFragment.setArguments(b);
 
        /** Adding the fragment to the fragment transaction */
        fragmentTransacton.add(R.id.country_details_fragment_container, detailsFragment);
 
        /** Making this transaction in effect */
        fragmentTransacton.commit();
 
    }
}
