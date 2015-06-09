package com.ntt.docker;

import java.util.List;

public class App implements ImageInterface{
	
	List<Doc>  file;
	String downloadLocation;
	String desitnation;
	String source;
	
	public List<Doc> getFile() {
		return file;
	}
	public void setFile(List<Doc> file) {
		this.file = file;
	}
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
