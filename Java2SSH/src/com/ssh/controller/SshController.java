package com.ssh.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssh.exec.ExecuteScript;


@Controller
public class SshController {
	

	@Autowired
	@Qualifier("devexec")
	private ExecuteScript devexec;
	 
	@Autowired
	@Qualifier("qaexec")
	private ExecuteScript qaexec;
	
	@Autowired
	@Qualifier("stageexec")
	private ExecuteScript stageexec;
	
	@Autowired
	@Qualifier("awsexec")
	private ExecuteScript awsexec;
	
	@Resource(name="stageCommands")
	private List<String> stageCommands;
	
	@Resource(name="stageHosts")
	private List<String> stageHosts;
	
	@Resource(name="stagePasswords")
	private List<String> stagePasswords;
	
	private @Value("${stage.host}") String stageSingleHost;
	
	
	
	
		@RequestMapping("/dev")
		public @ResponseBody String devExec(@RequestParam("type") String type) throws Throwable {
			String hostType= null;
			if("single".equals(type)){
				hostType = "Single Deploymnet";
			}
			else if("multiple".equals(type)){
				hostType = "Multiple Deployment";
			}
			String str = devexec.exec();
			if(str.equals("exit-status: 0")){
				String str1="<p>Application Name: Plan</p>";
				String str2="<p>Deployment Type: "+ hostType+"</p>";
				String str3="<p>VM IP Address: "+ devexec.getHost()+"</p>";
				return str1+str2+str3;
			}
			else{
				return "<p>Dev Containe Creation Failed</p>";
			}
			
		}
		
		@RequestMapping("/qa")
		public  @ResponseBody String qaExec(@RequestParam("type") String type) throws Throwable {
			String hostType= null;
			if("single".equals(type)){
				hostType = "Single Deploymnet";
			}
			else if("multiple".equals(type)){
				hostType = "Multiple Deployment";
			}
			String str = qaexec.exec();
			if(str.equals("exit-status: 0")){
				String str1="<p>Application Name: Plan</p>";
				String str2="<p>Deployment Type: "+ hostType+"</p>";
				String str3="<p>VM IP Address: "+ qaexec.getHost()+"</p>";
				return str1+str2+str3;
			}
			else{
				return "<p>QA Container Creation Failed</p>";
			}
		}
		
		@RequestMapping("/stage")
		public  @ResponseBody String stageExec(@RequestParam("type") String type) throws Throwable {
			String hostType= null;
			String status = null;
			if("single".equals(type)){
				hostType = "Single Deploymnet";
				stageexec.setHost(stageSingleHost);
				status = stageexec.exec();
			}
			else if("multiple".equals(type)){
				hostType = "Multiple Deployment";
				for(int i=0; i<5; i++){
					stageexec.setCommand(stageCommands.get(i));
					stageexec.setHost(stageHosts.get(i));
					stageexec.setPassword(stagePasswords.get(i));
					status = stageexec.exec();
					if(!status.equals("exit-status: 0")){
						break;
					}
				}
			}
			
			if(status.equals("exit-status: 0")){
				String str1="<p>Application Name: Plan</p>";
				String str2="<p>Deployment Type: "+ hostType+"</p>";
				String str3="<p>VM IP Address: "+ stageexec.getHost()+"</p>";
				return str1+str2+str3;
			}
			else{
				return "<p>Staging Container Creation Failed</p>";
			}
		}
		
		@RequestMapping("/aws")
		public  @ResponseBody String awsExec(@RequestParam("type") String type) throws Throwable {
			String hostType= null;
			if("single".equals(type)){
				hostType = "Single Deploymnet";
			}
			else if("multiple".equals(type)){
				hostType = "Multiple Deployment";
			}
			String str = awsexec.exec();
			if(str.equals("exit-status: 0")){
				String str1="<p>Application Name: Plan</p>";
				String str2="<p>Deployment Type: "+ hostType+"</p>";
				String str3="<p>VM IP Address: "+ awsexec.getHost()+"</p>";
				return str1+str2+str3;
			}
			else{
				return "<p>AWS Container Creation Failed</p>";
			}
		}

		public ExecuteScript getDevexec() {
			return devexec;
		}

		public void setDevexec(ExecuteScript devexec) {
			this.devexec = devexec;
		}

		public ExecuteScript getQaexec() {
			return qaexec;
		}

		public void setQaexec(ExecuteScript qaexec) {
			this.qaexec = qaexec;
		}

		public ExecuteScript getStageexec() {
			return stageexec;
		}

		public void setStageexec(ExecuteScript stageexec) {
			this.stageexec = stageexec;
		}

		public ExecuteScript getAwsexec() {
			return awsexec;
		}

		public void setAwsexec(ExecuteScript awsexec) {
			this.awsexec = awsexec;
		}

		public List<String> getStageCommands() {
			return stageCommands;
		}

		public void setStageCommands(List<String> stageCommands) {
			this.stageCommands = stageCommands;
		}

		public List<String> getStageHosts() {
			return stageHosts;
		}

		public void setStageHosts(List<String> stageHosts) {
			this.stageHosts = stageHosts;
		}

		public List<String> getStagePasswords() {
			return stagePasswords;
		}

		public void setStagePasswords(List<String> stagePasswords) {
			this.stagePasswords = stagePasswords;
		}
		
}

