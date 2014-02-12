package org.osuosl.ocw.Presentation;


import java.util.ArrayList;

import org.osuosl.ocw.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class ListViewAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final ArrayList<String> web;
	private final ArrayList<Integer> imageId;
	
	public ListViewAdapter(Activity context,
			ArrayList<String> web, ArrayList<Integer> imageId) {
		super(context, R.layout.listitem, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.listitem, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.firstLine);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		txtTitle.setText(web.get(position));
		imageView.setImageResource(imageId.get(position));
		return rowView;
	}
} 
