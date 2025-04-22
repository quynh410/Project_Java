package ra.edu.business.dao.invoice;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAOImp implements InvoiceDAO {
    private Connection conn;

    public InvoiceDAOImp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Invoice> findAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            callSt = conn.prepareCall("{call proc_findallinvoices()}");
            rs = callSt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setCustomerId(rs.getInt("cus_id"));
                invoice.setCustomerName(rs.getString("cus_name"));
                invoice.setCreateAt(rs.getTimestamp("create_at"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setStatus(rs.getString("status"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (callSt != null) {
                    callSt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return invoices;
    }

    @Override
    public Invoice findInvoiceById(int invoiceId) {
        Invoice invoice = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT i.*, c.cus_name FROM invoice i JOIN customer c ON i.cus_id = c.cus_id WHERE i.invoice_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, invoiceId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setCustomerId(rs.getInt("cus_id"));
                invoice.setCustomerName(rs.getString("cus_name"));
                invoice.setCreateAt(rs.getTimestamp("create_at"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return invoice;
    }

    @Override
    public List<Invoice> findInvoicesByCustomerId(int customerId) {
        List<Invoice> invoices = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT i.*, c.cus_name FROM invoice i JOIN customer c ON i.cus_id = c.cus_id WHERE i.cus_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setCustomerId(rs.getInt("cus_id"));
                invoice.setCustomerName(rs.getString("cus_name"));
                invoice.setCreateAt(rs.getTimestamp("create_at"));
                invoice.setTotalAmount(rs.getDouble("total_amount"));
                invoice.setStatus(rs.getString("status"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn theo ID khách hàng: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return invoices;
    }

//    @Override
//    public List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate) {
//        List<Invoice> invoices = new ArrayList<>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "SELECT i.*, c.cus_name FROM invoice i JOIN customer c ON i.cus_id = c.cus_id " +
//                    "WHERE i.create_at BETWEEN ? AND ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setTimestamp(1, new Timestamp(startDate.getTime()));
//            pstmt.setTimestamp(2, new Timestamp(endDate.getTime()));
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Invoice invoice = new Invoice();
//                invoice.setInvoiceId(rs.getInt("invoice_id"));
//                invoice.setCustomerId(rs.getInt("cus_id"));
//                invoice.setCustomerName(rs.getString("cus_name"));
//                invoice.setCreateAt(rs.getTimestamp("create_at"));
//                invoice.setTotalAmount(rs.getDouble("total_amount"));
//                invoice.setStatus(rs.getString("status"));
//                invoices.add(invoice);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return invoices;
//    }
@Override
public List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate) {
    List<Invoice> invoices = new ArrayList<>();
    CallableStatement callSt = null;
    ResultSet rs = null;

    try {
        callSt = conn.prepareCall("{call GetInvoicesByDateRange(?, ?)}");
        callSt.setTimestamp(1, new Timestamp(startDate.getTime()));
        callSt.setTimestamp(2, new Timestamp(endDate.getTime()));
        rs = callSt.executeQuery();

        while (rs.next()) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(rs.getInt("invoice_id"));
            invoice.setCustomerId(rs.getInt("cus_id"));
            invoice.setCustomerName(rs.getString("customer_name"));
            invoice.setCreateAt(rs.getTimestamp("create_at"));
            invoice.setTotalAmount(rs.getDouble("total_amount"));
            invoice.setStatus(rs.getString("status"));
            invoices.add(invoice);
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi tìm hóa đơn theo khoảng thời gian: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (callSt != null) {
                callSt.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    return invoices;
}
//    @Override
//    public List<Invoice> findInvoicesByStatus(String status) {
//        List<Invoice> invoices = new ArrayList<>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "SELECT i.*, c.cus_name FROM invoice i JOIN customer c ON i.cus_id = c.cus_id " +
//                    "WHERE i.status = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, status);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Invoice invoice = new Invoice();
//                invoice.setInvoiceId(rs.getInt("invoice_id"));
//                invoice.setCustomerId(rs.getInt("cus_id"));
//                invoice.setCustomerName(rs.getString("cus_name"));
//                invoice.setCreateAt(rs.getTimestamp("create_at"));
//                invoice.setTotalAmount(rs.getDouble("total_amount"));
//                invoice.setStatus(rs.getString("status"));
//                invoices.add(invoice);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return invoices;
//    }
@Override
public List<Invoice> findInvoicesByStatus(String status) {
    List<Invoice> invoices = new ArrayList<>();
    CallableStatement callSt = null;
    ResultSet rs = null;

    try {
        callSt = conn.prepareCall("{call GetInvoicesByStatus(?)}");
        callSt.setString(1, status);
        rs = callSt.executeQuery();

        while (rs.next()) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(rs.getInt("invoice_id"));
            invoice.setCustomerId(rs.getInt("cus_id"));
            invoice.setCustomerName(rs.getString("customer_name"));
            invoice.setCreateAt(rs.getTimestamp("create_at"));
            invoice.setTotalAmount(rs.getDouble("total_amount"));
            invoice.setStatus(rs.getString("status"));
            invoices.add(invoice);
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi tìm hóa đơn theo trạng thái: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (callSt != null) {
                callSt.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    return invoices;
}
    @Override
    public int createInvoice(Invoice invoice) {
        CallableStatement callSt = null;
        int newInvoiceId = -1;

        try {
            callSt = conn.prepareCall("{call proc_createinvoice(?, ?, ?)}");
            callSt.setInt(1, invoice.getCustomerId());
            callSt.setDouble(2, invoice.getTotalAmount());
            callSt.registerOutParameter(3, Types.INTEGER);

            callSt.execute();

            newInvoiceId = callSt.getInt(3);

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo hóa đơn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (callSt != null) {
                    callSt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newInvoiceId;
    }

    @Override
    public boolean updateInvoiceStatus(int invoiceId, String status) {
        CallableStatement callSt = null;
        boolean success = false;

        try {
            callSt = conn.prepareCall("{call proc_updateinvoicestatus(?, ?,?)}");
            callSt.setInt(1, invoiceId);
            callSt.setString(2, status);
            callSt.registerOutParameter(3, Types.BOOLEAN);

            callSt.execute();
            success = true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật trạng thái hóa đơn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (callSt != null) {
                    callSt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetails(int invoiceId) {
        List<InvoiceDetail> details = new ArrayList<>();
        CallableStatement callSt = null;
        ResultSet rs = null;

        try {
            // Thay đổi từ GetInvoiceDetailById thành GetInvoiceDetailsByInvoiceId
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
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (callSt != null) {
                    callSt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return details;
    }
    @Override
    public boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
        CallableStatement callSt = null;
        boolean success = false;

        try {
            callSt = conn.prepareCall("{call InsertInvoiceDetail(?, ?, ?, ?,?)}");
            callSt.setInt(1, invoiceDetail.getInvoiceId());
            callSt.setInt(2, invoiceDetail.getProId());
            callSt.setInt(3, invoiceDetail.getQuantity());
            callSt.setDouble(4, invoiceDetail.getUnitPrice());
            callSt.registerOutParameter(5, Types.BOOLEAN);
            callSt.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (callSt != null) {
                    callSt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

}