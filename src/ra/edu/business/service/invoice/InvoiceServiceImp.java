package ra.edu.business.service.invoice;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.dao.invoice.InvoiceDAO;
import ra.edu.business.dao.invoice.InvoiceDAOImp;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class InvoiceServiceImp implements InvoiceService {
    private final InvoiceDAO invoiceDAO;
    private final Connection conn;
    public InvoiceServiceImp() {
        this.conn = ConnectionDB.openConnection();
        this.invoiceDAO = new InvoiceDAOImp(conn);

//        List<Invoice> list = invoiceDAO.findAllInvoices();
//        if (list.isEmpty()) {
//            System.out.println("Không có hóa đơn");
//        } else {
//            System.out.println("\u001B[36m==================== HÓA ĐƠN ========================\u001B[0m");
//            System.out.printf("\u001B[36m| %-10s | %-20s | %-17s |\n", "ID", "Customer Name", "Total\u001B[0m");
//            System.out.println("\u001B[36m=====================================================\u001B[0m");
//
//            for (int i=0; i <  list.size(); i++) {
//                System.out.printf("| %-10s | %-20s | %-13.2f |\n",
//                        list.get(i).getInvoiceId(), list.get(i).getCustomerName(), list.get(i) .getTotalAmount());
//            }
//
//            System.out.println("\u001B[36m=====================================================\u001B[0m");
//        }
    }


    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAllInvoices();
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) {
        return invoiceDAO.findInvoiceById(invoiceId);
    }

    @Override
    public List<Invoice> getInvoicesByCustomerId(int customerId) {
        return invoiceDAO.findInvoicesByCustomerId(customerId);
    }

    @Override
    public List<Invoice> getInvoicesByDateRange(Date startDate, Date endDate) {
        return invoiceDAO.findInvoicesByDateRange(startDate, endDate);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceDAO.findInvoicesByStatus(status);
    }

    @Override
    public int createInvoice(Invoice invoice, List<InvoiceDetail> details) {
        try {
            conn.setAutoCommit(false);
            double totalAmount = calculateInvoiceTotal(details);
            invoice.setTotalAmount(totalAmount);
            int invoiceId = invoiceDAO.createInvoice(invoice);

            if (invoiceId > 0) {
                for (InvoiceDetail detail : details) {
                    detail.setInvoiceId(invoiceId);
                    boolean detailAdded = invoiceDAO.addInvoiceDetail(detail);
                    if (!detailAdded) {
                        conn.rollback();
                        System.out.println("Lỗi khi thêm chi tiết hóa đơn, thực hiện rollback.");
                        return -1;
                    }
                }

                conn.commit();
                return invoiceId;
            } else {
                conn.rollback();
                System.out.println("Lỗi khi tạo hóa đơn, thực hiện rollback.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo hóa đơn: " + e.getMessage());
            try {
                conn.rollback();
                System.out.println("Đã rollback giao dịch do lỗi.");
            } catch (SQLException ex) {
                System.out.println("Lỗi khi rollback giao dịch: " + ex.getMessage());
            }
            return -1;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Lỗi khi đặt lại chế độ tự động commit: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean updateInvoiceStatus(int invoiceId, String status) {
        return invoiceDAO.updateInvoiceStatus(invoiceId, status);
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetails(int invoiceId) {
        return invoiceDAO.getInvoiceDetails(invoiceId);
    }
    public double calculateInvoiceTotal(List<InvoiceDetail> details) {
        double total = 0.0;
        for (InvoiceDetail detail : details) {
            total += detail.getQuantity() * detail.getUnitPrice();
        }
        return total;
    }
}