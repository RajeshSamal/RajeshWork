package com.ntt.script;

public interface Constants {
	
	String stopAll = "StopAll";
	String mongoType = "mongodb";
	String mysqlType = "mysql";
	String tomcatType = "tomcat";
	String nginxType = "nginx";
	String dockerCommand ="docker run -d -P ";
	String isRestart ="always";
	String restartCommand = "--restart=always ";
	String nameCommand = "--name ";
	String stopCommand = "docker stop $(docker ps -a -q);docker rm $(docker ps -a -q);";
	String sleepCommand = "sleep ";
	String linkCommand = "--link ";
	String nginxPortMap =  "-p 80:80 -p 443:443 ";
	String single =  "Single";
	String singleHost =  "SingleHost";
	String multipleHost =  "MultipleHost";
	String logincommand = "docker login --email=\"rajesh@rajesh.com\" --password=\"rajesh\" --username=\"rajesh\" https://dockerimage.nttclouds.co:8080/";
	String dockerRegistry = "dockerimage.nttclouds.co:8080/";
}
