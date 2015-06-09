package com.ntt.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.ntt.docker.Deployment;
import com.ntt.docker.Node;

public class Main {

	private static Node getNodeByOrder(int order, List<Node> nodeList) {
		Node node = null;
		for (Node nd : nodeList) {
			if (nd.getOrder() == order) {
				node = nd;
				return nd;
			}
		}
		return node;
	}

	private static Node getNodeByType(String type, List<Node> nodeList) {
		Node node = null;
		for (Node nd : nodeList) {
			if (nd.getType().equalsIgnoreCase(type)) {
				node = nd;
				return nd;
			}
		}
		return node;
	}

	private static String restartAlways() {
		return Constants.restartCommand;
	}

	private static String addNameToScript(String name) {
		return Constants.nameCommand + name + " ";
	}

	private static StringBuffer addOption(List<String> options) {
		StringBuffer stbf = new StringBuffer();
		for (String st : options) {
			stbf.append("-e " + st + " ");
		}
		return stbf;
	}

	private static String addOptionWQ(String option, String key, String value) {
		return "-" + option + " " + key + "=" + value + " ";
	}

	private static StringBuffer addPort(List<String> ports) {
		StringBuffer stbf = new StringBuffer();
		for (String st : ports) {
			stbf.append("-p " + st + " ");
		}
		return stbf;
	}

	private static String[] prepareScript(Deployment deploy, Node node, String topologyName) {
		String dType = deploy.getType();
		if (dType.equalsIgnoreCase(Constants.single)) {
			dType = Constants.singleHost;
		} else {
			dType = Constants.multipleHost;
		}
		String[] string = new String[2];
		int k = 0;
		if (node.getNoOfInstances() >= 1) {
			k = node.getNoOfInstances() - 1;
		}

		for (int i = 0; i <= k; i++) {
			StringBuffer script = new StringBuffer(Constants.dockerCommand);
			if (node.getPort() != null && node.getPort().size() > 0) {
				script.append(addPort(node.getPort()));
			}
			if ((Constants.isRestart).equalsIgnoreCase(node.getRestart())) {
				script.append(restartAlways());
			}
			if (node.getName() != null) {
				if (node.getNoOfInstances() > 0) {
					script.append(addNameToScript(node.getName()
							+ Integer.toString(i + 1)));
				} else {
					script.append(addNameToScript(node.getName()));
				}

			}

			script.append(addOptionWQ("e", "ENV", deploy.getEnvironment()));
			script.append(addOptionWQ("e", "Deployment", dType));

			if (node.getOption() != null && node.getOption().size() > 0) {
				script.append(addOption(node.getOption()));
			}

			if (node.getLink() != null && node.getLink().size() > 0) {
				List<String> links = node.getLink();
				for (String link : links) {
					Node linkNode = getNodeByType(link, deploy.getNode());
					if (linkNode.getNoOfInstances() > 0) {
						for (int j = 1; j <= linkNode.getNoOfInstances(); j++) {
							script.append(addLink(linkNode.getName()
									+ Integer.toString(j)));
						}

					} else {
						script.append(addLink(linkNode.getName()));
					}

				}
			}

			script = script.append(TopologyImageStore.getImageStore().get(topologyName+node.getType()));

			string[i] = new String(script);
		}
		return string;
	}

	private static String stopScript() {
		return Constants.stopCommand;
	}

	private static String sleepScript(int time) {
		return Constants.sleepCommand + Integer.toString(time) + "s";
	}

	private static String addLink(String name) {
		return Constants.linkCommand + name + ":" + name + " ";
	}

	private static StringBuffer singleHost(StringBuffer sbf, Deployment deploy,String topologyName) {
		int length = deploy.getNode().size();
		sbf.append(Constants.logincommand);
		sbf.append(System.getProperty("line.separator"));
		if (Constants.stopAll.equalsIgnoreCase(deploy.getState())) {
			sbf.append(stopScript());
			sbf.append(System.getProperty("line.separator"));
		}
		sbf.append(System.getProperty("line.separator"));
		for (int i = 0; i < length; i++) {
			Node node = getNodeByOrder(i + 1, deploy.getNode());
			if (node.getSleep() > 0) {
				sbf.append(sleepScript(node.getSleep()));
				sbf.append(System.getProperty("line.separator"));
			}
			String[] scripts = prepareScript(deploy, node,topologyName);
			for (String s : scripts) {
				if (s != null) {
					sbf.append(s);
					sbf.append(System.getProperty("line.separator"));
				}

			}

		}
		return sbf;
	}
	
	
	public static void prepareContainerScript(String topologyName){

		Gson gson = new Gson();
		StringBuffer sbf = new StringBuffer();

		try {

			FileReader fileReader = new FileReader("file.json");

			BufferedReader buffered = new BufferedReader(fileReader);

			Deployment deploy = gson.fromJson(buffered, Deployment.class);
			buffered.close();
			fileReader.close();
			if (Constants.single.equalsIgnoreCase(deploy.getType())) {
				sbf = singleHost(sbf, deploy,topologyName);
			}

			FileWriter fileWriter = new FileWriter("plan.sh");
			BufferedWriter bufferWrt = new BufferedWriter(fileWriter);
			bufferWrt.write(new String(sbf));
			bufferWrt.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	
	}

	public static void main(String args[]) {
		Gson gson = new Gson();
		StringBuffer sbf = new StringBuffer();

		try {

			FileReader fileReader = new FileReader("file.json");

			BufferedReader buffered = new BufferedReader(fileReader);

			Deployment deploy = gson.fromJson(buffered, Deployment.class);
			buffered.close();
			fileReader.close();
			if (Constants.single.equalsIgnoreCase(deploy.getType())) {
				sbf = singleHost(sbf, deploy,args[0]);
			}

			FileWriter fileWriter = new FileWriter("plan.sh");
			BufferedWriter bufferWrt = new BufferedWriter(fileWriter);
			bufferWrt.write(new String(sbf));
			bufferWrt.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

}
