package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Reza Azmi
 */
public class koneksi {
    public static Connection SambungKeDatabase(){
        String DRIVER = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/projectkp";
        Connection kon = null;
        
        try {
            Class.forName(DRIVER).newInstance();
            kon = DriverManager.getConnection(url,"root","");
            return kon;
        } catch (ClassNotFoundException | IllegalAccessException |  InstantiationException | SQLException e) {
            System.err.println("Error: "+ e.getMessage());
            System.exit(0);   
        }
        return null;
    }
    
    public static Connection getConnection() {
        Connection con = null;
        if(con == null){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectkp?", "root", "");
            } catch (SQLException e) {
                System.exit(0);
            }
        }
        return con;
    }
}
