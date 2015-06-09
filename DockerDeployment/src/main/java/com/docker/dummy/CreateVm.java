package com.docker.dummy;

import com.docker.ssh.SshConnectionFactory;
import com.jcraft.jsch.Session;

public class CreateVm {
	
	
	static String fileJson = "\"name\": \"mymongodb\"";
	
	
	
	
	
	public static void main(String[] args){
		
		try {
			Session session = SshConnectionFactory.getSeesion("ubuntu", "208.55.220.5", "iU5ffjnrp");
			/*SshConnectionFactory.copyFileJson(session);
			SshConnectionFactory.runCommand(session, " cd /home/ubuntu/Docker-Plan &&  bash LaunchPlan.sh chendilDocker");*/
			String ip = SshConnectionFactory.readLine(session,"/home/ubuntu/Docker-Plan/NewVMIP.txt");
			session.disconnect();
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
