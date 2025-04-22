package ra.edu.business.dao.customer;
import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Customer;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImp implements CustomerDAO {

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findallcustomers()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCusId(rs.getInt("cus_id"));
                customer.setCusName(rs.getString("cus_name"));
                customer.setCusPhone(rs.getString("cus_phone"));
                customer.setCusEmail(rs.getString("cus_email"));
                customer.setCusAddress(rs.getString("cus_address"));
                String genderStr = rs.getString("cus_gender");
                if (genderStr != null) {
                    try {
                        customer.setCusGender(Customer.Gender.valueOf(genderStr.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Giá trị gender không hợp lệ: " + genderStr);
                    }
                }
                // Thêm customer vào danh sách sau khi đã set các thuộc tính
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return customers;
    }

    @Override
    public boolean add(Customer customer) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_savecustomer(?,?,?,?,?)}");
            callSt.setString(1, customer.getCusName());
            callSt.setString(2, customer.getCusPhone());
            callSt.setString(3, customer.getCusEmail());
            callSt.setString(4, customer.getCusAddress());
            callSt.setString(5, customer.getCusGender().toString().toLowerCase());
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                customer.setCusId(rs.getInt("cus_id"));
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    @Override
    public boolean update(Customer customer) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_updatecustomer(?,?,?,?,?,?)}");
            callSt.setInt(1, customer.getCusId());
            callSt.setString(2, customer.getCusName());
            callSt.setString(3, customer.getCusPhone());
            callSt.setString(4, customer.getCusEmail());
            callSt.setString(5, customer.getCusAddress());
            callSt.setString(6, customer.getCusGender().toString().toLowerCase());

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                result = rs.getBoolean("success");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_deletecustomer(?)}");
            callSt.setInt(1, id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                result = rs.getBoolean("success");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    @Override
    public Customer findById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Customer customer = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findcustomerbyid(?)}");
            callSt.setInt(1, id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCusId(rs.getInt("cus_id"));
                customer.setCusName(rs.getString("cus_name"));
                customer.setCusPhone(rs.getString("cus_phone"));
                customer.setCusEmail(rs.getString("cus_email"));
                customer.setCusAddress(rs.getString("cus_address"));
                String genderStr = rs.getString("cus_gender");
                if (genderStr != null) {
                    try {
                        customer.setCusGender(Customer.Gender.valueOf(genderStr.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Giá trị gender không hợp lệ: " + genderStr);
                        // Nếu giá trị không hợp lệ, sử dụng giá trị mặc định
                        customer.setCusGender(Customer.Gender.OTHER);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khách hàng theo ID: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return customer;
    }
}