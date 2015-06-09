package com.ntt.docker;

import java.util.List;

public class Image {
	
	List<ImageConfig> imageConfig;
	List<App> app;
	List<PostScript> postscript;
	List<Ports> ports;
	List<String> misc;
	String type;
	String imageName;
	public List<ImageConfig> getImageConfig() {
		return imageConfig;
	}
	public void setImageConfig(List<ImageConfig> imageConfig) {
		this.imageConfig = imageConfig;
	}
	public List<App> getApp() {
		return app;
	}
	public void setApp(List<App> app) {
		this.app = app;
	}
	public List<PostScript> getPostScripts() {
		return postscript;
	}
	public void setPostScripts(List<PostScript> postScripts) {
		this.postscript = postScripts;
	}
	public List<Ports> getPorts() {
		return ports;
	}
	public void setPorts(List<Ports> ports) {
		this.ports = ports;
	}
	
	public List<String> getMisc() {
		return misc;
	}
	public void setMisc(List<String> misc) {
		this.misc = misc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	

}
