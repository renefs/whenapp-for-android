package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.constants.Constants;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.activity.AddNewActivity;
import com.renefernandez.whenapp.presentation.activity.MomentDetailActivity;

public class HomeFragment extends Fragment {

	private final int MAX_MOMENTS_TO_DISPLAY = 2;

	private Long moment1Id;
	private Long moment2Id;

	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	View rootView = null;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static HomeFragment newInstance(int sectionNumber) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
				parent.removeView(rootView);
		}

		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		LinearLayout mainLayout = (LinearLayout) rootView
				.findViewById(R.id.home_layout);

		MomentDao dao = new MomentDao(this.getActivity());

		List<Moment> moments = dao.listAll();

		TextView textViewHeader = new TextView(this.getActivity());
		if (moments.size() > 0) {
			textViewHeader.setText(R.string.latest_moments);
		} else {
			textViewHeader.setText(R.string.no_moments);
		}
		textViewHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
		mainLayout.addView(textViewHeader);

		if (moments.size() >= 0) {

			for (int i = 0; (i < moments.size() && i < MAX_MOMENTS_TO_DISPLAY); i++) {

				Moment currentMoment = moments.get(i);

				if (i == 0)
					moment1Id = currentMoment.getId();
				if (i == 1)
					moment2Id = currentMoment.getId();

				LinearLayout momentLayout = new LinearLayout(this.getActivity());
				momentLayout.setOrientation(LinearLayout.HORIZONTAL);
				momentLayout.setFocusable(true);
				momentLayout.setClickable(true);

				momentLayout.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

				ImageView imageView = new ImageView(this.getActivity());
				imageView.setLayoutParams(new LayoutParams(190, 190));

				Drawable img = null;
				if (currentMoment.getImage() == null) {
					img = this.getActivity().getResources()
							.getDrawable(R.drawable.android_logo);

				} else {

					/* Get the size of the ImageView */
					int targetW = 90;
					int targetH = 90;

					/* Get the size of the image */
					BitmapFactory.Options bmOptions = new BitmapFactory.Options();
					bmOptions.inJustDecodeBounds = true;
					// BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
					int photoW = 90;
					int photoH = 90;

					/* Figure out which way needs to be reduced less */
					int scaleFactor = 1;
					if ((targetW > 0) || (targetH > 0)) {
						scaleFactor = Math.min(photoW / targetW, photoH
								/ targetH);
					}

					/* Set bitmap options to scale the image decode target */
					bmOptions.inJustDecodeBounds = false;
					bmOptions.inSampleSize = scaleFactor;
					bmOptions.inPurgeable = true;

					img = new BitmapDrawable(this.getActivity().getResources(),
							BitmapFactory.decodeByteArray(
									currentMoment.getImage(), 0,
									currentMoment.getImage().length, bmOptions));
				}

				img.setBounds(0, 0, 90, 90);
				imageView.setImageDrawable(img);
				momentLayout.addView(imageView);

				LinearLayout textLayout = new LinearLayout(this.getActivity());
				textLayout.setOrientation(LinearLayout.VERTICAL);

				textLayout.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

				TextView textViewTitle = new TextView(this.getActivity());
				textViewTitle.setText(currentMoment.getTitle());
				textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);

				textLayout.addView(textViewTitle);

				TextView textViewDate = new TextView(this.getActivity());

				SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",
						Locale.ENGLISH);

				textViewDate.setText(dt1.format(currentMoment.getDate()));

				textLayout.addView(textViewDate);
				momentLayout.addView(textLayout);

				if (i == 0) {
					momentLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(v.getContext(),
									MomentDetailActivity.class);

							Log.v("rene", "Moment ID: " + moment1Id);

							intent.putExtra("moment_id", moment1Id);

							startActivity(intent);
						}
					});
				}

				if (i == 1) {
					momentLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(v.getContext(),
									MomentDetailActivity.class);

							Log.v("rene", "Moment ID: " + moment2Id);

							intent.putExtra("moment_id", moment2Id);

							startActivity(intent);
						}
					});
				}

				mainLayout.addView(momentLayout);

			}

		}

		Button addNewButton = new Button(this.getActivity());
		addNewButton.setText(R.string.action_new);
		addNewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddNewActivity.class);

				/** Starting the activity by passing the implicit intent */
				startActivity(intent);

			}
		});
		mainLayout.addView(addNewButton);

		return rootView;
	}
}