package org.osuosl.ocw.DataAccess;

public class SpeakerDTO {

	private int id;
	private String fullname;
	private String biography;
	private String affiliation;
	private String twitter;
	private String email;
	private String website;
	private String blog;
	private String linkedin;
	
	
	public SpeakerDTO(int id, String fullname, String biography,
			String affiliation, String twitter, String email, String website,
			String blog, String linkedin) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.biography = biography;
		this.affiliation = affiliation;
		this.twitter = twitter;
		this.email = email;
		this.website = website;
		this.blog = blog;
		this.linkedin = linkedin;
	}


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
	
	
	
	
}
