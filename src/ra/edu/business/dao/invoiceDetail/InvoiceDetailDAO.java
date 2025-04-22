package ra.edu.business.dao.invoiceDetail;

import ra.edu.business.model.InvoiceDetail;
import java.util.List;

public interface InvoiceDetailDAO {
    // Lấy tất cả chi tiết của một hóa đơn
    List<InvoiceDetail> getDetailsByInvoiceId(int invoiceId);

    // Thêm chi tiết hóa đơn mới
    boolean addInvoiceDetail(InvoiceDetail detail);

    // Cập nhật chi tiết hóa đơn
    boolean updateInvoiceDetail(InvoiceDetail detail);

    // Xóa chi tiết hóa đơn
    boolean deleteInvoiceDetail(int detailId);

    // Tìm chi tiết hóa đơn theo ID
    InvoiceDetail findDetailById(int detailId);

    // Xóa tất cả chi tiết của một hóa đơn
    boolean deleteDetailsByInvoiceId(int invoiceId);

    // Kiểm tra sản phẩm đã tồn tại trong hóa đơn chưa
    boolean checkProductExistsInInvoice(int invoiceId, int productId);
}