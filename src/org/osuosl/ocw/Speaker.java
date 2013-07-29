package org.osuosl.ocw;

public class Speaker {
	
	public int id;
	public String name;
	public String biography;
	public String twitter;
	public String affiliation;
	public String identica;
	public String website;
	public String blog;
	
	public Speaker(){

		this.id = -1;
		this.name = null;
		this.biography = null;
		this.twitter = null;
		this.affiliation = null;
		this.identica = null;
		this.website = null;
		this.blog = null;

	}

	public Speaker(int id, String name, String biography, String twitter, String affiliation, 
			String identica, String website, String blog){
		this.id = id;
		this.name = name;
		this.biography = biography;
		this.twitter = twitter;
		this.affiliation = affiliation;
		this.identica = identica;
		this.website = website;
		this.blog = blog;
		
	}
	
	public Speaker(String id, String name, String biography, String twitter, String affiliation, 
			String identica, String website, String blog){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.biography = biography;
		this.twitter = twitter;
		this.affiliation = affiliation;
		this.identica = identica;
		this.website = website;
		this.blog = blog;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getIdentica() {
		return identica;
	}

	public void setIdentica(String identica) {
		this.identica = identica;
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


}


