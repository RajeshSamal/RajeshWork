package com.docker.dummy;

import java.util.List;

import com.docker.ssh.SshConnectionFactory;
import com.jcraft.jsch.Session;

public class CreateImage {
	
	
	static String imageJson = "\"name\": \"mymongodb\"";
	
	
	
	
	
	public static void main(String[] args){
		
		try {
			//Session session = SshConnectionFactory.getSeesion("ubuntu", "208.55.220.5", "iU5ffjnrp");
			//SshConnectionFactory.copyFileJson(session, imageJson);
			//SshConnectionFactory.runCommand(session, " cd /home/ubuntu/Docker-Plan &&  bash as.sh");
			String name = "plan";
			
			List<String> script = SshConnectionFactory.craeteImageScript(args[0]);
			Session session = SshConnectionFactory.getSeesion("ubuntu", "208.55.220.128", "uD8irhmex");
			String command = "rm -rf "+name+"&& mkdir "+ name;
			SshConnectionFactory.runCommand(session, command);
			for(String sc: script){
				String dir = sc.substring(0, sc.lastIndexOf('.'));
				command = "cd "+name+" && mkdir "+ dir;
				SshConnectionFactory.runCommand(session, command);
				SshConnectionFactory.copyFile(session, sc, "/home/ubuntu/"+name+"/"+dir);
				command = "cd /home/ubuntu/"+name+"/"+dir +" && dos2unix "+ sc;
				SshConnectionFactory.runCommand(session, command);
				command = "cd "+ "/home/ubuntu/"+name+"/"+dir + " && bash "+ sc;
				SshConnectionFactory.runCommand(session, command);
				command = "cd "+ "/home/ubuntu/"+name+"/"+dir + " && docker build -t "+name+dir +" .";
				SshConnectionFactory.runCommand(session, command);
				command = "docker tag -f "+ name+dir + " dockerimage.nttclouds.co:8080/"+name+dir;
				SshConnectionFactory.runCommand(session, command);
				command = "docker push dockerimage.nttclouds.co:8080/"+name+dir ;
				SshConnectionFactory.runCommand(session, command);
			}
			
			session.disconnect();
			
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
