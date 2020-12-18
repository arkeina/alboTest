/*
String wsURL = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=iron%20man&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1";
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class soapIronMan {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://gateway.marvel.com/v1/public/characters?nameStartsWith=iron%20man&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1");
        System.out.println(json.toString());
        System.out.println(json.get("id"));
    }
}
