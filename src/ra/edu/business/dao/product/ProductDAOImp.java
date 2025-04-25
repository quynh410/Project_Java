package ra.edu.business.dao.product;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements ProductDAO {

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findallproducts()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProId(rs.getInt("pro_id"));
                product.setProName(rs.getString("pro_name"));
                product.setProBrand(rs.getString("pro_brand"));
                product.setProPrice(rs.getDouble("pro_price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return products;
    }

    @Override
    public boolean addProducts(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call add_product(?, ?, ?, ?,?)}");
            callSt.setString(1, product.getProName());
            callSt.setString(2, product.getProBrand());
            callSt.setDouble(3, product.getProPrice());
            callSt.setInt(4, product.getStock());
            callSt.setBoolean(5,product.isStatus());

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product.setProId(rs.getInt("pro_id"));
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    @Override
    public boolean updateProducts(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_updateproduct(?, ?, ?, ?, ?,?)}");
            callSt.setInt(1, product.getProId());
            callSt.setString(2, product.getProName());
            callSt.setString(3, product.getProBrand());
            callSt.setDouble(4, product.getProPrice());
            callSt.setInt(5, product.getStock());
            callSt.setBoolean(6, product.isStatus());

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                result = rs.getBoolean("success");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
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
            callSt = conn.prepareCall("{call proc_deleteproduct(?)}");
            callSt.setInt(1, id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                result = rs.getBoolean("success");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    @Override
    public Product findById(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Product product = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findproductbyid(?)}");
            callSt.setInt(1, id);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProId(rs.getInt("pro_id"));
                product.setProName(rs.getString("pro_name"));
                product.setProBrand(rs.getString("pro_brand"));
                product.setProPrice(rs.getDouble("pro_price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo ID: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return product;
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findproductsbypricerange(?, ?)}");
            callSt.setDouble(1, minPrice);
            callSt.setDouble(2, maxPrice);

            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProId(rs.getInt("pro_id"));
                product.setProName(rs.getString("pro_name"));
                product.setProBrand(rs.getString("pro_brand"));
                product.setProPrice(rs.getDouble("pro_price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo khoảng giá: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return products;
    }

    @Override
    public List<Product> findByStockRange(int minStock, int maxStock) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findproductsbystockrange(?, ?)}");
            callSt.setInt(1, minStock);
            callSt.setInt(2, maxStock);

            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProId(rs.getInt("pro_id"));
                product.setProName(rs.getString("pro_name"));
                product.setProBrand(rs.getString("pro_brand"));
                product.setProPrice(rs.getDouble("pro_price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo khoảng tồn kho: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return products;
    }

    @Override
    public List<Product> findByBrand(String brand) {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_findproductsbybrand(?)}");
            callSt.setString(1, brand);

            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProId(rs.getInt("pro_id"));
                product.setProName(rs.getString("pro_name"));
                product.setProBrand(rs.getString("pro_brand"));
                product.setProPrice(rs.getDouble("pro_price"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm theo thương hiệu: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return products;
    }
}