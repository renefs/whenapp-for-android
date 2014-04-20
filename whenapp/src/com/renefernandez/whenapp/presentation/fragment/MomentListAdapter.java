package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;

public class MomentListAdapter extends ArrayAdapter<Moment> {

	private final Context context;
	private final Moment[] values;
 
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
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textViewDate = (TextView) rowView.findViewById(R.id.textViewDate);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position].getTitle());
		
		// Change icon based on name
		String s = values[position].getTitle();
 
		System.out.println(s);
		Drawable img =null;
		if(values[position].getImage()==null){			
			img = getContext().getResources().getDrawable( R.drawable.android_logo );	
			
		}else{
			
			/* Get the size of the ImageView */
			int targetW = 90;
			int targetH = 90;

			/* Get the size of the image */
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			//BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			int photoW = 90;
			int photoH = 90;
			
			/* Figure out which way needs to be reduced less */
			int scaleFactor = 1;
			if ((targetW > 0) || (targetH > 0)) {
				scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
			}
			
			/* Set bitmap options to scale the image decode target */
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;
			
			//img= new BitmapDrawable(context.getResources(),BitmapFactory.decodeByteArray(values[position].getImage(), 0, values[position].getImage().length));
			img = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(values[position].getImage(), 0, values[position].getImage().length, bmOptions));
		}		
		
		img.setBounds( 0, 0, 90, 90 );
		imageView.setImageDrawable(img);
		
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);
		
		textViewDate.setText(dt1.format(values[position].getDate()));
 
		return rowView;
	}
	
}
