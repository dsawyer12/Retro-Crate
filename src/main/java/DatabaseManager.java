import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

    public DatabaseManager() {}

    public static DatabaseManager getInstance() {
        return new DatabaseManager();
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public static boolean isConnected(Connection connection) {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /********** Create a new Table **************/

    public static void createNewTable(String tableName, ArrayList<String> columnData) {
        tableName = tableName.replace(" ", "_"); // only needed if users will be declaring their own names for tables
        StringBuilder sb = new StringBuilder();
        String prefix = "CREATE TABLE IF NOT EXISTS " + tableName +"(";
        sb.append(prefix);
        for (String columnItem : columnData) {
            sb.append(columnItem).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");

        Connection connection = getConnection();
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
