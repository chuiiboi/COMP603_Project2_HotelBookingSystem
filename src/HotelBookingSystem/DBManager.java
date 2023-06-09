package HotelBookingSystem;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 21145050
 */
public class DBManager {

    private static final String USER_NAME = "brendanc"; //DB username
    private static final String PASSWORD = "brendanc"; //DB password
    private static final String URL = "jdbc:derby:HotelDB; create=true";  //url of the DB host

    Connection conn;

    // DataBase manager:
    public DBManager() {
        establishConnection();

    }

    // Getter:
    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                //System.out.println(URL + " Get Connected Successfully ...."); // For DB connection testing.
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public boolean checkTableExists(String tableName) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
