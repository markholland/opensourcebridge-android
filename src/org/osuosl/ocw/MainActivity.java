package org.osuosl.ocw;

import org.osuosl.ocw.Presentation.ListViewActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	
	
	Controller con;
	// constant for identifying the dialog
	private static final int DIALOG_ABOUT = -5;
	private static final int DIALOG_NEW = -6;
	private static final int DIALOG_VIEW = -7;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//        con = Controller.getSingletonController(getApplicationContext());
//        } catch (DomainException e){
//        	e.printStackTrace();
//        }
        
//        View detail = findViewById(R.id.text);
//        TextView  view = (TextView) detail.findViewById(R.id.text);    		
//        try {
//        	Examples.examples(getApplicationContext(), ea);
//        	Advisor a = con.findAdvisor(ea, "500");
//            view.setText(a.getPersonal_data());
//        } catch (DomainException e) {
//        	e.printStackTrace();
//        }
        
        loadOperation lo = new loadOperation();
        lo.execute();
    
        
    }
    
    /**
	 * Loads data into memory if not present on a background thread, upon completion
	 * load the data into the view
	 * @author markholland
	 *
	 */
	private class loadOperation extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			//Find out what the orientation is and lock it while the background work runs
			int currentOrientation = getResources().getConfiguration().orientation; 
			if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			}
			else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			}
		}
		
        @Override
        protected Integer doInBackground(Void... params) {
               
        	Controller con = Controller.getSingletonController(getApplicationContext());
            con.loadSchedule();
            return 1;
                
        } 
        

        @Override
        protected void onPostExecute(Integer uselessResult) {
        	//set the requested orientation once background work has finished
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        	
        }
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
			
			Intent mainIntent = new Intent(MainActivity.this, ListViewActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
			
			
		}

		return null;
	}
    
}
