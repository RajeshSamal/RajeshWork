package com.ntt.docker;

import java.util.List;

public class Node {

	private String name;
	private String type;
	private String restart;
	private int order;
	private String username;
	private String password;
	private String dbname;
	private int noOfInstances;
	private String image;
	private List<String> link;
	private List<String> port;
	private List<String> option;
	private int sleep;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestart() {
		return restart;
	}

	public void setRestart(String restart) {
		this.restart = restart;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public int getNoOfInstances() {
		return noOfInstances;
	}

	public void setNoOfInstances(int noOfInstances) {
		this.noOfInstances = noOfInstances;
	}

	public List<String> getLink() {
		return link;
	}

	public void setLink(List<String> link) {
		this.link = link;
	}

	public List<String> getPort() {
		return port;
	}

	public void setPort(List<String> port) {
		this.port = port;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public List<String> getOption() {
		return option;
	}

	public void setOption(List<String> option) {
		this.option = option;
	}

	
	

}
