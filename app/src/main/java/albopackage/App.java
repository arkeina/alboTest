/*
 * Esta clase manda a llamar a los metodos para traer la informacion de Iron Man y Capitan America, trae a los creadores y el comic modificado más reciente de ese heroe por medio de ENDPOINTS dados por la API de marvel, asi mismo manda a llamar los datos de los heroes que salen en ese comic relacionados.
 */
package albopackage;

//Importe de librerias de java
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
//Importe de librerias de org
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
//Importe de librerias de entrada de datos de java
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
//Importe de librerias declaradas en la dependencia del gradle
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
//Importe de librerias de coneccion BD y manejo de resultsets
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;


public class App {
    
    /**
     * Funcion generada por el gradle de manera original
     */
    public String getGreeting() {
        return "Hello World!";
    }
    
    /**
     * Funcion de llamada para construir el JSON mediante la lectura del texto de la URL y el Buffered Reader
     * @param Reader rd
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    /**
     * Funcion de llamado a para leer un JSON desde una URL lo convierte en JSONObject para manejo
     * @param String url
     */
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

    /**
     * Funcion de llamado a ENDPOINT que regresa el JSON con los creadores por el ID de Iron Man ID 1009368
     */
    public static boolean callIronManCreators() throws MalformedURLException, IOException {
        //Se obtiene la respuesta JSON de la url para los datos
        JSONObject jsonIM = readJsonFromUrl("https://gateway.marvel.com/v1/public/characters/1009368/comics?orderBy=-modified&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1&limit=1");
        //ENDPOINT que da el id del HEROE = https://gateway.marvel.com/v1/public/characters?nameStartsWith=iron%20man&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1
        //ID iron man = 1009368
        //int loopComicsIM = jsonIM.getJSONObject("data").getInt("total");
        System.out.println("----------");
        System.out.println("ID iron man = 1009368");
        JSONArray arr = jsonIM.getJSONObject("data").getJSONArray("results");
        System.out.println("Id Comic:" + arr.getJSONObject(0).getInt("id"));
        System.out.println("----------");
        JSONArray creatorsArr = arr.getJSONObject(0).getJSONObject("creators").getJSONArray("items");
        System.out.println("Creadores");
        //for (int i = 0; i < arr.length(); i++) {
        for (int i = 0; i < creatorsArr.length(); i++) {
            //System.out.println(arr.getJSONObject(i).getString("name"));
            //System.out.println(arr.getJSONObject(i).getInt("id") + "\n");
            System.out.println("Nombre: " + creatorsArr.getJSONObject(i).getString("name"));
            System.out.println("Rol   : " + creatorsArr.getJSONObject(i).getString("role"));
        }
        callCharactersFromComic( String.valueOf( arr.getJSONObject(0).getInt("id") ) );
        System.out.println("----------");
        return false;
    }

    /**
     * Funcion de llamado a ENDPOINT que regresa el JSON con los creadores por el ID de capitan America ID 1009220
     */
    public static boolean callCapAmericaCreators() throws MalformedURLException, IOException {
        //Se obtiene la respuesta JSON de la url para los datos
        JSONObject jsonCap = readJsonFromUrl("https://gateway.marvel.com/v1/public/characters/1009220/comics?orderBy=-modified&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1&limit=1");
        //ENDPOINT que da el id del HEROE = https://gateway.marvel.com/v1/public/characters?nameStartsWith=Captain%20America&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1
        //ID capitan america = 1009220
        //ID del comic 
        //Se obtiene el total de resultados dentro del tag del JSON total
        int loopComicsCAP = jsonCap.getJSONObject("data").getInt("total");
        System.out.println("----------");
        System.out.println("ID capitan america = 1009220");
        //Se recorre el arreglo de resultados para 
        JSONArray arr = jsonCap.getJSONObject("data").getJSONArray("results");
        System.out.println("Id Comic:" + arr.getJSONObject(0).getInt("id"));
        System.out.println("----------");
        JSONArray creatorsArr = arr.getJSONObject(0).getJSONObject("creators").getJSONArray("items");
        System.out.println("Creadores");
        for (int i = 0; i < creatorsArr.length(); i++) {
            System.out.println("Nombre: " + creatorsArr.getJSONObject(i).getString("name"));
            System.out.println("Rol   : " + creatorsArr.getJSONObject(i).getString("role"));
            //System.out.println(arr.getJSONObject(0).getInt("id") + "\n");
        }
        callCharactersFromComic( String.valueOf( arr.getJSONObject(0).getInt("id") ) );
        System.out.println("----------");
        return false;
    }
    
    /**
     * Funcion de llamado a ENDPOINT que regresa el JSON con los personajes por el ID de comic
     * @parameters String comicNo
     */
    public static boolean callCharactersFromComic(String comicNo) throws MalformedURLException, IOException {
        JSONObject jsonIM = readJsonFromUrl("https://gateway.marvel.com/v1/public/comics/" + comicNo + "/characters?orderBy=name&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1");
        System.out.println("----------");
        JSONArray arr = jsonIM.getJSONObject("data").getJSONArray("results");
        //JSONArray creatorsArr = arr.getJSONObject(0).getJSONObject("creators").getJSONArray("items");
        System.out.println("Characters in comic " + comicNo);
        //for (int i = 0; i < arr.length(); i++) {
        for (int i = 0; i < arr.length(); i++) {
            //System.out.println(arr.getJSONObject(i).getString("name"));
            //System.out.println(arr.getJSONObject(i).getInt("id") + "\n");
            System.out.println("Heroe : " + arr.getJSONObject(i).getString("name"));
            JSONArray charactersArr = arr.getJSONObject(0).getJSONObject("comics").getJSONArray("items");
            System.out.println("Rol   : " + charactersArr.getJSONObject(i).getString("name"));
        }
        System.out.println("----------");
        return false;
    }
    
    private static Connection conn;

    // init
    public static void aMySQLConnection(String url, String user, String pass) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connection established.");
    }
    
    public void getHerosData() throws SQLException {
        String query = "SELECT * FROM  marvel_repo_db.heros;";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            //Print one row          
            for(int i = 1 ; i <= columnsNumber; i++){
                  System.out.print(rs.getString(i) + " ");
            }
            System.out.println();        
        }
        //return;
    }
    /**
     * Funcion principal que se ejecuta al correr el gradlew run
     */
    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException, SQLException, ClassNotFoundException{
        //System.out.println(new App().getGreeting());
        
        new App().callIronManCreators();
        TimeUnit.SECONDS.sleep(1);
        new App().callCapAmericaCreators();
        //MySQLConnection("jdbc:mysql://127.0.0.1", "root", "");
        new App().aMySQLConnection("jdbc:mysql://127.0.0.1", "root", "");
        new App().getHerosData();
    }
}
//int ironmanID = 1009368;
//int capamericaID = 1009220;
//ID de comic en que sale = 7332
/*
JSONArray charactersArr = arr.getJSONObject(0).getJSONObject("characters").getJSONArray("items");
System.out.println("Heroes con los que sale");
for (int i = 0; i < creatorsArr.length(); i++) {
System.out.println("Nombre: " + creatorsArr.getJSONObject(i).getString("name"));
System.out.println("Rol   : " + creatorsArr.getJSONObject(i).getString("role"));
//System.out.println(arr.getJSONObject(0).getInt("id") + "\n");
}
/**/
