package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
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
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.model.dao.MomentDao;
import com.renefernandez.whenapp.presentation.activity.AddNewActivity;
import com.renefernandez.whenapp.presentation.activity.MomentDetailActivity;
import com.renefernandez.whenapp.presentation.util.ImageHelper;

/**
 * Controlador de la vista principal de la aplicación.
 * 
 * @author rene
 * 
 */
public class HomeFragment extends Fragment {

	private final int MAX_MOMENTS_TO_DISPLAY = 2;

	private Long moment1Id;
	private Long moment2Id;

	private final int IMAGE_WIDTH = 190;
	private final int IMAGE_HEIGHT = 190;
	private final int HEADER_SIZE = 25;

	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	View rootView = null;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static HomeFragment newInstance(int sectionNumber) {
		HomeFragment fragment = new HomeFragment();
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

		//Se obtienen los momentos de la base de datos.
		MomentDao dao = new MomentDao(this.getActivity());
		List<Moment> moments = dao.listAll();

		setHeaderLabel(mainLayout, moments);

		//Se muestran los momentos que correspondan
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

				setImageViewForMoment(currentMoment, momentLayout);

				setTextLabelsForMoment(currentMoment, momentLayout);

				if (i == 0) {
					setListenerToMomentView(momentLayout, moment1Id);
				}

				if (i == 1) {

					setListenerToMomentView(momentLayout, moment2Id);

				}

				mainLayout.addView(momentLayout);

			}

		}

		addAddNewButtonToLayout(mainLayout);

		return rootView;
	}

	private void setHeaderLabel(LinearLayout mainLayout, List<Moment> moments) {
		TextView textViewHeader = new TextView(this.getActivity());
		if (moments.size() > 0) {
			textViewHeader.setText(R.string.latest_moments);
		} else {
			textViewHeader.setText(R.string.no_moments);
		}
		textViewHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, HEADER_SIZE);
		mainLayout.addView(textViewHeader);
	}

	/**
	 * Dibuja el botón para añadir nuevos Moment.
	 * @param mainLayout Layout sobre el que se dibujará.
	 */
	private void addAddNewButtonToLayout(LinearLayout mainLayout) {
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
	}

	/**
	 * Establece la ImageView con la imagen del Moment que corresponda
	 * 
	 * @param currentMoment
	 * @param momentLayout Layout sobre el que se dibujará la imageview.
	 */
	private void setImageViewForMoment(Moment currentMoment,
			LinearLayout momentLayout) {
		ImageView imageView = new ImageView(this.getActivity());
		imageView.setLayoutParams(new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT));

		Drawable img = null;
		if (currentMoment.getImagePath() == null) {
			img = this.getActivity().getResources()
					.getDrawable(R.drawable.android_logo);
			img.setBounds(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
			imageView.setImageDrawable(img);
		} else {

			int width = IMAGE_WIDTH;
			int height = IMAGE_HEIGHT;

			Log.v("rene", "Photow and photo H: " + width + " " + height);

			imageView.setImageBitmap(ImageHelper.decodeSampledBitmapFromPath(
					currentMoment.getImagePath(), width, height));
		}

		momentLayout.addView(imageView);
	}

	/**
	 * Establece las etiquetas del momento en función de los datos del mismo.
	 * 
	 * @param currentMoment Momento actual
	 * @param momentLayout Layout sobre el que se añadirán las etiquetas.
	 */
	private void setTextLabelsForMoment(Moment currentMoment,
			LinearLayout momentLayout) {
		LinearLayout textLayout = new LinearLayout(this.getActivity());
		textLayout.setOrientation(LinearLayout.VERTICAL);

		textLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

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
	}

	/**
	 * Establece los listeners para los distintos momentos según su id.
	 * 
	 * @param momentLayout
	 *            Layout al que se le aplicará el listener
	 * @param momentId
	 *            Id del momento que se pasará al Intent del Moment.
	 */
	private void setListenerToMomentView(LinearLayout momentLayout,
			final Long momentId) {
		momentLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),
						MomentDetailActivity.class);

				Log.v("rene", "Moment ID: " + momentId);

				intent.putExtra("moment_id", momentId);

				startActivity(intent);
			}
		});
	}
}