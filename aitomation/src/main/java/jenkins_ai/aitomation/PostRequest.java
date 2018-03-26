package jenkins_ai.aitomation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequest {
	
	HttpURLConnection connection;
	
	public String post(String postUrl, String data, String encoding) throws IOException {
        URL url = new URL(postUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        this.sendData(connection, data);
        return this.read(connection.getInputStream());
    }
	
	protected void sendData(HttpURLConnection connection, String data) throws IOException {
        DataOutputStream writer = null;
        writer = new DataOutputStream(connection.getOutputStream());
        writer.writeBytes(data);
        writer.flush();
        writer.close();
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
