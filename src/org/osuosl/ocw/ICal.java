package org.osuosl.ocw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import org.osuosl.ocw.BusinessLogic.Event;




/**
 * Class for parsing ical files
 * @author Peter Krenesky - peter@osuosl.org
 *
 */
public class ICal {
	
	private String mName, mVersion, mProdid, mCalscale;
	private ArrayList<Event> mEvents; 
	
	public ICal() {}
	
	public ICal(InputStream is){
		mEvents = new ArrayList<Event>();
		Event event = null;
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss"); 
		
        
			while ((line = br.readLine()) != null) {
				if(line.equals("BEGIN:VEVENT"))  {
					// start of event
					event = new Event();
      
				} else if(line.equals("END:VEVENT"))  {
					if (event != null){
						// clean up description
						event.setDescription(event.getDescription()
												.replace("\\,", ",")
												.replace("\\;", ";"));
						
			    		mEvents.add(event);
			    		event = null;
					}
				} else if(line.startsWith("DTEND:"))  {
					String str = line.substring(6);
					event.setEnd_time((Date)formatter.parse(str));
				
				} else if(line.startsWith("DTSTART:"))  {
					String str = line.substring(8);
					event.setStart_time((Date)formatter.parse(str));
				
				} else if(line.startsWith("SUMMARY:"))  {
					event.setTitle(line.substring(8)
										.replace("\\,", ",")
										.replace("\\;", ";"));
				
				} else if(line.startsWith("DESCRIPTION:"))  {
					event.setDescription(line.substring(12));
				
				} else if(line.startsWith(" "))  {
					// continuation of description
					event.setDescription(event.getDescription() + line.substring(1));
				
//				} else if(line.startsWith("URL:"))  {
////					event.url = line.substring(4);
//					event.setEvent_id(Integer.parseInt(event.url.substring(37)));
//					
				} else if(line.startsWith("LOCATION:"))  {
					event.setRoom_title(line.substring(9));
				
				} else if(line.startsWith("X-WR-CALNAME:"))  {
					mName = line.substring(13); 
				} else if(line.startsWith("X-WR-TIMEZONE:"))  {
					// TODO - parse time zone
				} else if(line.startsWith("VERSION:"))  {
					mVersion = line.substring(8);
				} else if(line.startsWith("PRODID:"))  {
					mProdid = line.substring(7);
				} else if(line.startsWith("CALSCALE:"))  {
					mCalscale = line.substring(9);
				}
      }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Event> getEvents(){
		return mEvents;
	}
	
	public void setEvents(ArrayList<Event> events){
		mEvents = events;
	}
}
