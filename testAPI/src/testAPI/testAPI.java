package testAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class testAPI {
	 public static void main(String[] args) {
	        // Define the server URL
	        String apiUrl = "http://127.0.0.1:8000/get_data";
	        
	        try {
	            // Create a URL object
	            URL url = new URL(apiUrl);
	            
	            // Open a connection to the URL
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            
	            // Set the request method to GET
	            connection.setRequestMethod("GET");
	            
	            // Get the response code to check if the request was successful
	            int responseCode = connection.getResponseCode();
	            System.out.println("Response Code: " + responseCode);

	            // Read the response (if successful, status code is 200)
	            if (responseCode == HttpURLConnection.HTTP_OK) { // success
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String inputLine;
	                StringBuilder response = new StringBuilder();
	                
	                // Read the response line by line
	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                    System.out.println(inputLine);
	                }
	                in.close();
	                
	                // Print the response body
	                System.out.println("Response Body: " + response.toString());
	                List<String> res = jsonParse.parse(response.toString());
	                for (String content: res) {
	                	System.out.println(content+"\n");
	                }
	            } else {
	                System.out.println("GET request failed. Response Code: " + responseCode);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
