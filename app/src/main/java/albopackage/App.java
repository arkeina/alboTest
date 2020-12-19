/*
 * Esta clase manda a llamar a los metodos para traer la informacion de Iron Man y Capitan America, trae a los creadores y el comic modificado más reciente de ese heroe por medio de ENDPOINTS dados por la API de marvel, asi mismo manda a llamar los datos de los heroes que salen en ese comic relacionados.
 */
package albopackage;

//Importe de librerias de java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
//Importe de librerias de org
//Importe de librerias de entrada de datos de java
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
//Importe de librerias declaradas en la dependencia del gradle
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
//Importe de librerias de coneccion BD y manejo de resultsets
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
     * Funcion de llamada para construir el JSON mediante la lectura del texto
     * de la URL y el Buffered Reader
     *
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
     * Funcion de llamado a para leer un JSON desde una URL lo convierte en
     * JSONObject para manejo
     *
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
     * Funcion de llamado a ENDPOINT que regresa el JSON con los creadores por
     * el ID de Iron Man ID 1009368
     */
    public static boolean callIronManCreators() throws MalformedURLException, IOException, SQLException {
        //Se obtiene la respuesta JSON de la url para los datos
        JSONObject jsonIM = readJsonFromUrl("https://gateway.marvel.com/v1/public/characters/1009368/comics?orderBy=-modified&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1&limit=1");
        //ENDPOINT que da el id del HEROE = https://gateway.marvel.com/v1/public/characters?nameStartsWith=iron%20man&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1
        //ID iron man = 1009368
        System.out.println("----------");
        System.out.println("ID iron man = 1009368");
        JSONArray arr = jsonIM.getJSONObject("data").getJSONArray("results");
        System.out.println("Id Comic:" + arr.getJSONObject(0).getInt("id"));
        System.out.println("----------");
        JSONArray creatorsArr = arr.getJSONObject(0).getJSONObject("creators").getJSONArray("items");
        System.out.println("Creadores");
        try {
            heroClearCreators("1");
        } catch (SQLException w) {
            System.out.println("Error:");
            System.out.println(w);
        }
        for (int i = 0; i < creatorsArr.length(); i++) {
            try {
                heroUpdateCreators(creatorsArr.getJSONObject(i).getString("name"), creatorsArr.getJSONObject(i).getString("role"), "1");
            } catch (SQLException w) {
                System.out.println("Error:");
                System.out.println(w);
            }
            System.out.println("Nombre: " + creatorsArr.getJSONObject(i).getString("name"));
            System.out.println("Rol   : " + creatorsArr.getJSONObject(i).getString("role"));
        }
        otherHerosClear("1");
        callCharactersFromComic(String.valueOf(arr.getJSONObject(0).getInt("id")), "1");
        lastSyncUpdate("1");
        System.out.println("----------");
        return false;
    }

    /**
     * Funcion de llamado a ENDPOINT que regresa el JSON con los creadores por
     * el ID de capitan America ID 1009220
     */
    public static boolean callCapAmericaCreators() throws MalformedURLException, IOException, SQLException {
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
        try {
            heroClearCreators("2");
        } catch (SQLException w) {
            System.out.println("Error:");
            System.out.println(w);
        }
        for (int i = 0; i < creatorsArr.length(); i++) {
            try {
                heroUpdateCreators(creatorsArr.getJSONObject(i).getString("name"), creatorsArr.getJSONObject(i).getString("role"), "2");
            } catch (SQLException w) {
                System.out.println("Error:");
                System.out.println(w);
            }
            System.out.println("Nombre: " + creatorsArr.getJSONObject(i).getString("name"));
            System.out.println("Rol   : " + creatorsArr.getJSONObject(i).getString("role"));
            //System.out.println(arr.getJSONObject(0).getInt("id") + "\n");
        }
        otherHerosClear("2");
        callCharactersFromComic(String.valueOf(arr.getJSONObject(0).getInt("id")), "2");
        lastSyncUpdate("2");
        System.out.println("----------");
        return false;
    }

    /**
     * Funcion de llamado a ENDPOINT que regresa el JSON con los personajes por
     * el ID de comic
     *
     * @param String comicNo
     */
    public static boolean callCharactersFromComic(String comicNo, String id_hero_related) throws MalformedURLException, IOException, SQLException {
        JSONObject jsonIM = readJsonFromUrl("https://gateway.marvel.com/v1/public/comics/" + comicNo + "/characters?orderBy=name&apikey=ac50fd1d19c4f4e2727b3444951a8573&hash=fab68ac6420bf936b28e040b1d24d9bd&ts=1");
        System.out.println("----------");
        JSONArray arr = jsonIM.getJSONObject("data").getJSONArray("results");
        System.out.println("Characters in comic " + comicNo);
        for (int i = 0; i < arr.length(); i++) {
            System.out.println("Heroe : " + arr.getJSONObject(i).getString("name"));
            JSONArray charactersArr = arr.getJSONObject(0).getJSONObject("comics").getJSONArray("items");
            System.out.println("Rol   : " + charactersArr.getJSONObject(i).getString("name"));
            try {
                otherHerosUpdate(arr.getJSONObject(i).getString("name"), charactersArr.getJSONObject(i).getString("name"), id_hero_related);
            } catch (SQLException w) {
                System.out.println("Error:");
                System.out.println(w);
            }
        }
        System.out.println("----------");
        return false;
    }

    private static Connection conn;

    /**
     * Funcion principal para la conexion a la base de datos requiere los
     * parametros de conexion MySQL
     *
     * @param String url
     * @param String user
     * @param String pass
     */
    public static void aMySQLConnection(String url, String user, String pass) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connection established.");
    }

    /**
     * Funcion que consulta la base de datos para obtener los detalles de id de
     * marvel y id de comic
     */
    public void getHerosData() throws SQLException {
        String query = "SELECT * FROM  marvel_repo_db.heros;";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Funcion que consulta la base de datos para obtener la tabla de creadores
     * de ambos heroes
     */
    public void getHerosDataCreators() throws SQLException {
        String query = "SELECT * FROM  marvel_repo_db.creators;";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Funcion que consulta la base de datos para obtener la tabla de creadores
     * de ambos heroes
     */
    public void getHerosDataOtherHeros() throws SQLException {
        String query = "SELECT * FROM  marvel_repo_db.other_heros;";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Funcion que borra a todos los creadores de la tabla de creadores por el
     * heroe relacionado que se recibe como parametro puede ser 1 IronMan 2
     * CapAmerica
     *
     * @param String hero_rel
     */
    public static void heroClearCreators(String hero_rel) throws SQLException {
        String query = "DELETE FROM marvel_repo_db.creators WHERE id_hero_rel = '" + hero_rel + "';";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Funcion que recibe parametros de nombre, rol, y heroe relacionado para
     * insertar en la tabla de creadores.
     *
     * @param String name
     * @param String rol
     * @param String id_hero_rel
     */
    public static void heroUpdateCreators(String name, String rol, String id_hero_rel) throws SQLException {
        String query = "INSERT INTO marvel_repo_db.creators (`name`,`rol`,`id_hero_rel`) VALUES ('" + name + "', '" + rol + "', '" + id_hero_rel + "');";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Funcion que borra a todos los heroes realcionados de la tabla de
     * other_heros por id del heroe relacionado que se recibe como parametro
     * puede ser 1-IronMan, 2-CapAmerica
     *
     * @param String hero_rel
     */
    public static void otherHerosClear(String hero_rel) throws SQLException {
        String query = "DELETE FROM marvel_repo_db.other_heros WHERE id_hero_related = '" + hero_rel + "';";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Funcion que recibe parametros de nombre del heroe que sale junto con el
     * principal, el nombre de comic donde aparece, y heroe relacionado para
     * insertar en la tabla de otros heroes.
     *
     * @param String other_hero
     * @param String comic_name
     * @param String id_hero_rel
     */
    public static void otherHerosUpdate(String other_hero, String comic_name, String id_hero_rel) throws SQLException {
        String query = "INSERT INTO marvel_repo_db.other_heros (`name`,`comic`,`id_hero_related`) VALUES ('" + other_hero + "','" + comic_name + "','" + id_hero_rel + "');";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Funcion que actualiza la ultima sincronizacion de los datos por el id de
     * heroe
     *
     * @param String id_hero
     */
    public static void lastSyncUpdate(String id_hero) throws SQLException {
        String query = "UPDATE `marvel_repo_db`.`heros` SET `last_sync` = current_timestamp() WHERE `id_hero` = '" + id_hero + "';";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
    }

    /**
     * Funcion principal que se ejecuta al correr el gradlew run, manda a llamar
     * a todos los metodos internos de la clase para ejecucion de los mismos
     */
    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException, SQLException, ClassNotFoundException {
        //System.out.println(new App().getGreeting());
        new App().aMySQLConnection("jdbc:mysql://127.0.0.1", "root", "");
        new App().callIronManCreators();
        TimeUnit.SECONDS.sleep(1);
        new App().callCapAmericaCreators();
        //MySQLConnection("jdbc:mysql://127.0.0.1", "root", "");
        new App().getHerosData();
        System.out.println("");
        new App().getHerosDataCreators();
        System.out.println("");
        new App().getHerosDataOtherHeros();
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
