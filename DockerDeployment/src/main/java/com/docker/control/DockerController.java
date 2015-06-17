package com.docker.control;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.docker.ssh.Database;
import com.docker.ssh.SshConnectionFactory;

@Controller
@EnableWebMvc
public class DockerController {
	
	
	
	@RequestMapping(value="createImage/{appName}", method=RequestMethod.POST)
	@ResponseBody
	public void createImage(@PathVariable String appName, @RequestParam(value="file", required=true) MultipartFile file) throws Throwable {
		
		
		
		SshConnectionFactory.createImage(appName, file);
		Database.getAppList().add(appName);
		
	}
	
	
	
	@RequestMapping(value="/deploy/{appName}/{topology}", method=RequestMethod.POST)
	@ResponseBody
	public String deploy(@PathVariable String appName, @PathVariable String topology,@RequestParam("file") MultipartFile file) throws Throwable {
		
		String IP = SshConnectionFactory.createVM(appName,topology, file);
		return "";
		
	}
	
	@RequestMapping(value="/getApp", method=RequestMethod.GET)
	
	@ResponseBody
	public List<String> getAppList() throws Throwable {
		
		return Database.getAppList();
		
		
		
	}
	
@RequestMapping(value="/getVM", method=RequestMethod.GET)
	
	@ResponseBody
	public Map<String,String> getMList() throws Throwable {
	
	
		return Database.getVmList();
		
		
		
	}

}
