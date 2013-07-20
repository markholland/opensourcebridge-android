package org.osuosl.ocw;

public class DataHandler {

	private int Id;
	private String name;
	private String value;
	
	public DataHandler(String name, String value){
		this.Id = -1;
		this.name = name;
		this.value = value;
	}
	
	public DataHandler(int Id, String name, String value){
		this.Id = Id;
		this.name = name;
		this.value = value;
	}


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	
	
}
