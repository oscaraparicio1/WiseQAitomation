package es.sogeti.wiseqarest.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ExecuteShellCommand {

	private String path;
	
	public ExecuteShellCommand(String path) {
		this.path = path;
	}
	
	public List<String> executeCommand(String command) {
		List<String> output = new LinkedList<>();
		Process p;
		try {
			p = Runtime.getRuntime().exec(new String[]{"bash", "-c", command}, null, new File(path));
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line = "";	
			while ((line = reader.readLine())!= null) {
				output.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
