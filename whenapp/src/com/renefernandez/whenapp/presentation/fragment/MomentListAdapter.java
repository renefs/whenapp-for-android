package com.renefernandez.whenapp.presentation.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.renefernandez.whenapp.R;
import com.renefernandez.whenapp.model.Moment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		if (s.equals("Moment 1")) {
			img = getContext().getResources().getDrawable( R.drawable.windowsmobile_logo );			
		} else if (s.equals("Moment 2")) {
			img = getContext().getResources().getDrawable( R.drawable.ios_logo );			
		} else if (s.equals("Moment 3")) {
			img = getContext().getResources().getDrawable( R.drawable.blackberry_logo );
		} else {			
			img = getContext().getResources().getDrawable( R.drawable.android_logo );			
		}
		img.setBounds( 0, 0, 90, 90 );
		//textView.setCompoundDrawables(img, null, null, null);
		imageView.setImageDrawable(img);
		
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.ENGLISH);
		
		textViewDate.setText(dt1.format(values[position].getDate()));
 
		return rowView;
	}
	
}
