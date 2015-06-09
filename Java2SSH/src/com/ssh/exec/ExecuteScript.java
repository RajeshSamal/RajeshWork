package com.ssh.exec;

import java.io.InputStream;



import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ExecuteScript {
	String host = null;

	String user = null;
	
	String password = null;
	
	String command = null;


	public String exec() throws Throwable {
		String str = "failed";
		try {
			JSch jsch = new JSch();

			Session session = jsch.getSession(user, host, 22);

			session.setPassword(password);
			
			java.util.Properties config = new java.util.Properties(); 
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

			

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);


			channel.setInputStream(null);


			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					str = "exit-status: " + channel.getExitStatus();
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();

		} catch (Throwable e) {
			throw e;
		}
		return str;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCommand() {
		return command;
	}


	public void setCommand(String command) {
		this.command = command;
	}



	
}
