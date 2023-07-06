package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CustomConnector {
    public Connection getConnection(String url) {
        Connection conn;
        try {
            FileInputStream fis=new FileInputStream("app.properties");
            Properties props=new Properties ();
            props.load(fis);
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public Connection getConnection(String url, String user, String password) {

        Connection conn;

        try {

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;

    }
}
