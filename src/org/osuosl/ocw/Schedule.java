package org.osuosl.ocw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Schedule extends Activity {

	//static DataBaseHandler db; //Single object for handling database operations.
	
	private StateChangeData data = null;
	
	SimpleDateFormat httpHeaderFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	
	// Stores in memory the days that the conference has events
	private ArrayList<Date> DAYS;
	
	private ICal calendar;

	//Global Toast object in order to allow it be displayed and cancelled when needed
	private Toast toast;
	
	// Indexes for Options menu items
	private static final int MENU_NEXT = -5;
	private static final int MENU_PREV = -6;
	private static final int MENU_ABOUT = -7;
	private static final int MENU_NOW = -8;
	private static final int MENU_REFRESH = -9;
	
	// Names of timeout fields in JSON files
	private static final String SCHEDULE_TIMEOUT = "schedule_timeout";
	private static final String SPEAKERS_TIMEOUT = "speakers_timeout";
	private static final String TRACKS_TIMEOUT = "tracks_timeout";
	
	// Names of the fields in SharedPreferences that store when database tables were last updated
	private static final String SCHEDULE_UPDATED = "schedule_updated";
	private static final String SPEAKERS_UPDATED = "speakers_updated";
	private static final String TRACKS_UPDATED = "tracks_updated";
	
	
	// state
	Date mCurrentDate;
	TextView mDate;
	boolean mDetail = false;  // Is the event description view currently visible. Used when orientation switches are performed to allow the same view to be visible
	private Handler mHandler; 
	
	// session list
	EventAdapter mAdapter;
	ListView eventsListView;
	
	// screen animation
	ViewFlipper mFlipper;
	Animation mInLeft;
    Animation mInRight;
    Animation mOutLeft;
    Animation mOutRight;
	
    // session details
    Event mEvent = null; // Used to manipulate the selected event in the list
    HashMap<Integer, Speaker> mSpeakers;  //Stores the conference speakers in memory
    HashMap<Integer, Track> mTracks;      //Stores the tracks in memory
    
    View mHeader;
    TextView mTitle;
    TextView mTime;
    TextView mRoom_title;
    View mTimeLocation;
    TextView mSpeaker;
    ScrollView mDescriptionScroller;
    TextView mDescription;
    LinearLayout mBio;
    
    // session detail actions
    Button mShare;
    Button mShowDescription;
    Button mShowBio;
    
    // URLs for the JSON files
    private static final String SCHEDULE_URI = "http://www.partiallogic.com/gsoc2013/schedule.json";
    private static final String TRACKS_URI = "http://www.partiallogic.com/gsoc2013/tracks.json";
    private static final String SPEAKER_URI = "http://www.partiallogic.com/gsoc2013/speakers.json";
    
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        mHandler = new Handler();
        
        toast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);  // Instantiate the toast in order to change its text and show when needed.
        
        mFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mDate = (TextView) findViewById(R.id.date);
        eventsListView = (ListView) findViewById(R.id.events);
        
        Context context = getApplicationContext();
        mInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        mInRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        mOutLeft = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        mOutRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        
        // grab views for details
        View detail = findViewById(R.id.detail);
        mHeader = findViewById(R.id.detail_header);
        mSpeaker = (TextView) findViewById(R.id.speaker);
        mTitle = (TextView) detail.findViewById(R.id.title);
        mTimeLocation = detail.findViewById(R.id.time_location);
        mTime = (TextView) detail.findViewById(R.id.time);
        mRoom_title = (TextView) detail.findViewById(R.id.room_title);
        mDescription = (TextView) detail.findViewById(R.id.description);
        mDescriptionScroller = (ScrollView) detail.findViewById(R.id.description_scroller);
        mBio = (LinearLayout) detail.findViewById(R.id.bio);
        
        // detail action buttons 
        mShare = (Button) findViewById(R.id.share);
        mShowDescription = (Button) findViewById(R.id.show_description);
        mShowBio = (Button) findViewById(R.id.show_bio);
        
        
        // Get saved data if destroyed
        data = (StateChangeData) getLastNonConfigurationInstance();
        
        // No saved data, run app as normal so we need to instantiate some variables that will have values assigned when data is pulled from server/database
        if( data == null){
        	
        	mSpeakers = new HashMap<Integer, Speaker>();
        	mTracks = new HashMap<Integer, Track>();
        	if(DAYS == null || DAYS.isEmpty())
        		DAYS = new ArrayList<Date>();
        	calendar = new ICal();
        	mCurrentDate = new Date(1900, 0, 0);
        
        // Saved data available, load it instead of from json/db
        } else {
        	mSpeakers = data.getmSpeakers();
        	mTracks = data.getmTracks();
        	DAYS = data.getDAYS();
        	calendar = new ICal();
        	calendar.setEvents(data.getmEvents());
        	mCurrentDate = data.getmCurrentData();
        	mDetail = data.getmDetail();
        	
        	int flipperTab = data.getFlipperTab();
        	mEvent = data.getmEvent();
        	
        	// Was looking at an event when destroyed so load it.
        	if(mDetail) {
        		if(mTracks.containsKey(mEvent.getTrack_id())) // If referenced track exists
        			mHeader.setBackgroundColor(mTracks.get(mEvent.getTrack_id()).getColor());
        		else // Otherwise use default track
        			mHeader.setBackgroundColor(new Track().getColor());
        		mTitle.setText(mEvent.getEvent_title());
        		mRoom_title.setText(mEvent.getRoom_title());
        		// Create format for displaying start/end times
        		DateFormat startFormat = new SimpleDateFormat("E, h:mm");
        		DateFormat endFormat = new SimpleDateFormat("h:mm a");
        		String timeString = startFormat.format(mEvent.getStart_time()) + " - " + endFormat.format(mEvent.getEnd_time());
        		mTime.setText(timeString);
        		
        		Speaker sp = new Speaker();
        		if(mSpeakers.containsKey(Integer.parseInt(mEvent.getSpeaker_ids()[0])))  // If first speaker exists
        			sp = mSpeakers.get(Integer.parseInt(mEvent.getSpeaker_ids()[0]));
        		// Otherwise default speaker is used
        			

        		if(sp!=null){
        			if(!mEvent.getPresenter().equals("null")) // If presenter exists
        				mSpeaker.setText(mEvent.getPresenter());
        			else 									  // Otherwise use speaker 
        				mSpeaker.setText(sp.getFullname());
        			if(mTracks.containsKey(mEvent.getTrack_id()))
        				mTimeLocation.setBackgroundColor(mTracks.get(mEvent.getTrack_id()).getColor());
        			else //Use default track
        				mTimeLocation.setBackgroundColor(new Track().getColor());
        			mDescription.setText(mEvent.getDescription());
        		}
        		
        		mFlipper.setDisplayedChild(flipperTab);
        		
        		// Depending on which view was visible on destroy, show that view.
        		if(data.getmDescriptionVisibility() == View.VISIBLE)
        			show_description();
        		if(data.getmBioVisibility() == View.VISIBLE)
        			show_bio();
        	}
        	
        	
        }
        
        
        
        eventsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
				//Remove toast if shown
				toast.cancel();

				
				Object item = mAdapter.mFiltered.get(position);
				if (item instanceof Date) {
					return;// ignore clicks on the dates
				}
				mEvent = (Event) item;
				if (mEvent.getTrack_id() == -1){
					return;// ignore clicks on non-events
				}
				//only set color if event has track_id
				if(mEvent.getTrack_id() != -1)
					if(mTracks.containsKey(mEvent.getTrack_id()))
						mHeader.setBackgroundColor(mTracks.get(mEvent.getTrack_id()).getColor());
					else //Use default track
						mHeader.setBackgroundColor(new Track().getColor());
				mTitle.setText(mEvent.getEvent_title());
				mRoom_title.setText(mEvent.getRoom_title());
				DateFormat startFormat = new SimpleDateFormat("E, h:mm");
				DateFormat endFormat = new SimpleDateFormat("h:mm a");
				String timeString = startFormat.format(mEvent.getStart_time()) + " - " + endFormat.format(mEvent.getEnd_time());
				mTime.setText(timeString);
				//TODO Fix for multiple speakers
				
				Speaker sp = new Speaker();
				if(mSpeakers.containsKey(Integer.parseInt(mEvent.getSpeaker_ids()[0]))) // If first speaker exists
					sp = mSpeakers.get(Integer.parseInt(mEvent.getSpeaker_ids()[0]));
				//Otherwise use default speaker
					
        		if(sp!=null){
        			if(!mEvent.getPresenter().equals("null"))
        				mSpeaker.setText(mEvent.getPresenter());
        			else
        				mSpeaker.setText(sp.getFullname());
        			if(mTracks.containsKey(mEvent.getTrack_id()))
        				mTimeLocation.setBackgroundColor(mTracks.get(mEvent.getTrack_id()).getColor());
        			else //Use default track
        				mTimeLocation.setBackgroundColor(new Track().getColor());
        			mDescription.setText(mEvent.getDescription());
        			show_description();
					mDescriptionScroller.scrollTo(0, 0);
					mFlipper.setInAnimation(mInRight);
					mFlipper.setOutAnimation(mOutLeft);
					mFlipper.showNext();
					mDetail = true;
				} else { 
					//TODO remove hardcoded string
					toast.setText("Event info not downloaded");
					toast.show();
				}
			}
		});
        
        mShowDescription.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				//Remove toast if showing
				toast.cancel();
				show_description();
			}
        });

        mShowBio.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				//Remove toast if showing
				toast.cancel();
				
				String error = show_bio();
				
				if(!error.equals("none")) { 
					//TODO remove hardcoded string
					toast.setText(error);
					toast.show();
				}
				
			}
			
			
			
			
        });
        
        mShare.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				//Remove toast if showing
				toast.cancel();
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
				Resources r = getApplicationContext().getResources();
				intent.putExtra(Intent.EXTRA_SUBJECT, r.getString(R.string.share_subject));
				intent.putExtra(Intent.EXTRA_TEXT, r.getString(R.string.share_text) + mTitle.getText() + r.getString(R.string.share_text2));
				startActivity(Intent.createChooser(intent, "Share"));
			}
        });
        
        // spawn loading into separate thread
        mHandler.post(new Runnable() {
        	public void run() { 
        		
        		loadOperation lo = new loadOperation();
    			lo.execute(false);

        	}
		});
    
       
    }//end onCreate
	
	
	private class loadOperation extends AsyncTask<Boolean, Void, Integer> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pdia = new ProgressDialog(Schedule.this);
			//TODO remove hardcoded string
			pdia.setMessage("Loading...");
			pdia.show();
		}
		
        @Override
        protected Integer doInBackground(Boolean... params) {
                Boolean force = params[0];
                
                if(mSpeakers.size() == 0){
                	loadSpeakers(force);
                }
                if(mTracks.size() == 0){
                	loadTracks(force);
                }
                if(DAYS.isEmpty()){
                	Log.d("HERE","HERE");
                	parseProposals(force);
                }
                
                return 1;
        }        

        @Override
        protected void onPostExecute(Integer uselessResult) {
        	pdia.dismiss();
        	setAdapter();
        	// If viewing the list of events 
        	if(!mDetail){
    			// When first launch date is set an impossible date
    			// in order to force refresh, here we check if it is that date
    			if(mCurrentDate.equals(new Date(1900, 0, 0)))
    				now();
    				
    			// Otherwise our current date was destroyed so we load it
    			else {
    				mAdapter.filterDay(mCurrentDate);
    				mDate.setText(date_formatter.format(mCurrentDate));
    				showList();
    			}
    		}
        }
	}
	
	@Override
    public void onResume() {
		super.onResume();
		
		// if not an orientation switch
		if(data == null){
			
			// Check that we have timeouts and last updated variables available
			if(!getPref(SCHEDULE_UPDATED).equals("") && !getPref(SCHEDULE_TIMEOUT).equals("")){
				//Check if the timeout has been hit
				if((Long.parseLong(getPref(SCHEDULE_UPDATED))
						+Long.parseLong(getPref(SCHEDULE_TIMEOUT))) < System.currentTimeMillis()){
					parseProposals(true);
					setAdapter();
					mAdapter.filterDay(mCurrentDate);
					mDate.setText(date_formatter.format(mCurrentDate));
					showList();
					
				}
			}

			// Check that we have timeouts and last updated variables available
			if(!getPref(SPEAKERS_UPDATED).equals("") && !getPref(SPEAKERS_TIMEOUT).equals(""))
				//Check if the timeout has been hit
				if((Long.parseLong(getPref(SPEAKERS_UPDATED))
						+Long.parseLong(getPref(SPEAKERS_TIMEOUT))) < System.currentTimeMillis()){
					loadSpeakers(true);
					setAdapter();
					mAdapter.filterDay(mCurrentDate);
					mDate.setText(date_formatter.format(mCurrentDate));
					showList();
				}

			// Check that we have timeouts and last updated variables available
			if(!getPref(TRACKS_UPDATED).equals("") && !getPref(TRACKS_TIMEOUT).equals(""))
				//Check if the timeout has been hit
				if((Long.parseLong(getPref(TRACKS_UPDATED))
						+Long.parseLong(getPref(TRACKS_TIMEOUT))) < System.currentTimeMillis()){
					loadTracks(true);
					setAdapter();
					mAdapter.filterDay(mCurrentDate);
					mDate.setText(date_formatter.format(mCurrentDate));
					showList();
				}
			
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		
		//So that when onCreate or onResume is called it won't think there was an orientation switch
		//and ignore checking whether to update data
		data = null;
	}
	
	
	
	// Save the app state when destroyed
	@Override
	public Object onRetainNonConfigurationInstance() {
		final StateChangeData data = new StateChangeData(DAYS, calendar.getEvents(),
				mSpeakers, mTracks, mCurrentDate, mDescription.getVisibility(),
				mBio.getVisibility(), mDetail, mFlipper.getDisplayedChild(),
				mEvent);
		return data;
	}
	
	
	
	
	//																		//
	// 								LOAD VIEWS 								//
	//																		//
	
	/**
	 * loads a view populated with the speakers info
	 * @param id
	 * @return
	 */
	private View loadBioView(int id) {
//		Integer id = new Integer(sid);
		Speaker speaker = new Speaker();
		View view = null;
		// Check memory to see if speaker had already been loaded
		if (mSpeakers.containsKey(id))
			speaker = mSpeakers.get(id);
		// Otherwise use default speaker
		
		// create view
		if (speaker != null) {
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.bio, null);
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(speaker.getFullname());
			TextView biography = (TextView) view.findViewById(R.id.biography);
			biography.setText(speaker.getBiography());
			
			// Single string variable for all text
			String textToSet = speaker.getAffiliation();
			
			if (textToSet != null && textToSet != ""  && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.affiliation);
				text.setText(textToSet);
			} else {
				TextView text = (TextView) view.findViewById(R.id.affiliation);
				text.setText("No Affiliation");
			}
			
			textToSet = speaker.getTwitter();
			if (textToSet != null && textToSet != ""  && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.twitter);
				text.setText(textToSet);
				View parent = (View) text.getParent();
				parent.setVisibility(View.VISIBLE);
			}
			
			textToSet = speaker.getEmail();
			if (textToSet != null && textToSet != "" && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.website);
				text.setText(textToSet);
				View parent = (View) text.getParent();
				parent.setVisibility(View.VISIBLE);
			}
			
			textToSet = speaker.getWebsite();
			if (textToSet != null && textToSet != "" && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.website);
				text.setText(textToSet);
				View parent = (View) text.getParent();
				parent.setVisibility(View.VISIBLE);
			}
			
			textToSet = speaker.getBlog();
			if (textToSet != null && textToSet != "" && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.blog);
				text.setText(textToSet);
				View parent = (View) text.getParent();
				parent.setVisibility(View.VISIBLE);
			}
			
			textToSet = speaker.getLinkedin();
			if (textToSet != null && textToSet != "" && textToSet != "null" && textToSet.length() > 0){
				TextView text = (TextView) view.findViewById(R.id.identica);
				text.setText(textToSet);
				View parent = (View) text.getParent();
				parent.setVisibility(View.VISIBLE);
			}
		}
		
		return view;
	}
	
	
	//																		//
	// 							MANIPULATE VIEWS 							//
	//																		//
	
	
	
	/**
	 * Shows the session description, hides all other sub views
	 */
	private void show_description(){
		mBio.setVisibility(View.GONE);
		mDescription.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Shows the session bio, hides all other subviews
	 */
	private String show_bio(){
		boolean display = true;
		//TODO remove hardcoded string
		String error = "none";
		mBio.removeAllViews();
		String[] speaker_ids = mEvent.getSpeaker_ids();
		
		
		if (speaker_ids != null) {
			for (int i=0; i<speaker_ids.length; i++) {

				View view = loadBioView(Integer.parseInt(speaker_ids[i]));
				if (view != null) {
					if (i>0){
						view.setPadding(0, 30, 0, 0);
					}
					mBio.addView(view);
				} else {
					// bio not yet downloaded
					display = false;
					//TODO Remove hardcoded string
					error = "The speakers for this event haven't been downloaded yet";
				}
			}
		} else { 
			// Event doesn't have any speakers
			display = false;
			//TODO Remove hardcoded string
			error = "This event doesn't have any speakers";
			
		}
		if(display){
			mDescription.setVisibility(View.GONE);
			mBio.setVisibility(View.VISIBLE);
		}
		
		return error;
	}
	
	/* sets the current day, filtering the list if need be */
	public void setDay(Date date) {
		if (isSameDay(mCurrentDate, date)) {
			// same day, just jump to current time
			mAdapter.now(date);
		} else {
			// different day, update the list
			mCurrentDate = date;
			
			mAdapter.filterDay(date);
			mDate.setText(this.getDayAsString(mCurrentDate));
		} 
		
		
	}
	
	/**
	 * Jumps the user to right now in the event list:
	 * 
	 *    - if its before or after the conference, it shows the beginning
	 *      of day 1
	 *    - if its during the conference it will show the first event 
	 *      currently underway
	 */
	public void now(){
		Date now = new Date();
		
		if(!DAYS.isEmpty()){

			if (now.before(DAYS.get(0)) || now.after(DAYS.get(DAYS.size() - 1))) {
				setDay(DAYS.get(0));
				showList();
			} else {
				// use now, since it will have the time of day for 
				// jumping to the right time
				setDay(now);
				showList();
			}
			
		} else {
			//TODO remove hardcoded string
			toast.setText("Conference info not downloaded");
			toast.show();
			
		}
		
	}
	
	/**
	 * Jumps to the next day, if not already at the end
	 */
	public void next() {
		
		//Find the index of the current day and then set to the next
		boolean found = false;
		for(int i = 0; i < DAYS.size() - 1 && !found; i++){
			if(mCurrentDate == DAYS.get(i)){
				setDay(DAYS.get(i+1));
				found = true;
			}
		}
		if(!found) {
			//TODO remove hardcoded string
			toast.setText("No more days");
			toast.show();
			
		}
		showList();
	}
	
	/**
	 * Jumps to the previous day if now already at the beginning
	 */
	public void previous() {
		
		boolean found = false;
		for(int i = DAYS.size() - 1; i > 0 && !found; i--){
			if(mCurrentDate == DAYS.get(i)){
				setDay(DAYS.get(i-1));
				found = true;
			}
		}
		if(!found) {
			//TODO remove hardcoded string
			toast.setText("No more days");
			toast.show();
		}
		showList();
	}
	
	/**
	 * Shows the event listing
	 */
	public void showList() {
		if (mDetail) {
			mFlipper.setInAnimation(mInLeft);
            mFlipper.setOutAnimation(mOutRight);
            mFlipper.showPrevious();
            mDetail=false;
		}
	}
	
	/**
	 * overridden to hook back button when on the detail page
	 */
	public boolean onKeyDown(int keyCode, KeyEvent  event){
		if (mDetail && keyCode == KeyEvent.KEYCODE_BACK){
			showList();
			mAdapter.filterDay(mCurrentDate);
			mDate.setText(date_formatter.format(mCurrentDate));
			showList();
			return true;
		}
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			//Cancel any toasts when pressing the menu button
			toast.cancel();
		}
		
		return super.onKeyDown(keyCode, event);
		
		
	}
	
	/* Creates the menu items */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_PREV, 0, "Previous Day").setIcon(R.drawable.ic_menu_back);
		SubMenu dayMenu = menu.addSubMenu("Day").setIcon(android.R.drawable.ic_menu_today);

		for(int i = 0; i < DAYS.size(); i++){
	    	//i+1 because zero is the days sub menu so start id at 1
	    	dayMenu.add(0, i+1, 0, this.getDayAsString(DAYS.get(i)));
	    	
	    }
	    
	    

		menu.add(0, MENU_NEXT, 0, "Next Day").setIcon(R.drawable.ic_menu_forward);
	    menu.add(0, MENU_NOW, 0, "Now").setIcon(android.R.drawable.ic_menu_mylocation);
	    menu.add(0, MENU_REFRESH, 0, "Refresh").setIcon(R.drawable.ic_menu_refresh);
	    menu.add(0, MENU_ABOUT, 0, "About").setIcon(android.R.drawable.ic_menu_info_details);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Manage per-day schedules dynamically, rather than hard coding them in.
	    
		int id = item.getItemId();
		
		switch (id) {
	    case MENU_NOW:
	        now();
	        return true;
		case MENU_PREV:
			previous();
			return true;
		case MENU_NEXT:
			next();
			return true;
		case MENU_ABOUT:
			showDialog(0);
			return true;
		case MENU_REFRESH:
			refreshOperation ro = new refreshOperation();
			ro.execute();
			
			return true;
	    }
	    
		if(id >= 1) {
			//submenu id starts at 1 when days start at 0 hence "id-1"
	    	setDay(DAYS.get(id-1));
	    	showList();
	    	return true;
		}
	    
	    return false;
	}
	
	private ProgressDialog pdia;
	
	private class refreshOperation extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pdia = new ProgressDialog(Schedule.this);
			pdia.setMessage("Refreshing...");
			pdia.show();
		}
		
        @Override
        protected Integer doInBackground(Void... params) {
        	
        	loadSpeakers(true);
        	loadTracks(true);
        	parseProposals(true);
    		
            return 1;
        }        

        @Override
        protected void onPostExecute(Integer uselessResult) {
        	pdia.dismiss();
        	mAdapter.notifyDataSetChanged();
        	mAdapter.filterDay(mCurrentDate);
			mDate.setText(date_formatter.format(mCurrentDate));
			showList();
        }
	}
	
	
	
	
	protected Dialog onCreateDialog(int id){
        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.about, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setCancelable(true);
        builder.setView(v);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        final AlertDialog alert = builder.create();
        return alert;
    }

	public static final DateFormat date_formatter = new SimpleDateFormat("E, MMMM d");

	/* Returns string with date formatted like "Friday, June 24". */
	public String getDayAsString(Date date) {
		return date_formatter.format(date);
	}

	//																		//
	// 								LOAD DATA 								//
	//																		//

	
	
	
	//Must be executed following loading when back on the ui thread if not a refresh
	private void setAdapter(){
		mAdapter = new EventAdapter(this, R.layout.listevent, calendar.getEvents());
        eventsListView.setAdapter(mAdapter);
	}
	
	
	public void loadSpeakers(boolean force){
		
		Speaker speaker = null;

		try {
			String raw_json = getURL(SPEAKER_URI, "SPEAKERS", force);

			//Log.d("CHECK DATABASE",raw_json);
			if (raw_json.equals("database")){
				DataBaseHandler db = new DataBaseHandler(getApplicationContext());
		  		Long size = db.numRows("SPEAKERS");
				
				for(int i = 1; i <= size; i++){
					speaker = getSpeaker(""+i, getApplicationContext());
					if(speaker == null)
						speaker = new Speaker();
					mSpeakers.put(speaker.getSpeaker_id(), speaker);
				}
				
			} else if(raw_json.equals("doNothing")) {
				
			} else {
				ArrayList<Speaker> speakersList = new ArrayList<Speaker>();
				JSONObject speakers = new JSONObject(raw_json);
				
				String timeout = speakers.getString("timeout");
				putPref(SPEAKERS_TIMEOUT, timeout);
				
				JSONArray json_speakers = speakers.getJSONArray("items");
				int size = json_speakers.length();
				for(int i=0; i<size; i++){
					JSONObject json = json_speakers.getJSONObject(i);
					speaker = new Speaker();

					speaker.setSpeaker_id(json.getInt("speaker_id"));
					speaker.setFullname(json.getString("fullname"));

					//Optional fields
					if (json.has("biography")) {
						speaker.setBiography(json.getString("biography").replace("\r",""));
					}
					if (json.has("affiliation")) {
						speaker.setAffiliation(json.getString("affiliation"));
					}
					if (json.has("twitter")) {
						speaker.setTwitter(json.getString("twitter"));
					}
					if (json.has("email")){
						speaker.setEmail(json.getString("email"));
					}
					if (json.has("website")) {
						speaker.setWebsite(json.getString("website"));
					}
					if (json.has("blog")) {
						speaker.setBlog(json.getString("blog"));
					}
					if (json.has("linkedin")) {
						speaker.setLinkedin(json.getString("linkedin"));
					}

					Log.d("CURRENT ROW", speaker.getFullname());
					speakersList.add(speaker);
					mSpeakers.put(speaker.getSpeaker_id(), speaker);
				
				}
				
				if(numRows("SPEAKERS",getApplicationContext()) == 0l){
					addSpeakers(speakersList,getApplicationContext());
				} else {
					deleteAllRows("SPEAKERS",getApplicationContext());
					addSpeakers(speakersList,getApplicationContext());
				}
				
				putPref(SPEAKERS_UPDATED,""+System.currentTimeMillis());
				
			}


		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// file couldn't be loaded
		}
		

	}
	
	private void loadTracks(boolean force){
		try{
			//TODO
			String raw_json = getURL(TRACKS_URI, "TRACKS", force);
			
			if (raw_json.equals("database")){
				DataBaseHandler db = new DataBaseHandler(getApplicationContext());
				long size = db.numRows("TRACKS");
				for(int i=1; i<=size; i++){
					Track track = new Track();
					Log.d("Track Row", ""+i);
					track = getTrack(""+i, getApplicationContext());
					
					mTracks.put(track.getTrack_id(), track);
				}
			
			} else if(raw_json.equals("doNothing")) {
				
			} else {
				ArrayList<Track> tracksList = new ArrayList<Track>();
				
				JSONObject tracks = new JSONObject(raw_json);
				
				String timeout = tracks.getString("timeout");
				putPref(TRACKS_TIMEOUT, timeout);
				
				JSONArray json_tracks = tracks.getJSONArray("tracks");
				int size = json_tracks.length();
				for(int i=0; i<size; i++){
					JSONObject json = json_tracks.getJSONObject(i);
					Track track = new Track();

					track.setTrack_id(json.getInt("track_id"));
					track.setTrack_title(json.getString("track_title"));
					
					
					if (json.has("color"))
						track.setColor(Color.parseColor(json.getString("color")));
					
				
					if (json.has("color_text"))
						track.setColor_text(Color.parseColor(json.getString("color_text")));
					
					
					// TODO
					// dont touch database if no internet, database is already loaded

					Log.d("CURRENT ROW", track.getTrack_title());

					tracksList.add(track);
					mTracks.put(track.getTrack_id(), track);
				}
				
				if(numRows("TRACKS",getApplicationContext()) == 0l){
					addTracks(tracksList,getApplicationContext());
				} else {
					deleteAllRows("TRACKS",getApplicationContext());
					addTracks(tracksList,getApplicationContext());
				}
				
				putPref(TRACKS_UPDATED, ""+System.currentTimeMillis());
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// unable to get file, show error to user
			// TODO
		}
	}
	

	/**
	 * fetches a url and returns it as a string.  This method will cache the
	 * result locally and use the cache on repeat loads
	 * @param uri - a uri beginning with http://
	 * @param force - force refresh of data
	 * @return
	 */
	private String getURL(String uri, String table, boolean force) throws IOException{
		InputStream is = null;
		
		String line;
		StringBuilder sb = new StringBuilder();
		
		
		
		try {
			// determine whether to open local file or remote file
			// Retrieve from database instead of raw file
			DataBaseHandler db = new DataBaseHandler(getApplicationContext());
			
			if (table.equals("SCHEDULE") && (db.numRows("SCHEDULE") != 0) && !force && ((Long.parseLong(getPref(SCHEDULE_UPDATED))
					+Long.parseLong(getPref(SCHEDULE_TIMEOUT))) > System.currentTimeMillis())){
				return "database";
			}
			else if(table.equals("SPEAKERS") && (db.numRows("SPEAKERS") != 0) && !force && ((Long.parseLong(getPref(SPEAKERS_UPDATED))
					+Long.parseLong(getPref(SPEAKERS_TIMEOUT))) > System.currentTimeMillis())){
				return "database";
			}
			else if(table.equals("TRACKS") && (db.numRows("TRACKS") != 0) && !force && ((Long.parseLong(getPref(TRACKS_UPDATED))
					+Long.parseLong(getPref(TRACKS_TIMEOUT))) > System.currentTimeMillis())){
				return "database";
			
			} else {
				URL url = new URL(uri);
				URLConnection conn = null;
				try {
					conn = url.openConnection(); 
					conn.setDoInput(true); 
					conn.setUseCaches(false);
					is = conn.getInputStream();
					
				} catch (IOException e) {
					// fall back to local file if exists, regardless of age
					if ((db.numRows("SCHEDULE") != 0)) {
						return "database";
					} else if(db.numRows("SPEAKERS") != 0) {
						return "database";
					} else if(db.numRows("TRACKS") != 0){
						return "database";
					}
					else {
						throw e;
					}
				}
			}
		
			
			//check json http header here
			Boolean jsonModified = jsonModified(table);

			if(jsonModified){

				// read entire file, write cache at same time if we are fetching from the remote uri
				BufferedReader br;
				if (is!=null){ 
					br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8192);

					while ((line = br.readLine()) != null) {
						sb.append(line);

					}
				} else {
					return "database";
				}
			} else if(mCurrentDate.equals(new Date(1900, 0, 0))){
				return "database";}
			else{
				return "doNothing";
			}
			
		} catch (NumberFormatException e){
			throw e;
			
		} catch (IOException e) {
			// failure to get file, throw this higher
			throw e;
			
		 } finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
		return sb.toString();
	}
	
	
	
	/**
	 * parse events from json file and update the given calendar
	 * @param calendar
	 * @param force - force refresh
	 */
	private void parseProposals(boolean force){
		ArrayList<Event> events = new ArrayList<Event>();
		try{
			//TODO
			String raw_json = getURL(SCHEDULE_URI, "SCHEDULE", force);
			Log.d("RAW_JSON",raw_json);
			
			if (raw_json.equals("database")){
				DataBaseHandler db = new DataBaseHandler(getApplicationContext());
				long size = db.numRows("SCHEDULE");
				for(int i=1; i<=size; i++){
					mEvent = getSchedule(""+i, getApplicationContext());
					
					if(mEvent != null){
						events.add(mEvent);

						//If no days then add the first
						if(DAYS.isEmpty())
							DAYS.add(mEvent.getStart_time());
						//if the event date is after the last added days date then add the day
						Log.d("event.date",""+mEvent.getStart_time().getDate());
						Log.d("Last added date", ""+DAYS.get(DAYS.size() - 1).getDate());
						if(mEvent.getStart_time().getDate() > (DAYS.get(DAYS.size() - 1).getDate())){
							DAYS.add(mEvent.getStart_time());
						}
						
					}
					
				}
			
			} else if(raw_json.equals("doNothing")) {
				
			} else {
				
			
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-'07:00'");
				JSONObject schedule = new JSONObject(raw_json);
				

				String timeout = schedule.getString("timeout");
				putPref(SCHEDULE_TIMEOUT, timeout);
				
				
				JSONArray json_events = schedule.getJSONArray("items");
				int size = json_events.length();
				for(int i=0; i<size; i++){
					JSONObject json = json_events.getJSONObject(i);
					Event event = new Event();

					event.setEvent_id(json.getInt("event_id"));
					event.setEvent_title(json.getString("event_title"));
					
					event.setStart_time(formatter.parse(json.getString("start_time")));
					
					//If no days then add the first
					if(DAYS.isEmpty())
						DAYS.add(event.getStart_time());
					//if the event date is after the last added days date then add the day
					Log.d("event.date",""+event.getStart_time().getDate());
					Log.d("Last added date", ""+DAYS.get(DAYS.size() - 1).getDate());
					if(event.getStart_time().getDate() > (DAYS.get(DAYS.size() - 1).getDate())){
						DAYS.add(event.getStart_time());
					}
					
					event.setEnd_time(formatter.parse(json.getString("end_time")));
					
					if(json.has("description")){
						event.setDescription(json.getString("description")
								.replace("\r","")
								.replace("<br>","\n")
								.replace("<blockquote>","")
								.replace("</blockquote>","")
								.replace("<b>","")
								.replace("</b>",""));
						if (event.getDescription().equals("")){
							//XXX fill description with spaces, fixes a bug where android will
							//    center the logo on the detail page without content in description
							event.setDescription("                                                                                  ");
						}
					}
					if(json.has("room_title")){
						event.setRoom_title(json.getString("room_title"));
						if (event.getRoom_title() == "null"){
							event.setRoom_title("");
						}
					}
					if(json.has("track_id")){
						if (json.has("track_id")){
							event.setTrack_id(json.getInt("track_id"));
						} else {
							event.setTrack_id(-1);
						}
					}
					
					if (json.has("speaker_ids")){
						
						JSONArray speaker_ids = json.getJSONArray("speaker_ids");
						String [] stringArray = new String[speaker_ids.length()];
						for(int j = 0; j < speaker_ids.length(); j++)
						{						    
							//int in = speaker_ids.getInt(j);
							String aux = speaker_ids.toString();
							aux = aux.replace("\"", "");
							aux = aux.replace("\"", "");
							aux = aux.replace("[", "");
							aux = aux.replace("]", "");
							stringArray = aux.split(",");
							Log.d("STRINGARRAY", stringArray[j]);

						}
						event.setSpeaker_ids(stringArray);
					}
					
					
					if(json.has("presenter")){
						event.setPresenter(json.getString("presenter"));
					} else {
						event.setPresenter("");
					}
					
					Log.d("CURRENT ROW", event.getEvent_title());

					events.add(event);
				}
				
				if(numRows("SCHEDULE",getApplicationContext()) == 0l){
					addEvents(events,getApplicationContext());
				} else {
					deleteAllRows("SCHEDULE", getApplicationContext());
					addEvents(events,getApplicationContext());
				}
				
				putPref(SCHEDULE_UPDATED, ""+System.currentTimeMillis());
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// unable to get file, show error to user
			// TODO
		}
		calendar.setEvents(events);
		
		
	}
	
	
	private boolean jsonModified(String json){
		
		try{
			
			HttpClient client = new DefaultHttpClient();
			HttpGet request = null;
			
			
			if(json.equals("SCHEDULE")){
				request = new HttpGet(SCHEDULE_URI);
			
			}
			if(json.equals("SPEAKERS")){
				 request = new HttpGet(SPEAKER_URI);
				
				}
			if(json.equals("TRACKS")){
				 request = new HttpGet(TRACKS_URI);
				
			}
			HttpResponse response = null;
			if(request != null)
				response = client.execute(request);

			String server = ""+response.getFirstHeader("Last-Modified");
			Log.d("SERVERMODIFIED", server);
			if(!server.equals("") || server != null){
				server = server.substring(15,server.length());
				Log.d("SERVERMODIFIED", server);
				httpHeaderFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date current = httpHeaderFormat.parse(server);


				String lm = getPref(json+"lastModified");
				Log.d("LASTMODIFIED", lm);
				if(!lm.equals("")){
					Date lastModified = new Date(lm);
					if(current.after(lastModified)){
						putPref(json+"lastModified",lm);

						return true;
					}
				} else { //lastModified doesn't exist so still need to load from json
					putPref(json+"lastModified", server);
					return true;
				}
			}



		} catch(Exception e) {
			e.printStackTrace();
		}

		

		return false;
		
	}
		
	
	/**
	 * EventAdapter used for displaying a list of events
	 *
	 */
	private class EventAdapter extends ArrayAdapter<Event> {
		private ArrayList<Event> mItems;
		private ArrayList<Object> mFiltered;
		
		public EventAdapter(Context context, int textViewResourceId,
				ArrayList<Event> items) {
			super(context, textViewResourceId, items);
			this.mItems = items;
			mFiltered = new ArrayList<Object>();
		}

		/**
		 * Filters the list to contain only events for the given date.
		 * @param date - date to filter by
		 */
		public void filterDay(Date date){
			ArrayList<Event> items = mItems;
			ArrayList<Object> filtered = new ArrayList<Object>();
			int size = mItems.size();
			Date currentStart = null;
			for (int i=0; i<size; i++){
				Event event = items.get(i);
				if(isSameDay(date, event.getStart_time())){
					if(currentStart == null || event.getStart_time().after(currentStart)) {
						currentStart = event.getStart_time();
						filtered.add(currentStart);
					}
					filtered.add(event);
				}
			}
			mFiltered = filtered; 
			notifyDataSetChanged();
			now(date);
		}
		
		/**
		 * sets the position to the current time
		 * @param date
		 */
		public void now(Date date) {
			ArrayList<Object> filtered = mFiltered;
			int size = filtered.size();
			for (int i=0; i<size; i++){
				Object item = filtered.get(i);
				
				// find either the first session that hasn't ended yet
				// or the first time marker that hasn't occurred yet.
				if (item instanceof Date ){
					Date slot = (Date) item;
					if (date.before(slot)) {
						eventsListView.setSelection(i);
						return;
					}
				} else {
					Event event = (Event) item;
					if (event.getEnd_time().after(date)) {
						// should display the time marker instead of the
						// session
						eventsListView.setSelection(i-1);
						return;
					}
				}
			}
			
			// no current event was found, jump to the next day
			next();
		}
		
		public int getCount(){
			return mFiltered.size();
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			Object item = mFiltered.get(position);
			if (item instanceof Date) {
				Date date = (Date)item;
				v = vi.inflate(R.layout.list_slot, null);
				TextView time = (TextView) v.findViewById(R.id.time);
				DateFormat formatter = new SimpleDateFormat("h:mm a");
				time.setText(formatter.format(date));
			} else {
				Event event = (Event) item;
				v = vi.inflate(R.layout.listevent, null);
				if (event != null) {
					TextView event_title = (TextView) v.findViewById(R.id.title);
					TextView room_title = (TextView) v.findViewById(R.id.room_title);
					TextView time = (TextView) v.findViewById(R.id.time);
					if (event.getTrack_id() != -1) {
						//Context context = getApplicationContext();
						TextView track = (TextView) v.findViewById(R.id.track);
						if(mTracks.containsKey(event.getTrack_id())) {
							track.setTextColor(mTracks.get(event.getTrack_id()).getColor_text());
							track.setText(mTracks.get(event.getTrack_id()).getTrack_title());
						} else { //Fall back on default track at -1
							track.setTextColor(new Track().getColor_text());
							track.setText(new Track().getTrack_title());
						}
					
					}
					if (event_title != null) {
						event_title.setText(event.getEvent_title());
					}
					if (room_title != null) {
						room_title.setText(event.getRoom_title());
					}
					if (time != null) {
						DateFormat formatter = new SimpleDateFormat("h:mm");
						time.setText(formatter.format(event.getStart_time()) + "-" + formatter.format(event.getEnd_time()));
					}
					
				}
			}
			return v;
		}
	}
	
	/**
	 * Checks if two dates are the same day
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
 
	/**
	 * Checks if two calendars are the same day
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
	
	
	//																//
	//                     SHARED  PREFERENCES                      //
	//																//
	
	private void putPref(String key, String value){
		 SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	     SharedPreferences.Editor editor = sharedPreferences.edit();
	     editor.putString(key, value);
	     editor.commit();
	}
	
	private String getPref(String key){
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedPref = sharedPreferences.getString(key, "");
        return savedPref;
	}
	
	
	
	
	//																//
	//						DATABASE HANDLERS						//
	//																//
	
  	
	public static Long addEvents(ArrayList<Event> events, Context context){
		DataBaseHandler db = new DataBaseHandler(context);
  		return db.addEvents(events);
  	}
	
	public static Long addSpeakers(ArrayList<Speaker> speakersList, Context context){
		DataBaseHandler db = new DataBaseHandler(context);
  		return db.addSpeakers(speakersList);
  	}

  	public static Long addTracks(ArrayList<Track> tracks, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.addTracks(tracks);
  	}
  	
  	public static Event getSchedule(String event_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.getScheduleRow(event_id);
  	}
  	
  	public static Speaker getSpeaker(String speaker_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.getSpeakersRow(speaker_id);
  	}
  		
  	public static Track getTrack(String track_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.getTracksRow(track_id);
  	}
  	
  	public static int eventExists(String event_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.existsEvent(event_id);
  	}
  	
  	public static int speakerExists(String speaker_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.existsSpeaker(speaker_id);
  	}
  	
  	public static int trackExists(String track_id, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.existsTrack(track_id);
  	}
	
  	public static Long numRows(String table, Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.numRows(table);
  	}
  	
  	public static Long deleteAllRows(String table,Context context){
  		DataBaseHandler db = new DataBaseHandler(context);
  		return db.deleteAllRows(table);
  	}
  	

}
