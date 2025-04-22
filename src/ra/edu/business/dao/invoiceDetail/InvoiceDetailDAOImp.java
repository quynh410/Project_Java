package ra.edu.business.dao.invoiceDetail;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.dao.product.ProductDAO;
import ra.edu.business.dao.product.ProductDAOImp;
import ra.edu.business.model.InvoiceDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAOImp implements InvoiceDetailDAO {
    private final ProductDAO productDAO = new ProductDAOImp();

    @Override
    public List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId) {
        List<InvoiceDetail> details = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call GetInvoiceDetailsByInvoiceId(?)}");
            callSt.setInt(1, invoiceId);
            rs = callSt.executeQuery();

            while (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setInvoiceDetailId(rs.getInt("detail_id"));
                detail.setInvoiceId(rs.getInt("invoice_id"));
                detail.setProId(rs.getInt("product_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnitPrice(rs.getDouble("unit_price"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching invoice details by invoice ID: " + invoiceId, e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return details;
    }

    @Override
    public boolean addInvoiceDetail(InvoiceDetail detail) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call InsertInvoiceDetail(?,?,?,?,?)}");
            callSt.setInt(1, detail.getInvoiceId());
            callSt.setInt(2, detail.getProId());
            callSt.setInt(3, detail.getQuantity());
            callSt.setDouble(4, detail.getUnitPrice());
            callSt.registerOutParameter(5, Types.BOOLEAN);

            callSt.execute();
            result = callSt.getBoolean(5);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding invoice detail", e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public boolean updateInvoiceDetail(InvoiceDetail detail) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call UpdateInvoiceDetail(?,?,?,?,?)}");
            callSt.setInt(1, detail.getInvoiceDetailId());
            callSt.setInt(2, detail.getQuantity());
            callSt.setDouble(3, detail.getUnitPrice());
            callSt.registerOutParameter(4, Types.BOOLEAN);
            callSt.registerOutParameter(5, Types.INTEGER);

            callSt.execute();
            result = callSt.getBoolean(4);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating invoice detail", e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public boolean deleteInvoiceDetail(int detailId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call DeleteInvoiceDetail(?,?)}");
            callSt.setInt(1, detailId);
            callSt.registerOutParameter(2, Types.BOOLEAN);

            callSt.execute();
            result = callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting invoice detail", e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public InvoiceDetail findDetailById(int detailId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        InvoiceDetail detail = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call GetInvoiceDetailById(?)}");
            callSt.setInt(1, detailId);
            rs = callSt.executeQuery();

            if (rs.next()) {
                detail = new InvoiceDetail();
                detail.setInvoiceDetailId(rs.getInt("detail_id"));
                detail.setInvoiceId(rs.getInt("invoice_id"));
                detail.setProId(rs.getInt("product_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnitPrice(rs.getDouble("unit_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding invoice detail by ID: " + detailId, e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return detail;
    }

    @Override
    public boolean deleteDetailsByInvoiceId(int invoiceId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call DeleteInvoiceDetailsByInvoiceId(?,?)}");
            callSt.setInt(1, invoiceId);
            callSt.registerOutParameter(2, Types.BOOLEAN);

            callSt.execute();
            result = callSt.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting invoice details by invoice ID: " + invoiceId, e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public boolean checkProductExistsInInvoice(int invoiceId, int productId) {
        Connection conn = null;
        CallableStatement callSt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call CheckProductExistsInInvoice(?,?)}");
            callSt.setInt(1, invoiceId);
            callSt.setInt(2, productId);
            rs = callSt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking product existence in invoice", e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return exists;
    }
}