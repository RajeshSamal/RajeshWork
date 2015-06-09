package com.docker.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	
	private static List<String> appList = new ArrayList<String>();
	private static Map<String, String > vmList = new HashMap<String, String>();
	
	public static synchronized List<String> getAppList() {
		return appList;
	}

	public static synchronized Map<String, String> getVmList() {
		return vmList;
	}

}
