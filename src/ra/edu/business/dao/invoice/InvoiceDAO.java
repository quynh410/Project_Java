package ra.edu.business.dao.invoice;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.util.Date;
import java.util.List;

public interface InvoiceDAO {
    List<Invoice> findAllInvoices();
    Invoice findInvoiceById(int invoiceId);
    List<Invoice> findInvoicesByCustomerId(int customerId);
    List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate);
    List<Invoice> findInvoicesByStatus(String status);
    int createInvoice(Invoice invoice);
    boolean updateInvoiceStatus(int invoiceId, String status);

    List<InvoiceDetail> getInvoiceDetails(int invoiceId);
    boolean addInvoiceDetail(InvoiceDetail invoiceDetail);
}