package com.ntt.docker;

import java.util.List;

public interface ImageInterface {
	
	
	public String getDownloadLocation();
	public void setDownloadLocation(String downloadLocation);
	public String getDesitnation();
	public void setDesitnation(String desitnation) ;
	public String getSource() ;
	public void setSource(String source);

	
}
