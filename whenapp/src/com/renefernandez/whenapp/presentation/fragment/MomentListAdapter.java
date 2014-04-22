package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;
import com.renefernandez.whenapp.presentation.util.ImageHelper;

/**
 * Adapta las listas de Moment para mostrarlas en una ListView.
 * @author rene
 *
 */
public class MomentListAdapter extends ArrayAdapter<Moment> {

	private final Context context;
	private final Moment[] values;

	private final int IMAGE_WIDTH = 90;
	private final int IMAGE_HEIGHT = 90;

	public MomentListAdapter(Context context, Moment[] values) {
		super(context, R.layout.list_mobile, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_mobile, parent, false);

		// Text
		setTextLabels(position, rowView);

		// Image
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		setItemImage(position, imageView);

		return rowView;
	}

	/**
	 * Establece los valores de las etiquetas de texto
	 * 
	 * @param position
	 *            Posición del array de Moments del que se obtendrá la
	 *            información.
	 * @param rowView
	 *            Vista padre a la que se añadirán las vistas de texto.
	 */
	private void setTextLabels(int position, View rowView) {
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textViewDate = (TextView) rowView
				.findViewById(R.id.textViewDate);

		textView.setText(values[position].getTitle());

		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",
				Locale.ENGLISH);

		textViewDate.setText(dt1.format(values[position].getDate()));
	}

	/**
	 * Establece la imagen para el elemento actual.
	 * 
	 * @param position
	 *            La posición del elemento dentro del array "values".
	 * @param imageView
	 *            Vista en la que se introducirá la imagen.
	 */
	private void setItemImage(int position, ImageView imageView) {
		Drawable img = null;
		// Si no tiene imagen asociada, se asigna una por defecto.
		if (values[position].getImagePath() == null) {
			img = getContext().getResources().getDrawable(
					R.drawable.android_logo);
			img.setBounds(0, 0, IMAGE_HEIGHT, IMAGE_WIDTH);
			imageView.setImageDrawable(img);
		} else {

			Bitmap bitmap = ImageHelper.decodeSampledBitmapFromPath(
					values[position].getImagePath(), IMAGE_WIDTH, IMAGE_HEIGHT);

			/* Associate the Bitmap to the ImageView */
			imageView.setImageBitmap(bitmap);
		}
	}

}
