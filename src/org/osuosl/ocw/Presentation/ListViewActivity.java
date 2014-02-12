package org.osuosl.ocw.Presentation;

import java.util.ArrayList;
import java.util.Iterator;

import org.osuosl.ocw.Controller;
import org.osuosl.ocw.R;
import org.osuosl.ocw.BusinessLogic.Speaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends Activity {
	ListView list;

	// constant for identifying the dialog
	private static final int DIALOG_ABOUT = -5;
	private static final int DIALOG_NEW = -6;
	private static final int DIALOG_VIEW = -7;


	ArrayList<String> names = new ArrayList<String>();
	ArrayList<Integer> images = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		Controller con = null;
		Iterator<Speaker> advs = null;

		con = Controller.getSingletonController(getApplicationContext());
		advs = con.getSpeakers().iterator();


		if(advs != null){




			while(advs.hasNext()){
				names.add(advs.next().getFullname());
				images.add(R.drawable.icon);
			}

			ListViewAdapter adapter = new
					ListViewAdapter(ListViewActivity.this, names, images);
			list=(ListView)findViewById(R.id.list);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Toast.makeText(ListViewActivity.this, "You Clicked at " +names.get(position), Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_new:
			showDialog(DIALOG_NEW);			//Show list of new operations
			return true;
		case R.id.action_view:
			showDialog(DIALOG_VIEW);	    //Show list of view operations
			return true;
		case R.id.action_about:
			showDialog(DIALOG_ABOUT);	    //Show about dialog
			return true;

		}

		return false;
	}

	/**
	 * Treats the different dialogs that can be opened
	 * - The about dialog
	 * - The filter tracks dialog
	 * - The local data last updated dialog
	 */
	protected Dialog onCreateDialog(int id){
		if(id == DIALOG_ABOUT){
			Context context = getApplicationContext();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.about, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.about);
			builder.setCancelable(true);
			builder.setView(v);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			final AlertDialog alert = builder.create();
			return alert;
		} else if(id == DIALOG_NEW){
			//			final List<String> strings = new ArrayList<String>();
			//			
			//			strings.add("Advisor");
			//			
			//			final CharSequence[] items = strings.toArray(new String[strings.size()]);
			//
			//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//			builder.setTitle(R.string.picktrack)
			//			.setCancelable(true)
			//			.setItems(items, new DialogInterface.OnClickListener() {
			//				public void onClick(DialogInterface dialog, int which) {
			//					mAdapter.filterTracks(tracks.get(which));
			//					showList();
			//				}
			//			});
			//			builder.setNegativeButton(R.string.removefilter, new DialogInterface.OnClickListener() {
			//				public void onClick(DialogInterface dialog, int id) {
			//					mAdapter.filterDay(mCurrentDate);
			//					mDate.setText(date_formatter.format(mCurrentDate));
			//					showList();
			//				}
			//			});
			//			final AlertDialog alert = builder.create();
			//			return alert;
		} else if(id == DIALOG_VIEW){

			//			Context context = getApplicationContext();
			//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			//			View v = inflater.inflate(R.layout.viewalldialog, null);
			//
			//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//
			//			builder.setCancelable(true);
			//			builder.setView(v);
			//			builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
			//				public void onClick(DialogInterface dialog, int id) {
			//					// User cancelled the dialog
			//				}
			//			});
			//
			//			final AlertDialog alert = builder.create();
			//			return alert;

			//start list view activity



		}

		return null;
	}

}