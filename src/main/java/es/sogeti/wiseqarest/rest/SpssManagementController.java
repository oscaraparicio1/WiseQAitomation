package es.sogeti.wiseqarest.rest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.sogeti.wiseqarest.configuration.Environment;
import es.sogeti.wiseqarest.managment.Metric;
import es.sogeti.wiseqarest.services.MetricService;
import es.sogeti.wiseqarest.systemProperties.Log;
import es.sogeti.wiseqarest.utilities.ExecuteShellCommand;
import es.sogeti.wiseqarest.utilities.StringUtilities;

@RestController
@RequestMapping("/rest")
public class SpssManagementController {
	
	@Autowired
	private MetricService metricService;
	
	@GetMapping("/execute")
	public void executeMetric(@RequestParam("metricIds[]") String[] metricIds) {
		List<Metric> metrics = Arrays.stream(metricIds).map(mId -> metricService.getMetric(mId)).collect(Collectors.toList());
		
		ExecuteShellCommand shell = new ExecuteShellCommand(Environment.MODELER_BATCH);
		for(Metric metric : metrics) {
			String clemb = "./clemb -server -hostname localhost -port 28056 -username wiseqa -password wiseqa -stream data/" 
					+ metric.getStrName() + " -P:databasenode.password='1q2w3e4r' -execute";
			shell.executeCommand(clemb);
		}
	}
	
	@PostMapping("/uploadMetric")
	public Boolean uploadMetric(@RequestParam("file") MultipartFile file) {
		Boolean uploaded = false;
		File newFile = new File(Environment.STREAM_DATA_FOLDER + file.getOriginalFilename());
		try {
			file.transferTo(newFile);
			uploaded = true;
			Log.info(SpssManagementController.class, "File " + file.getOriginalFilename() + " has been uploaded correctly");
		} catch (IllegalStateException | IOException e) {
			Log.error(e);
		}
		return uploaded;
	}
	
	@GetMapping("/showAllStreams")
	public String showAllStreams() {
		ExecuteShellCommand obj = new ExecuteShellCommand(Environment.STREAM_DATA_FOLDER);
		List<String> output = obj.executeCommand("ls -la");
		return StringUtilities.parseListToString(output);
	}
	
	@PostMapping("/removeStream")
	public Boolean removeStream(@RequestParam("strName") String strName) {
		ExecuteShellCommand obj = new ExecuteShellCommand(Environment.STREAM_DATA_FOLDER);
		obj.executeCommand("rm " + strName);
		// return true if file exists or false if not
		Boolean found = Boolean.valueOf(obj.executeCommand("test -f " + strName + " && echo \"true\" || echo \"false\"").get(0));
		// that's because we return if the file has been erased or not
		Log.info(SpssManagementController.class, "The original file has been deleted?: " + !found);
		return !found;
	}
	
	/*
	 * options: 
	 * 		- list: you can see spss server status
	 * 		- start: start spss server
	 * 		- kill: stop spss server
	 */
	@PostMapping("/serverOption")
	public String serverOption(@RequestParam("option") String option) {
		ExecuteShellCommand obj = new ExecuteShellCommand(Environment.MODELER_SERVER);
		List<String> output = obj.executeCommand("./modelersrv.sh " + option);
		return StringUtilities.parseListToString(output);
	}
	
	/*
	 * return true if spss-server is active and false if it's stopped
	 */
	@GetMapping("/checkServerStatus")
	public Boolean checkServerStatus() {
		System.out.println("We are checking the server status\n");
		ExecuteShellCommand obj = new ExecuteShellCommand(Environment.MODELER_SERVER);
		List<String> output = obj.executeCommand("./modelersrv.sh list");
		return output.size() > 1;
	}
	
	@PostMapping("/checkStream")
	public Boolean checkStream(@RequestParam("strName") String strName) {
		ExecuteShellCommand obj = new ExecuteShellCommand(Environment.STREAM_DATA_FOLDER);
		Boolean found = Boolean.valueOf(obj.executeCommand("test -f " + strName + " && echo \"true\" || echo \"false\"").get(0));
		Log.info(SpssManagementController.class, "The file " + strName + " exists?: "+found);
		return found;
	}
	
	@PostMapping("/replaceStream")
	public Boolean replaceStream(@RequestParam("strName") String original, @RequestParam("file") MultipartFile file) {
		Boolean removed = false;
		Boolean uploaded = false;
		
		File newFile = new File(Environment.STREAM_DATA_FOLDER + file.getOriginalFilename());
		try {
			file.transferTo(newFile);
			uploaded = true;
			Log.info(SpssManagementController.class, "File " + file.getOriginalFilename() + " has been uploaded correctly");			
		} catch (IllegalStateException | IOException e) {
			Log.error(e);
		}
		if(uploaded) {
			ExecuteShellCommand obj = new ExecuteShellCommand(Environment.STREAM_DATA_FOLDER);
			obj.executeCommand("rm " + original);
			// return true if file exists or false if not
			removed = Boolean.valueOf(obj.executeCommand("test -f " + original + " && echo \"true\" || echo \"false\"").get(0));
			// that's because we return if the file has been erased or not
			removed = !removed;
			Log.info(SpssManagementController.class, "The file " + original + " has been deleted?: " + removed);
		}
		return (removed && uploaded);
	}
}
