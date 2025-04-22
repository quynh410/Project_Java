package ra.edu.business.service.invoiceDetail;
import ra.edu.business.dao.invoiceDetail.InvoiceDetailDAO;
import ra.edu.business.dao.invoiceDetail.InvoiceDetailDAOImp;
import ra.edu.business.model.InvoiceDetail;

import java.util.List;

public class InvoiceDetailServiceImp implements InvoiceDetailService {
    private final InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAOImp();

    @Override
    public List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId) {
        return invoiceDetailDAO.getDetailsByInvoiceId(invoiceId);
    }

    @Override
    public boolean addInvoiceDetail(InvoiceDetail detail) {
        return invoiceDetailDAO.addInvoiceDetail(detail);
    }


    @Override
    public InvoiceDetail findDetailById(int detailId) {
        return invoiceDetailDAO.findDetailById(detailId);
    }


    @Override
    public double calculateInvoiceTotal(int invoiceId) {
        List<InvoiceDetail> details = getDetailsByInvoiceId(invoiceId);
        double total = 0;

        for (InvoiceDetail detail : details) {
            total += detail.getSubtotal();
        }

        return total;
    }
}
