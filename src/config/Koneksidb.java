/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import javax.swing.JFrame;

/**
 *
 * @author Reza Azmi
 */
public class Koneksidb {
    public Connection con;
    public Statement st;
    public ResultSet rest;
    
    public Koneksidb() throws SQLException, SQLTimeoutException{
        bukaKoneksi();
    }
    public void bukaKoneksi() throws SQLException, SQLTimeoutException{
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectkp?", "root", "");
        st = con.createStatement();
    }
    public ResultSet eksekusiSelect(String sql) throws SQLException, SQLTimeoutException{
        bukaKoneksi();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }
    public int eksekusiUpdate(String sql) throws SQLException, SQLTimeoutException{
        bukaKoneksi();
        int result = st.executeUpdate(sql);
        return result;
    }
    public void tutupKoneksi() throws SQLException, SQLTimeoutException{
        con.close();
    }
    public void sambung(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/projcetkp","root","");
            System.out.println("koneksi sukses");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void movePageJFrame(JFrame move, JFrame ini){
        move.setVisible(true);
        ini.setVisible(false);
    }
}
