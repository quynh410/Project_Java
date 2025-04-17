package ra.edu.business.dao.admin;

import ra.edu.business.model.Admin;
import ra.edu.business.config.ConnectionDB;
import java.sql.*;

public class AdminDAOImp implements AdminDAO {
    private static Admin currentAdmin = null;

    @Override
    public void logout() {
        if (currentAdmin != null) {
            System.out.println("Đăng xuất thành công! Tạm biệt " + currentAdmin.getUsername());
            currentAdmin = null; // Xóa thông tin người dùng đang đăng nhập
        } else {
            System.out.println("Không có người dùng nào đang đăng nhập");
        }
    }

    // Phương thức hỗ trợ kiểm tra trạng thái đăng nhập
    public static boolean isLoggedIn() {
        return currentAdmin != null;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }
    @Override
    public Admin login(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";

        try {
            conn = ConnectionDB.openConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Nếu tìm thấy admin trong database
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                admin.setStatus(rs.getBoolean("status"));
                admin.setRole(Admin.Role.fromString(rs.getString("role")));

                if (!admin.isStatus()) {
                    System.out.println("Tài khoản hiện không hoạt động");
                    return null;
                }

                currentAdmin = admin;
                System.out.println("Đăng nhập thành công! Chào mừng " + admin.getUsername());
                return admin;
            } else {
                System.out.println("Tên đăng nhập hoặc mật khẩu không đúng");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) ConnectionDB.closeConnection(conn, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}