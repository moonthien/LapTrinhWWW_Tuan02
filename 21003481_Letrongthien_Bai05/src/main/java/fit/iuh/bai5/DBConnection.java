package fit.iuh.bai5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Constructor riêng tư để ngăn việc tạo thể hiện
    private DBConnection() { 
        // Class tiện ích; không cho phép tạo thể hiện
    }

    // Thiết lập kết nối với cơ sở dữ liệu
    public static Connection getConnection() {
        Connection connection = null;
        String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=Users;encrypt=false;";
        String jdbcUsername = "sa";
        String jdbcPassword = "123";

        try {
            // Tải driver JDBC của SQL Server
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Thử kết nối đến cơ sở dữ liệu
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: Không thể kết nối với cơ sở dữ liệu.");
            e.printStackTrace();
        } 

        return connection;
    }

    // Phương thức tiện ích để đóng kết nối
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Lỗi SQL: Không thể đóng kết nối.");
                e.printStackTrace();
            }
        }
    }
}
