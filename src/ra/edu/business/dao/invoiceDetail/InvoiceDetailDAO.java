package ra.edu.business.dao.invoiceDetail;

import ra.edu.business.model.InvoiceDetail;
import java.util.List;

public interface InvoiceDetailDAO {
    List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId);
    boolean addInvoiceDetail(InvoiceDetail detail);
    InvoiceDetail findDetailById(int detailId);
    boolean checkProductExistsInInvoice(int invoiceId, int productId);
}