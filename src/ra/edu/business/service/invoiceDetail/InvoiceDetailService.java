package ra.edu.business.service.invoiceDetail;
import ra.edu.business.model.InvoiceDetail;
import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId);
    boolean addInvoiceDetail(InvoiceDetail detail);
    InvoiceDetail findDetailById(int detailId);
    double calculateInvoiceTotal(int invoiceId);
}
