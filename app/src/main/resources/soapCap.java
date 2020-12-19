package albopackage;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class soapCap {

    public String soapCap() throws MalformedURLException, IOException {

        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        //String wsURL = "<Endpoint of the webservice to be consumed>";
        String wsURL = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=Captain%20America&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1";
        //String wsURL = "https://gateway.marvel.com:443/v1/public/characters?apikey=ac50fd1d19c4f4e2727b3444951a8573";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput = "entire SOAP Request";

        byte[] buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction = "<SOAP action of the webservice to be consumed>";
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
                String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("GET");
        httpConn.setDoOutput(true);
        //httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        //Write the content of the request to the outputstream of the HTTP Connection.
        out.write(b);
        out.close();
        //Ready with sending the request.

        //Read the response.
        InputStreamReader isr = null;
        if (httpConn.getResponseCode() == 200) {
            isr = new InputStreamReader(httpConn.getInputStream());
        } else {
            isr = new InputStreamReader(httpConn.getErrorStream());
        }

        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        //Document document = parseXmlFile(outputString); // Write a separate method to parse the xml input.
        //NodeList nodeLst = document.getElementsByTagName("<TagName of the element to be retrieved>");
        //String elementValue = nodeLst.item(0).getTextContent();
        //System.out.println(elementValue);

        //Write the SOAP message formatted to the console.
        //String formattedSOAPResponse = formatXML(outputString); // Write a separate method to format the XML input.
        //System.out.println(formattedSOAPResponse);
        //return elementValue;
        return outputString;
    }
}
