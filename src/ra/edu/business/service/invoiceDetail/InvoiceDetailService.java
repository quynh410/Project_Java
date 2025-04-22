package ra.edu.business.service.invoiceDetail;
import ra.edu.business.model.InvoiceDetail;
import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId);
    boolean addInvoiceDetail(InvoiceDetail detail);
    boolean updateInvoiceDetail(InvoiceDetail detail);
    boolean deleteInvoiceDetail(int detailId);
    InvoiceDetail findDetailById(int detailId);
    boolean updateQuantity(int detailId, int newQuantity);
    double calculateInvoiceTotal(int invoiceId);
}
