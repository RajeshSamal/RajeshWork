import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class Sftp {
	
	public static void main(String args[]){
		
		Sftp.downloadFile();
	}
	
	public static void downloadFile() {

    Session session = null;
    Channel channel = null;
    ChannelSftp channelSftp = null;
    boolean success = false;
    String SFTPWORKINGDIR = "/tmp/";
    String local = "D://temp/";

    try {
        JSch jsch = new JSch();
        session = jsch.getSession("ubuntu", "209.238.152.148",22);
        session.setPassword("vD4vtahmu");
        

        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        

        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTPWORKINGDIR);
        Vector<ChannelSftp.LsEntry> filelist = channelSftp.ls(SFTPWORKINGDIR);
        for (ChannelSftp.LsEntry entry : filelist) {
        	success = false;
        	 OutputStream output = null;
           if(!(entry.getFilename().equals("..") || entry.getFilename().equals("."))){
        	  output = new FileOutputStream(local+entry.getFilename());
           }
           else{
        	   continue;
           }
        	if(output != null){
        		channelSftp.get(entry.getFilename(),output);
                success = true;
                if (success)
                   
                output.close();
        	}

            
        
        }

        

        

    } catch (JSchException ex) {
    } catch (SftpException ex) {
    } catch (IOException ex) {
    }catch (Exception ex) {
    }
    catch (Throwable ex) {
    	System.out.println(ex);
    }finally {
        if (channelSftp.isConnected()) {
            try {
                session.disconnect();
                channel.disconnect();
                channelSftp.quit();
            } catch (Exception ioe) {
            }
        }
    }
}


}
