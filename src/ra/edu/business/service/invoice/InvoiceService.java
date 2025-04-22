package ra.edu.business.service.invoice;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    Invoice getInvoiceById(int invoiceId);
    List<Invoice> getInvoicesByCustomerId(int customerId);
    List<Invoice> getInvoicesByDateRange(Date startDate, Date endDate);
    List<Invoice> getInvoicesByStatus(String status);
    int createInvoice(Invoice invoice, List<InvoiceDetail> details);
    boolean updateInvoiceStatus(int invoiceId, String status);

    List<InvoiceDetail> getInvoiceDetails(int invoiceId);
}