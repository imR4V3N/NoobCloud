package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;

public class Connexion {
    private String database = "noobcloud";
    private String url = "jdbc:postgresql://localhost:5432/"+database;
    private String driver = "org.postgresql.Driver";
    private String user = "postgres";
    private String password = "post";

    
    public Connection getConnexion() throws Exception{
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (RuntimeException e) {
            throw new Exception();	
        }
        return connection;
    }
}
