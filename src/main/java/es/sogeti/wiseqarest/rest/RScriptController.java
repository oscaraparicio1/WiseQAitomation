package es.sogeti.wiseqarest.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.sogeti.wiseqarest.configuration.Environment;
import es.sogeti.wiseqarest.systemProperties.Log;
import es.sogeti.wiseqarest.utilities.ExecuteShellCommand;
import es.sogeti.wiseqarest.utilities.StringUtilities;

@RestController
@RequestMapping("/rest")
public class RScriptController {

    @PostMapping("/runscript")
    public String rScriptExecutor(@RequestParam("name") String name) {
        ExecuteShellCommand obj = new ExecuteShellCommand(Environment.R_WISEQA_FOLDER);
        List<String> output = obj.executeCommand("Rscript " + name + ".R");
        return StringUtilities.parseListToString(output);
    }
    
    @PostMapping("/uploadJson")
    public Boolean uploadJson(@RequestParam("file") MultipartFile file) {
    	Boolean uploaded = false;
    	System.out.println("File name: " + file.getOriginalFilename() + " - " + file.getSize());
    	File newFile = new File(Environment.R_DATA_FOLDER + file.getOriginalFilename());
    	try {
    		file.transferTo(newFile);
    		uploaded = true;
    		Log.info(RScriptController.class, "File " + file.getOriginalFilename() + " has been uploaded correctly");
    	} catch(IllegalStateException | IOException e) {
    		Log.error(e);
    	}
    	return uploaded;
    }
}
