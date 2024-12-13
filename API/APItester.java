
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class APItester {
	 public static void main(String[] args) {
			String apiUrl = "http://127.0.0.1:8000";
			// String descriptions = "{\"public class Vehicle\" :" +
			// 						"{\"start\":\"The Vehicle initializes its engine and prepares all systems for operation.\"," +
			// 						"\"stop\": \"The Vehicle powers down its engine and deactivates non-essential systems.\","+
			// 						"\"accelerate\": \"The Vehicle increases its speed by applying more power to its drivetrain.\","+
			// 						"\"brake\": \"The Vehicle slows down or comes to a stop by applying its braking mechanism.\"}," +
			// 					"\"public class Car\" :"+
			// 						"{\"playMusic\": \"The Car's entertainment system plays music for the passengers.\","+
			// 						"\"enableCruiseControl\": \"The Car maintains a steady speed without driver input through its cruise control system.\"},"+
			// 					"\"public class ElectricCar\" :"+
			// 					" {\"chargeBattery\": \"The ElectricCar connects to a charging station to replenish its battery.\","+
			// 					" \"regeneratePower\": \"The ElectricCar converts kinetic energy into electrical energy during braking to recharge the battery.\"}}";
			String descriptions = readFiletoJson("test.txt");
			String classes =  "{" + //
			"    \"public class Vehicle\": {" + //
			"        \"attributes\": {" + //
			"            \"make\": \"private String make\"," + //
			"            \"model\": \"private String model\"," + //
			"            \"year\": \"private int year\"," + //
			"            \"speed\": \"private double speed\"" + //
			"        }," + //
			"        \"methods\": {" + //
			"            \"start\": \"void start()\"," + //
			"            \"stop\": \"void stop()\"," + //
			"            \"accelerate\": \"void accelerate(double increment)\"," + //
			"            \"brake\": \"void brake(double decrement)\"" + //
			"        }" + //
			"    }," + //
			"    \"public class Car\": {" + //
			"        \"attributes\": {" + //
			"            \"entertainmentSystem\": \"private String entertainmentSystem\"," + //
			"            \"seatingCapacity\": \"private int seatingCapacity\"" + //
			"        }," + //
			"        \"methods\": {" + //
			"            \"playMusic\": \"void playMusic(String songName)\"," + //
			"            \"enableCruiseControl\": \"void enableCruiseControl(double speed)\"" + //
			"        }" + //
			"    }," + //
			"    \"public class ElectricCar\": {" + //
			"        \"attributes\": {" + //
			"            \"batteryCapacity\": \"private double batteryCapacity\"," + //
			"            \"currentCharge\": \"private double currentCharge\"," + //
			"            \"chargingPortType\": \"private String chargingPortType\"" + //
			"        }," + //
			"        \"methods\": {" + //
			"            \"chargeBattery\": \"void chargeBattery(double hours)\"," + //
			"            \"regeneratePower\": \"void regeneratePower(double energy)\"" + //
			"        }" + //
			"    }" + //
			"}";
			 System.out.println(descriptions);
			 System.out.println(classes);
			postAPI(descriptions, classes);

            try {
                ProcessBuilder pb = new ProcessBuilder("python", "test.py");
                Process process = pb.start();
    
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
	        String getUrl = apiUrl + "/get_code";
	        
	        try {
	            // Create a URL object
	            URL url = new URL(getUrl);
	            
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
	                List<String> res = parse(response.toString());
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

	public static String readFiletoJson(String filename){
		String descriptionString = readFileAsString("test.txt");
			String[] lines = descriptionString.split("\n");
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{");

			String currentClass = null;
			boolean isFirstClass = true;

			for (String line : lines) {
				line = line.trim();

				if (line.startsWith("public class")) {
					// Close the previous class object
					if (currentClass != null) {
						jsonBuilder.append("},");
					}

					// Start a new class object
					currentClass = line;
					if (!isFirstClass) {
						jsonBuilder.append("\n");
					}
					isFirstClass = false;

					jsonBuilder.append("\"").append(currentClass).append("\": {");
				} else if (!line.isEmpty()) {
					String[] parts = line.split(":");
					if (parts.length == 2) {
						String methodName = parts[0].trim();
						String description = parts[1].trim();

						jsonBuilder.append("\n    \"")
								.append(methodName).append("\": \"")
								.append(description).append("\",");
					}
				}
			}

			int lastCommaIndex = jsonBuilder.lastIndexOf(",");
			if (lastCommaIndex != -1) {
				jsonBuilder.deleteCharAt(lastCommaIndex);
			}

			jsonBuilder.append("\n  }\n}");
			String jsonString = jsonBuilder.toString();
			jsonString = jsonString.replaceAll(",(\\s*})", "$1"); // Removes commas before '}'
        	jsonString = jsonString.replaceAll(",(\\s*])", "$1"); // Handles arrays if present in the future
			return jsonString;
	}

	public static void postAPI(String descriptions, String classes){
		String apiUrl = "http://127.0.0.1:8000";
		String jsonDescriptions = "{\"descriptions\" :" + descriptions + "," +
								"\"classes\" :" + classes + "}";
		// System.out.println(jsonDescriptions);
		try {
			URL postUrl = new URL(apiUrl+"/send_descriptions");

			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();


			connection.setRequestMethod("POST");

			connection.setDoOutput(true);
			connection.setDoInput(true);

			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonDescriptions.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = connection.getResponseCode();
			// System.out.println("Response Code: " + responseCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> parse(String jsonString) {
        jsonString = jsonString.substring(jsonString.indexOf("[") + 1, jsonString.lastIndexOf("]"));

        String[] entries = jsonString.split("\\},\\{");

        List<String> contents = new ArrayList<>();
        for (String entry : entries) {
            int startIndex = entry.indexOf("\"Content\":\"") + 11;
            int endIndex = entry.lastIndexOf("\"");
            String content = entry.substring(startIndex, endIndex).replace("\\n", "\n").replace("\\t", "\t").replace("\\\"", "\"");
            contents.add(content);
        }

        return contents;
    }

	public static String readFileAsString(String filePath) {
        try {
            // Read all lines and join them with newline characters
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return String.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
