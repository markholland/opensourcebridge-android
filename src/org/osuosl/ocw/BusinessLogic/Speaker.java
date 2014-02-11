package org.osuosl.ocw.BusinessLogic;

import java.util.ArrayList;
import java.util.Iterator;

public class Speaker {
	
	private int id;
	private String fullname;
	private String biography;
	private String affiliation;
	private String twitter;
	private String email;
	private String website;
	private String blog;
	private String linkedin;
	private ArrayList<Event> events;
	private ArrayList<Event> is_presenter;
	
	//	//A default speaker for when an event references a speaker not present locally.
	//	public Speaker(){
	//
	//		this.id = -1;
	//		this.fullname = "Not available"; //Be sure that in strings.xml this is assigned to "not_available"
	//		this.biography = null;
	//		this.affiliation = null;
	//		this.twitter = null;
	//		this.email = null;
	//		this.website = null;
	//		this.blog = null;
	//		this.linkedin = null;
	//
	//	}

	//Constructor with all parameters as Strings to facilitate when retrieving from the database
	
	public Speaker(int id, String fullname, String biography, String affiliation,
			String twitter, String email, String website, String blog, 
			String linkedin){
		
		this.id = id;
		this.fullname = fullname;
		this.biography = biography;
		this.affiliation = affiliation;
		this.twitter = twitter;
		this.email = email;
		this.website = website;
		this.blog = blog;
		this.linkedin = linkedin;
		
		this.events = new ArrayList<Event>();
		this.is_presenter = new ArrayList<Event>();
		
	}
	
	// Getters and Setters auto-generated by eclipse //

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public Iterator<Event> getEvents() {
		return events.iterator();
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
	
	public void addEvent(Event event) {
		this.events.add(event);
	}
	
	public void removeEvent(Event event) { 
		this.events.remove(event);
	}

	public Iterator<Event> getIs_presenter() {
		return is_presenter.iterator();
	}
	
	public void setIs_presenter(ArrayList<Event> is_presenter) {
		this.is_presenter = is_presenter;
	}
	
	public void addEventIsPresenter(Event event) {
		this.is_presenter.add(event);
	}
	
	public void removeEventIsPresenter(Event event) {
		this.is_presenter.remove(event);
	}

	public boolean containsEvent(Event event) {
		return events.contains(event);
	}
	
	

}


