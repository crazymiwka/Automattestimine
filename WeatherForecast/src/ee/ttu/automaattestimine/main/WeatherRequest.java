package ee.ttu.automaattestimine.main;

import org.json.JSONObject;
import java.io.*;
import java.net.*;

public class WeatherRequest {
    public JSONObject request(String link) throws IOException {
        URL url = new URL(link);
        URLConnection con = url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();
        InputStream in = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            stringBuilder.append(line);
        }
        return new JSONObject(stringBuilder.toString());
    }
}
