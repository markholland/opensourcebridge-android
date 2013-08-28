package org.osuosl.ocw;

public class Speaker {
	
	private int speaker_id;
	private String fullname;
	private String biography;
	private String affiliation;
	private String twitter;
	private String email;
	private String website;
	private String blog;
	private String linkedin;
	
	public Speaker(){

		this.speaker_id = -1;
		this.fullname = "Not available";
		this.biography = null;
		this.affiliation = null;
		this.twitter = null;
		this.email = null;
		this.website = null;
		this.blog = null;
		this.linkedin = null;

	}

	public Speaker(int speaker_id, String fullname, String biography, String affiliation,
			String twitter, String email, String website, String blog, 
			String linkedin){
		
		this.speaker_id = speaker_id;
		this.fullname = fullname;
		this.biography = biography;
		this.affiliation = affiliation;
		this.twitter = twitter;
		this.email = email;
		this.website = website;
		this.blog = blog;
		this.linkedin = linkedin;
		
	}
	
	public Speaker(String speaker_id, String fullname, String biography, String affiliation,
			String twitter, String email, String website, String blog, 
			String linkedin){
		
		this.speaker_id = Integer.parseInt(speaker_id);
		this.fullname = fullname;
		this.biography = biography;
		this.affiliation = affiliation;
		this.twitter = twitter;
		this.email = email;
		this.website = website;
		this.blog = blog;
		this.linkedin = linkedin;
		
	}

	public int getSpeaker_id() {
		return speaker_id;
	}

	public void setSpeaker_id(int speaker_id) {
		this.speaker_id = speaker_id;
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

	


}


