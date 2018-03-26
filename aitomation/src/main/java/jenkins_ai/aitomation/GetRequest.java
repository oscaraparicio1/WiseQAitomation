package jenkins_ai.aitomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequest {

	public String get(String url) throws IOException {
		URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setRequestMethod("GET");
        return this.read(connection.getInputStream());
	}
	
	private String read(InputStream input) throws IOException {
		BufferedReader in = null;
		String inputLine;
		StringBuilder body;
		in = new BufferedReader(new InputStreamReader(input));
		body = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			body.append(inputLine);
		}
		in.close();
		return body.toString();
	}

}
