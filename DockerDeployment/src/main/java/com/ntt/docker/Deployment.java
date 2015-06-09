package com.ntt.docker;

import java.util.List;

public class Deployment {
	
	private String type;
	private String state;
	private String environment;
	
	private List<Node> node;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public List<Node> getNode() {
		return node;
	}

	public void setNode(List<Node> node) {
		this.node = node;
	}
	
	

}
