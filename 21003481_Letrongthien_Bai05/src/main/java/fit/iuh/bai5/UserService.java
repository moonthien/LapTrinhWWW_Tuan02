package fit.iuh.bai5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            if (conn == null) {
                System.out.println("Kết nối cơ sở dữ liệu không thành công");
                return null; // Thêm kiểm tra nếu kết nối không thành công
            }
            rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"), 
                        rs.getString("first_name"), 
                        rs.getString("last_name"), 
                        rs.getDate("dob").toLocalDate()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return users;
    }

    // Lấy thông tin của một người dùng
    public User getUserById(int id) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"), 
                        rs.getString("first_name"), 
                        rs.getString("last_name"), 
                        rs.getDate("dob").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return user;
    }

    // Tạo một người dùng mới
    public void createUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("INSERT INTO users (first_name, last_name, dob) VALUES (?, ?, ?)");
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(user.getDob())); // Với kiểu java.time.LocalDate
            

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, dob = ? WHERE id = ?");
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(user.getDob())); // Với kiểu java.time.LocalDate
            stmt.setInt(4, user.getId());

            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return rowUpdated;
    }

    // Xóa người dùng
    public boolean deleteUser(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowDeleted = false;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt.setInt(1, id);

            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return rowDeleted;
    }
}
