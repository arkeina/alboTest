package albopackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLConnection {

    private static Connection conn;

    // init
    public static void MySQLConnection(String url, String user, String pass) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connection established.");
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySQLConnection("jdbc:mysql://127.0.0.1", "root", "");
    }
    /*
    public void getDrugsClaims() throws SQLException {
        String query = "select * from drugs";
        Statement st = this._conn.createStatement();
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
        return;
    }
    /**/

}