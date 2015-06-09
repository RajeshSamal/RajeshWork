package com.ntt.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ntt.docker.App;
import com.ntt.docker.Doc;
import com.ntt.docker.Image;
import com.ntt.docker.ImageConfig;
import com.ntt.docker.ImageInterface;
import com.ntt.docker.Ports;
import com.ntt.docker.PostScript;

public class PrepareImage {

	private static String prepareWget(String source, String downlaodLocation) {
		String s = "";
		if (downlaodLocation != null && downlaodLocation != "") {
			s = "wget " + downlaodLocation + " ";
		}
		if (source != null && source != "" && !source.isEmpty()) {
			s = s + "-O " + source + getSourceFromLocation(downlaodLocation);
		}
		return s;
	}

	private static String prepareDockerFileScript(String source,
			String destination) {
		String s = "";

		if ((source != null && source != "")
				&& (destination != null && destination != "")) {
			s = "echo \"ADD " + source + " " + destination + "\""
					+ ">>Dockerfile";
		}
		return s;
	}

	private static String getSourceFromLocation(String downloadLocation) {
		String s;
		int i = downloadLocation.lastIndexOf("/");
		s = downloadLocation.substring(i + 1);
		return s;
	}

	private static StringBuffer prepareImageNode(ImageInterface node) {
		StringBuffer stbf = new StringBuffer();
		String s;

		if (node.getDownloadLocation() != null
				&& node.getDownloadLocation() != "") {
			s = prepareWget(node.getSource(), node.getDownloadLocation());
			if (s != null && s != "") {
				stbf.append(s);
				stbf.append(System.getProperty("line.separator"));
			}
		}
		if (stbf.length() > 3) {
			String source = getSourceFromLocation(node.getDownloadLocation());
			if (node.getSource() != null && node.getSource() != "") {
				source = node.getSource() + source;
			}
			String destination = node.getDesitnation();
			if (destination == null || destination == "") {
				destination = "/";
			}
			String ds = prepareDockerFileScript(source, destination);
			if (ds != null && ds != "") {
				stbf.append(ds);
				stbf.append(System.getProperty("line.separator"));
			}
		}

		return stbf;
	}

	private static StringBuffer prepareBeginScript(Image image) {
		StringBuffer sbf = new StringBuffer();

		sbf.append("echo \"FROM dockerimage.nttclouds.co:8080/" + image.getImageName() + "\" > Dockerfile");
		sbf.append(System.getProperty("line.separator"));

		sbf.append("echo \"MAINTAINER NTT CPDC <nttcpdc@nttdata.com>\" >>Dockerfile");
		sbf.append(System.getProperty("line.separator"));

		sbf.append("echo \"RUN mkdir /root/dev/\" >>Dockerfile");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("echo \"RUN mkdir /root/qa/\" >>Dockerfile");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("echo \"RUN mkdir /root/staging/\" >>Dockerfile");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("echo \"RUN mkdir /root/prod/\" >>Dockerfile");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("mkdir qa/");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("mkdir dev/");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("mkdir staging/");
		sbf.append(System.getProperty("line.separator"));
		sbf.append("mkdir prod/");
		sbf.append(System.getProperty("line.separator"));

		return sbf;

	}
	public static synchronized List prepareScript(String topologyName){

		List<String> shnames = new ArrayList<String>();
		Gson gson = new Gson();

		try {

			FileReader fileReader = new FileReader("image.json");

			BufferedReader buffered = new BufferedReader(fileReader);

			com.ntt.docker.Image[] image = gson.fromJson(buffered,
					com.ntt.docker.Image[].class);
			buffered.close();
			fileReader.close();
			for (int i = 0; i < image.length; i++) {
				TopologyImageStore.getImageStore().put(topologyName+image[i].getType(), Constants.dockerRegistry+topologyName+image[i].getImageName());
				StringBuffer script = new StringBuffer();
				script.append(prepareBeginScript(image[i]));
				List<ImageConfig> imgConfig = image[i].getImageConfig();
				for (int j = 0; j < imgConfig.size(); j++) {
					StringBuffer imageNodeScript = prepareImageNode(imgConfig
							.get(j));
					if (imageNodeScript.length() > 1) {
						script.append(imageNodeScript);
					}
					if (imgConfig.get(j).getFile().size() > 0) {
						for (Doc imagefile : imgConfig.get(j).getFile()) {
							script.append(prepareImageNode(imagefile));
						}
					}
				}
				List<App> app = image[i].getApp();
				for (int k = 0; k < app.size(); k++) {
					StringBuffer imageNodeScript = prepareImageNode(app.get(k));
					if (imageNodeScript.length() > 1) {
						script.append(imageNodeScript);
					}
					if (app.get(k).getFile().size() > 0) {
						for (Doc imagefile : app.get(k).getFile()) {
							script.append(prepareImageNode(imagefile));
						}
					}
				}
				List<Ports> ports = image[i].getPorts();
				for (int m = 0; m < ports.size(); m++) {
					List<String> portList = ports.get(m).getPort();
					for (String port : portList) {
						String portScript = "echo \"EXPOSE " + port
								+ "\">> Dockerfile";
						script.append(portScript);
						script.append(System.getProperty("line.separator"));
					}
				}
				List<String> misc = image[i].getMisc();
				for (int n = 0; n < misc.size(); n++) {
					String command = misc.get(n);
					
						String portScript = "echo \"" + command
								+ "\">> Dockerfile";
						script.append(portScript);
						script.append(System.getProperty("line.separator"));
					
				}
				List<PostScript> postScript = image[i].getPostScripts();
				for (int l = 0; l < postScript.size(); l++) {
					StringBuffer imageNodeScript = prepareImageNode(postScript
							.get(l));
					if (imageNodeScript.length() > 1) {
						script.append(imageNodeScript);
					}
					if (postScript.get(l).getFile().size() > 0) {
						for (Doc imagefile : postScript.get(l).getFile()) {
							script.append(prepareImageNode(imagefile));
						}
					}

					if (postScript.get(l).getFile().size() > 0) {
						for (Doc doc : postScript.get(l).getFile()) {
							String ps = "echo \"RUN chmod +x ";
							ps = ps
									+ doc.getDesitnation()
									+ getSourceFromLocation(doc
											.getDownloadLocation())
									+ "\">>Dockerfile";
							String ps1 = "echo \"CMD ";
							ps1 = ps1
									+ doc.getDesitnation()
									+ getSourceFromLocation(doc
											.getDownloadLocation())
									+ "\">>Dockerfile";
							script.append(ps);
							script.append(System.getProperty("line.separator"));
							script.append(ps1);
							script.append(System.getProperty("line.separator"));
						}
					}

				}

				FileWriter fileWriter = new FileWriter(image[i].getImageName()
						+ ".sh");
				shnames.add(image[i].getImageName()
						+ ".sh");
				BufferedWriter bufferWrt = new BufferedWriter(fileWriter);
				bufferWrt.write(new String(script));
				bufferWrt.close();
				fileWriter.close();
				

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		return shnames;
	
	}

	public static void main(String[] args) {
		
		prepareScript(args[0]);
		
	}

}
