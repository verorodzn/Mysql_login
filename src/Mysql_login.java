import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mysql_login {
    static final String URL = "jdbc:mysql://10.10.161.91/db_books?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    public static void main (String[] args){
        // Connection to the database

        //Connection conexion = null;

        String user = "library";
        String password = "zE3F3BPsxG8ccteX@";
        String program_username = "Spiderman";
        String program_password = "swoosh123";

        Connection conexion = ConectaServer(user, password, program_username, program_password);

        if(conexion != null){
            System.out.println("Welcome " + program_username + "!");
            // Punto de acceso
        } else {
            System.out.println("Access denied to " + program_username);
        }

        try{
            conexion.close();
        }
        catch (Exception e){
            System.out.println("Error closing connection");
        }
    }
    
    public static Connection ConectaServer(String user, String password, String program_user, String program_password){
        Connection conexion = null;
        String SQL = "SELECT username FROM users WHERE username= ? AND password = SHA2(?,256)";
        System.out.println(SQL);

        try{
            conexion = DriverManager.getConnection(URL, user, password);
            System.out.println("Successful connection to the database");
            PreparedStatement psu = conexion.prepareStatement(SQL);
            psu.setString(1, program_user);
            psu.setString(2, program_password);
            System.out.println("SQL final: " + psu.toString());
            ResultSet rsu = psu.executeQuery();

            if(rsu.next() == true){
                return conexion;
            } else {
                return null;
            }
        }
        catch (Exception ex){
            System.out.println("SQL Exception: " +  ex.getMessage());
        }
        return conexion;
    }
}