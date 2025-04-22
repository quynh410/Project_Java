package ra.edu.business.dao;

public class AppDAO {
}

/*

package ra.edu.presentation;

import ra.edu.business.dao.invoice.InvoiceDAO;
import ra.edu.business.dao.invoice.InvoiceDAOImp;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;
import ra.edu.business.model.Product;
import ra.edu.business.dao.product.ProductDAO;
import ra.edu.business.dao.product.ProductDAOImp;
import ra.edu.business.dao.customer.CustomerDAO;
import ra.edu.business.dao.customer.CustomerDAOImp;
import ra.edu.business.model.Customer;
import ra.edu.util.ConnectionDB;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InvoiceUI {
    private final Scanner scanner = new Scanner(System.in);
    private InvoiceDAO invoiceDAO;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private Connection conn;

    public InvoiceUI() {
        try {
            conn = ConnectionDB.openConnection();
            invoiceDAO = new InvoiceDAOImp(conn);
            productDAO = new ProductDAOImp(conn);
            customerDAO = new CustomerDAOImp(conn);
        } catch (Exception e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }

    public void menuInvoice() {
        int choice;
        do {
            System.out.println("===== QUẢN LÝ HÓA ĐƠN =====");
            System.out.println("1. Tạo hóa đơn mới");
            System.out.println("2. Hiển thị danh sách hóa đơn");
            System.out.println("3. Xem chi tiết hóa đơn");
            System.out.println("4. Tìm kiếm hóa đơn");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    createNewInvoice();
                    break;
                case 2:
                    displayAllInvoices();
                    break;
                case 3:
                    viewInvoiceDetails();
                    break;
                case 4:
                    searchInvoiceMenu();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private void createNewInvoice() {
        try {
            // Hiển thị danh sách khách hàng
            System.out.println("\n===== DANH SÁCH KHÁCH HÀNG =====");
            List<Customer> customers = customerDAO.findAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("Không có khách hàng nào trong hệ thống.");
                return;
            }

            for (Customer c : customers) {
                System.out.printf("ID: %d | Tên: %s | SĐT: %s\n", c.getCustomerId(), c.getCustomerName(), c.getPhone());
            }

            // Chọn khách hàng
            System.out.print("Nhập ID khách hàng: ");
            int customerId;
            try {
                customerId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ.");
                return;
            }

            Customer selectedCustomer = customerDAO.findCustomerById(customerId);
            if (selectedCustomer == null) {
                System.out.println("Không tìm thấy khách hàng với ID này.");
                return;
            }

            // Tạo hóa đơn mới
            Invoice invoice = new Invoice();
            invoice.setCustomerId(customerId);
            invoice.setCustomerName(selectedCustomer.getCustomerName());
            invoice.setStatus("Chờ thanh toán");
            invoice.setTotalAmount(0.0);

            // Lưu hóa đơn vào cơ sở dữ liệu để lấy ID
            int invoiceId = invoiceDAO.createInvoice(invoice);
            if (invoiceId == -1) {
                System.out.println("Không thể tạo hóa đơn mới.");
                return;
            }

            System.out.println("Đã tạo hóa đơn mới với ID: " + invoiceId);

            // Thêm chi tiết hóa đơn
            addInvoiceDetailsProcess(invoiceId);

            // Cập nhật tổng tiền của hóa đơn
            updateInvoiceTotal(invoiceId);

            System.out.println("Đã tạo hóa đơn thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo hóa đơn: " + e.getMessage());
        }
    }

    private void addInvoiceDetailsProcess(int invoiceId) {
        boolean addMore = true;
        double totalAmount = 0;

        while (addMore) {
            try {
                // Hiển thị danh sách sản phẩm
                System.out.println("\n===== DANH SÁCH SẢN PHẨM =====");
                List<Product> products = productDAO.findAllProducts();
                if (products.isEmpty()) {
                    System.out.println("Không có sản phẩm nào trong hệ thống.");
                    return;
                }

                for (Product p : products) {
                    System.out.printf("ID: %d | Tên: %s | Giá: %.2f | Số lượng còn: %d\n",
                                      p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantity());
                }

                // Chọn sản phẩm
                System.out.print("Nhập ID sản phẩm: ");
                int productId;
                try {
                    productId = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("ID không hợp lệ.");
                    continue;
                }

                Product selectedProduct = productDAO.findProductById(productId);
                if (selectedProduct == null) {
                    System.out.println("Không tìm thấy sản phẩm với ID này.");
                    continue;
                }

                // Nhập số lượng
                System.out.print("Nhập số lượng: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                        continue;
                    }
                    if (quantity > selectedProduct.getQuantity()) {
                        System.out.println("Số lượng không đủ trong kho.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ.");
                    continue;
                }

                // Tạo chi tiết hóa đơn
                InvoiceDetail detail = new InvoiceDetail();
                detail.setInvoiceId(invoiceId);
                detail.setProId(productId);
                detail.setQuantity(quantity);
                detail.setUnitPrice(selectedProduct.getPrice());

                // Lưu chi tiết hóa đơn
                boolean success = invoiceDAO.addInvoiceDetail(detail);
                if (!success) {

* */
