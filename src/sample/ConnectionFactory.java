package sample;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/apteka?useUnicode=true&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка подключения к бд", ex);
        }
    }
}
