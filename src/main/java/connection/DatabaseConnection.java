package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// singleton ce realizeaza conexiunea la baza de date
public class DatabaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/java_proiect?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
