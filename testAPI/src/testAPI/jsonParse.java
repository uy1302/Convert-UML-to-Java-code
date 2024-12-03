package testAPI;
import java.util.*;

public class jsonParse {
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
}
