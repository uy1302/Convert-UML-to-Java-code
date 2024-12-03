package testAPI;

import java.io.*;

public class callPython {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "myscript.py", "World");
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Outputs: Hello, World!
            }
            System.out.println("ABC");
        } catch (Exception e) {
        	System.out.println("abc");
            e.printStackTrace();
        }
    }
}
