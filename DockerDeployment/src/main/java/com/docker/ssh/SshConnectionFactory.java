package com.docker.ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.ntt.script.Main;
import com.ntt.script.PrepareImage;
import com.ntt.script.TopologyImageStore;

public class SshConnectionFactory {
	private static final String PRIVATE_KEY_LOCATION ="dockerkey";
	
	
	
	

	

	

	public static synchronized Session getSeesion(String user, String host,
			String password) throws Throwable {

		JSch jsch = new JSch();
		

		Session session = jsch.getSession(user, host, 22);

		session.setPassword(password);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		return session;

	}
	
	public static synchronized Session getSeesionByKey(String user, String host) throws Throwable {

		JSch jsch = new JSch();
		final byte[] privateKey = getPrivateKeyAsByteStream();
		KeyFactory f = KeyFactory.getInstance("RSA");
	      System.out.println(f.getProvider().getName());
	      jsch.addIdentity(user,
	              privateKey,
	             null,
	             new byte[0]
	      );
		Session session = jsch.getSession(user, host, 22);


		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		return session;

	}
	
	public static synchronized String runCommand(Session session, String command) throws Throwable {
		String str = null;
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
		return str;
		
	}
	
	
	
	public static synchronized String readLine(Session session, String filePath) throws Throwable {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp channelSftp = (ChannelSftp)channel;
		InputStream stream = channelSftp.get(filePath);
	    String ip=null;
	    String tmp=null;
	   
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
	        while (( tmp=br.readLine()) != null) {
	    		ip = tmp;
	    	}
	        br.close();
	        ip= ip.substring(ip.lastIndexOf('=')+1);
	        ip=ip.trim();
	       
	    } finally {
	        stream.close();
	    }
        channel.disconnect();
        return ip;
	}
	
	
	
	
	public static synchronized void copyFile(Session session, String fileName, String remoteDir) throws Throwable {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp channelSftp = (ChannelSftp)channel;
	    channelSftp.cd(remoteDir);
	   
	    File f = new File(fileName);
        channelSftp.put(new FileInputStream(f), f.getName());
        channel.disconnect();
	}
	
	public static synchronized List craeteImageScript(String topologyName) throws Throwable {
		
		List<String> scriptList = PrepareImage.prepareScript(topologyName);
		return scriptList;
		
	}
	
public static synchronized void craeteContainerScript(String topologyName) throws Throwable {
		
		Main.prepareContainerScript(topologyName);
		
		
	}
	
	
	public static synchronized String createVM(String name, MultipartFile json){
		try {
			File f = new File("file.json");
			f.deleteOnExit();
			File newFile = new File("file.json");
			newFile.createNewFile();
			FileCopyUtils.copy(json.getBytes(), newFile);
			SshConnectionFactory.craeteContainerScript(name);
			Session session = SshConnectionFactory.getSeesion("ubuntu", "208.55.220.5", "iU5ffjnrp");
			String command = "rm -f plan.sh";
			SshConnectionFactory.runCommand(session, command);
			SshConnectionFactory.copyFile(session, "plan.sh", "/home/ubuntu/Docker-Plan/");
			command = "cd /home/ubuntu/Docker-Plan/" +" && dos2unix plan.sh";
			SshConnectionFactory.runCommand(session, command);
			SshConnectionFactory.runCommand(session, " cd /home/ubuntu/Docker-Plan/ &&  bash LaunchPlan.sh " +name);
			String ip = SshConnectionFactory.readLine(session,"/home/ubuntu/Docker-Plan/NewVMIP.txt");
			Database.getVmList().put(name, ip);
			session.disconnect();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "IP";
	}
	
	
	
	public static synchronized void createImage(String name, MultipartFile json){
		
		try {
			File f = new File("image.json");
			f.deleteOnExit();
			File newFile = new File("image.json");
			newFile.createNewFile();
			FileCopyUtils.copy(json.getBytes(), newFile);
			List<String> script = SshConnectionFactory.craeteImageScript(name);
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
			e.printStackTrace();
		}
		
	}
	
	
	
    
	
	private static byte[] getPrivateKeyAsByteStream() {
	    // TODO Auto-generated method stub
	    final File privateKeyLocation = new File(PRIVATE_KEY_LOCATION);
	    InputStream is = null;
	    try {
	        is = new FileInputStream(privateKeyLocation);
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    long length = privateKeyLocation.length();
	    if (length > Integer.MAX_VALUE) {
	        try {
	            throw new IOException(
	                    "File to process is too big to process in this example.");
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	 
	    final byte[] bytes = new byte[(int) length];
	 
	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    try {
	        while ((offset < bytes.length)
	                && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
	 
	            offset += numRead;
	 
	        }
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	 
	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        try {
	            throw new IOException("Could not completely read file "
	                    + privateKeyLocation.getName());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	 
	    try {
	        is.close();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return bytes;
	 
	}



	

	
}
