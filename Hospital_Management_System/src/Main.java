import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/company";
        String username = "root";
        String password = "Satyam@1";

        try {
            // Establishing connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");

            // Perform database operations here...

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
