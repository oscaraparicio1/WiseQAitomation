package jenkins_ai.aitomation;

import java.io.IOException;
import java.util.Base64;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main(String[] args) {
    	
    	int port = 8010;
    	String password = "Sogeti@4";
    	String username = "sogetilabs";
    	String host = "192.168.50.102";
    	String jobName = "WiseQAController";
    	String auth = username + ":" + password;
		String jenkinsUrl = "http://" + host + ":" + port + "/";
		String encoding = Base64.getEncoder().encodeToString(auth.getBytes());
    	
			
		try {
			String buildUrl = jenkinsUrl + "job/" + jobName + "/build";
			PostRequest postRequest = new PostRequest();
			String content;
			content = postRequest.post(buildUrl, null, encoding);			
			System.out.println(content.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    }
}
