package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=QLLNV";
        String user = "kaiser";
        String pass = "1234";
        con = DriverManager.getConnection(dbURL, user, pass);
    }

    public void disConnect() {
        if (con != null)
			try {
            con.close();
            System.out.println("Thành công");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
