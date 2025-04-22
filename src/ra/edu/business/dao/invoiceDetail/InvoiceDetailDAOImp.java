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
        String sql = "{call GetInvoiceDetailsByInvoiceId(?)}";

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InvoiceDetail detail = new InvoiceDetail();
                    detail.setInvoiceDetailId(rs.getInt("detail_id"));
                    detail.setInvoiceId(rs.getInt("invoice_id"));
                    detail.setProId(rs.getInt("product_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setUnitPrice(rs.getDouble("unit_price"));
                    details.add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching invoice details by invoice ID: " + invoiceId, e);
        }

        return details;
    }

    @Override
    public boolean addInvoiceDetail(InvoiceDetail detail) {
        String sql = "{call InsertInvoiceDetail(?,?,?,?,?)}";
        boolean result = false;

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, detail.getInvoiceId());
            stmt.setInt(2, detail.getProId());
            stmt.setInt(3, detail.getQuantity());
            stmt.setDouble(4, detail.getUnitPrice());
            stmt.registerOutParameter(5, Types.BOOLEAN);

            stmt.execute();
            result = stmt.getBoolean(5);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding invoice detail", e);
        }

        return result;
    }


    @Override
    public InvoiceDetail findDetailById(int detailId) {
        String sql = "{call GetInvoiceDetailById(?)}";
        InvoiceDetail detail = null;

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, detailId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detail = new InvoiceDetail();
                    detail.setInvoiceDetailId(rs.getInt("detail_id"));
                    detail.setInvoiceId(rs.getInt("invoice_id"));
                    detail.setProId(rs.getInt("product_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setUnitPrice(rs.getDouble("unit_price"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tìm kiếm bằng ID: " + detailId, e);
        }

        return detail;
    }

    @Override
    public boolean checkProductExistsInInvoice(int invoiceId, int productId) {
        String sql = "{call CheckProductExistsInInvoice(?,?)}";
        boolean exists = false;

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, invoiceId);
            stmt.setInt(2, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking product existence in invoice", e);
        }

        return exists;
    }
}
