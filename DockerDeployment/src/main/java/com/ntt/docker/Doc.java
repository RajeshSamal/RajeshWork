package com.ntt.docker;

public class Doc implements ImageInterface{
	
	String downloadLocation;
	String desitnation;
	String source;
	
	public String getDownloadLocation() {
		return downloadLocation;
	}
	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}
	public String getDesitnation() {
		return desitnation;
	}
	public void setDesitnation(String desitnation) {
		this.desitnation = desitnation;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	

}
