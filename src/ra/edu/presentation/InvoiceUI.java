package ra.edu.presentation;

import ra.edu.business.model.Customer;
import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;
import ra.edu.business.model.Product;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;
import ra.edu.business.service.invoice.InvoiceService;
import ra.edu.business.service.invoice.InvoiceServiceImp;
import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvoiceUI {
    private final InvoiceService invoiceService = new InvoiceServiceImp();
    private final CustomerService customerService = new CustomerServiceImp();
    private final ProductService productService = new ProductServiceImp();
    private final InvoiceDetailUI invoiceDetailUI = new InvoiceDetailUI();
    private final Scanner scanner = new Scanner(System.in);


    public void menuInvoice() {
        int choice;
        do {
            System.out.println("\u001B[34m========== QUẢN LÝ HÓA ĐƠN ==========\u001B[0m");
            System.out.println("|1. Tạo hóa đơn mới                 |");
            System.out.println("|2. Hiển thị danh sách hóa đơn      |");
            System.out.println("|3. Xem chi tiết hóa đơn            |");
            System.out.println("|4. Tìm kiếm hóa đơn                |");
            System.out.println("|5. Quản lý chi tiết hóa đơn        |");
            System.out.println("|0. Quay lại                        |");
            System.out.println("=====================================");
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
                    displayAllInvoice();
                    break;
                case 3:
                    displayInvoiceDetail();
                    break;
                case 4:
                    searchInvoice();
                    break;
                case 5:
                    invoiceDetailUI.menuInvoiceDetail();
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
            System.out.println("\u001B[34m========= TẠO HÓA ĐƠN MỚI =========\u001B[0m");
            List<Customer> customers = customerService.findAllCustomers();
            if (customers.isEmpty()) {
                System.err.println("Chưa có khách hàng nào trong hệ thống.");
                return;
            }
            System.out.println("\u001B[36m=================== DANH SÁCH KHÁCH HÀNG =====================\u001B[0m");
            System.out.printf("\u001B[36m| %-5s | %-25s | %-15s |\u001B[0m%n", "ID", "Tên khách hàng", "Số điện thoại");
            System.out.println("\u001B[36m---------------------------------------------------------\u001B[0m");
            for (Customer c : customers) {
                System.out.printf("\u001B[32m| %-5d | %-25s | %-15s |\u001B[0m%n",
                        c.getCusId(), c.getCusName(), c.getCusPhone());
            }
            System.out.print("Nhập ID khách hàng: ");
            int customerID;
            try {
                customerID = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("ID không hợp lệ.");
                return;
            }
            Customer selectedCustomer = customerService.findCustomerById(customerID);
            if (selectedCustomer == null) {
                System.err.println("Không tìm thấy khách hàng với ID " + customerID);
                return;
            }

            List<Product> products = productService.findAllProducts();
            if (products.isEmpty()) {
                System.err.println("Chưa có sản phẩm nào trong hệ thống.");
                return;
            }

            Invoice invoice = new Invoice();
            invoice.setCustomerId(selectedCustomer.getCusId());
            invoice.setCustomerName(selectedCustomer.getCusName());

            List<InvoiceDetail> invoiceDetails = new ArrayList<>();
            double totalAmount = 0;

            System.out.println("\u001B[36m============================ DANH SÁCH SẢN PHẨM ============================\u001B[0m");
            System.out.printf("\u001B[36m| %-5s | %-30s | %-10s | %-10s |\u001B[0m%n", "ID", "Tên sản phẩm", "Giá", "Tồn kho");
            System.out.println("\u001B[36m-------------------------------------------------------------------------\u001B[0m");
            for (Product p : products) {
                System.out.printf("\u001B[32m| %-5d | %-30s | %-10.2f | %-10d |\u001B[0m%n",
                        p.getProId(), p.getProName(), p.getProPrice(), p.getStock());
            }

            boolean addMore = true;
            while (addMore) {
                System.out.print("Nhập ID sản phẩm: ");
                int productId;
                try {
                    productId = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.err.println("ID sản phẩm không hợp lệ.");
                    continue;
                }

                Product selectedProduct = null;
                for (Product p : products) {
                    if (p.getProId() == productId) {
                        selectedProduct = p;
                        break;
                    }
                }

                if (selectedProduct == null) {
                    System.err.println("Không tìm thấy sản phẩm với ID " + productId);
                    continue;
                }

                if (selectedProduct.getStock() <= 0) {
                    System.err.println("Sản phẩm đã hết hàng.");
                    continue;
                }

                System.out.print("Nhập số lượng: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                    if (quantity <= 0) {
                        System.err.println("Số lượng phải lớn hơn 0.");
                        continue;
                    }
                    if (quantity > selectedProduct.getStock()) {
                        System.err.println("Số lượng vượt quá tồn kho (hiện có: " + selectedProduct.getStock() + ")");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Số lượng không hợp lệ.");
                    continue;
                }

                InvoiceDetail detail = new InvoiceDetail();
                detail.setProId(selectedProduct.getProId());
                detail.setQuantity(quantity);
                detail.setUnitPrice(selectedProduct.getProPrice());

                invoiceDetails.add(detail);

                totalAmount += (selectedProduct.getProPrice() * quantity);

                System.out.print("Bạn có muốn thêm sản phẩm khác không? (Y/N): ");
                String choice = scanner.nextLine().trim();
                addMore = choice.equalsIgnoreCase("Y");
            }

            if (invoiceDetails.isEmpty()) {
                System.err.println("Hóa đơn chưa có sản phẩm nào. Hủy tạo hóa đơn.");
                return;
            }

            invoice.setTotalAmount(totalAmount);
            invoice.setStatus("pending");

            int newInvoiceId = invoiceService.createInvoice(invoice, invoiceDetails);

            if (newInvoiceId > 0) {
                System.out.println("\u001B[32mTạo hóa đơn thành công với ID: " + newInvoiceId + "\u001B[0m");
                System.out.println("\u001B[32mTổng tiền: " + totalAmount + " VND\u001B[0m");
            } else {
                System.err.println("Tạo hóa đơn thất bại!");
            }

        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi khi tạo hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayAllInvoice() {
        System.out.println("\u001B[34m========= DANH SÁCH HÓA ĐƠN =========\u001B[0m");
        List<Invoice> invoices = invoiceService.getAllInvoices();

        if (invoices.isEmpty()) {
            System.out.println("Chưa có hóa đơn nào trong hệ thống.");
            return;
        }

        System.out.println("\u001B[36m===================================================================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-20s | %-20s | %-12s | %-10s |\u001B[0m%n",
                "ID", "Khách hàng", "Ngày tạo", "Tổng tiền", "Trạng thái");
        System.out.println("\u001B[36m===================================================================================\u001B[0m");

        for (Invoice invoice : invoices) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dateString = (invoice.getCreateAt() != null) ? dateFormat.format(invoice.getCreateAt()) : "N/A";

            String statusColor;
            switch (invoice.getStatus().toLowerCase()) {
                case "paid":
                    statusColor = "\u001B[32m";
                    break;
                case "pending":
                    statusColor = "\u001B[33m";
                    break;
                case "cancelled":
                    statusColor = "\u001B[31m";
                    break;
                default:
                    statusColor = "\u001B[37m";
            }

            System.out.printf("| %-5d | %-20s | %-20s | %-12.2f | %s%-10s\u001B[0m |\n",
                    invoice.getInvoiceId(),
                    invoice.getCustomerName(),
                    dateString,
                    invoice.getTotalAmount(),
                    statusColor,
                    invoice.getStatus());
        }
        System.out.println("\u001B[36m===================================================================================\u001B[0m");
    }

    private void displayInvoiceDetail() {
        System.out.println("\u001B[34m========= CHI TIẾT HÓA ĐƠN =========\u001B[0m");
        System.out.print("Nhập ID hóa đơn cần xem chi tiết: ");

        int invoiceId;
        try {
            invoiceId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("ID không hợp lệ");
            return;
        }

        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice == null) {
            System.err.println("Không tìm thấy hóa đơn với ID " + invoiceId);
            return;
        }

        System.out.println("\u001B[32m===== THÔNG TIN HÓA ĐƠN =====\u001B[0m");
        System.out.println("ID hóa đơn: " + invoice.getInvoiceId());
        System.out.println("Khách hàng: " + invoice.getCustomerName() + " (ID: " + invoice.getCustomerId() + ")");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = (invoice.getCreateAt() != null) ? dateFormat.format(invoice.getCreateAt()) : "N/A";
        System.out.println("Ngày tạo: " + dateString);

        System.out.println("Trạng thái: " + invoice.getStatus());
        System.out.println("Tổng tiền: " + invoice.getTotalAmount() + " VND");

        List<InvoiceDetail> details = invoiceService.getInvoiceDetails(invoiceId);

        if (details.isEmpty()) {
            System.out.println("Hóa đơn này không có chi tiết nào.");
            return;
        }

        System.out.println("\n\u001B[32m===== CHI TIẾT HÓA ĐƠN =====\u001B[0m");
        System.out.println("\u001B[36m==================================================================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-30s | %-8s | %-12s | %-12s |\u001B[0m%n",
                "STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("\u001B[36m==================================================================================\u001B[0m");

        int count = 1;
        for (InvoiceDetail detail : details) {
            Product product = productService.findProductById(detail.getProId());
            String productName = (product != null) ? product.getProName() : "Sản phẩm #" + detail.getProId();

            double amount = detail.getQuantity() * detail.getUnitPrice();

            System.out.printf("| %-5d | %-30s | %-8d | %-12.2f | %-12.2f |\n",
                    count++,
                    productName,
                    detail.getQuantity(),
                    detail.getUnitPrice(),
                    amount);
        }

        System.out.println("\u001B[36m==================================================================================\u001B[0m");
        System.out.printf("\u001B[32mTổng cộng: %.2f VND\u001B[0m\n", invoice.getTotalAmount());

        System.out.print("\nBạn có muốn cập nhật trạng thái hóa đơn không? (Y/N): ");
        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("\u001B[34m========= CẬP NHẬT TRẠNG THÁI =========\u001B[0m");
            System.out.println("1.| Đang chờ xử lý (pending)                    |");
            System.out.println("2. Đã xác nhận (confirmed)                      |");
            System.out.println("3. Đang giao hàng (processing)                  |");
            System.out.println("4. Đã giao hàng (delivered)                     |");
            System.out.println("5. Đã thanh toán (paid)                         |");
            System.out.println("6. Đã hủy (cancelled)                           |");
            System.out.println("7. Thất bại (failed)                            |");
            System.out.println("\u001B[34m=================================================\u001B[0m");
            System.out.print("Chọn trạng thái mới: ");

            String newStatus;
            try {
                int statusChoice = Integer.parseInt(scanner.nextLine());
                switch (statusChoice) {
                    case 1:
                        newStatus = "pending";
                        break;
                    case 2:
                        newStatus = "confirmed";
                        break;
                    case 3:
                        newStatus = "processing";
                        break;
                    case 4:
                        newStatus = "delivered";
                        break;
                    case 5:
                        newStatus = "paid";
                        break;
                    case 6:
                        newStatus = "cancelled";
                        break;
                    case 7:
                        newStatus = "failed";
                        break;
                    default:
                        System.err.println("Lựa chọn không hợp lệ!");
                        return;
                }

                boolean updated = invoiceService.updateInvoiceStatus(invoiceId, newStatus);
                if (updated) {
                    System.out.println("\u001B[32mCập nhật trạng thái thành công!\u001B[0m");
                } else {
                    System.err.println("Cập nhật trạng thái thất bại!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void searchInvoice() {
        System.out.println("\u001B[34m========= TÌM KIẾM HÓA ĐƠN =========\u001B[0m");
        System.out.println("1. Tìm theo ID khách hàng");
        System.out.println("2. Tìm theo khoảng thời gian");
        System.out.println("3. Tìm theo trạng thái");
        System.out.println("0. Quay lại");
        System.out.print("Lựa chọn của bạn: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Lựa chọn không hợp lệ");
            return;
        }

        List<Invoice> searchResults = null;

        switch (choice) {
            case 1:
                System.out.print("Nhập ID khách hàng: ");
                try {
                    int customerId = Integer.parseInt(scanner.nextLine());
                    searchResults = invoiceService.getInvoicesByCustomerId(customerId);
                } catch (NumberFormatException e) {
                    System.err.println("ID không hợp lệ");
                    return;
                }
                break;

            case 2:
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.setLenient(false);

                    System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
                    String startDateStr = scanner.nextLine();
                    Date startDate = dateFormat.parse(startDateStr);

                    System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
                    String endDateStr = scanner.nextLine();
                    Date endDate = dateFormat.parse(endDateStr);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(endDate);
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endDate = calendar.getTime();

                    searchResults = invoiceService.getInvoicesByDateRange(startDate, endDate);
                } catch (ParseException e) {
                    System.err.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy");
                    return;
                }
                break;

            case 3:
                System.out.println("Các trạng thái: pending, paid, cancelled, delivered, processing");
                System.out.print("Nhập trạng thái cần tìm: ");
                String status = scanner.nextLine().trim();
                searchResults = invoiceService.getInvoicesByStatus(status);
                break;

            case 0:
                return;

            default:
                System.err.println("Lựa chọn không hợp lệ");
                return;
        }

        if (searchResults == null || searchResults.isEmpty()) {
            System.out.println("Không tìm thấy hóa đơn nào.");
            return;
        }

        System.out.println("\u001B[36m=====================================================================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-20s | %-20s | %-12s | %-10s |\u001B[0m%n",
                "ID", "Khách hàng", "Ngày tạo", "Tổng tiền", "Trạng thái");
        System.out.println("\u001B[36m=====================================================================================\u001B[0m");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Invoice invoice : searchResults) {
            String dateString = (invoice.getCreateAt() != null) ? dateFormat.format(invoice.getCreateAt()) : "N/A";

            String statusColor;
            switch (invoice.getStatus().toLowerCase()) {
                case "paid":
                    statusColor = "\u001B[32m";
                    break;
                case "pending":
                    statusColor = "\u001B[33m";
                    break;
                case "cancelled":
                    statusColor = "\u001B[31m";
                    break;
                default:
                    statusColor = "\u001B[37m";
            }

            System.out.printf("| %-5d | %-20s | %-20s | %-12.2f | %s%-10s\u001B[0m |\n",
                    invoice.getInvoiceId(),
                    invoice.getCustomerName(),
                    dateString,
                    invoice.getTotalAmount(),
                    statusColor,
                    invoice.getStatus());
        }
        System.out.println("\u001B[36m=====================================================================================\u001B[0m");

        System.out.print("\nBạn có muốn xem chi tiết hóa đơn nào không? (Y/N): ");
        String viewChoice = scanner.nextLine().trim();

        if (viewChoice.equalsIgnoreCase("Y")) {
            System.out.print("Nhập ID hóa đơn cần xem chi tiết: ");
            try {
                int invoiceId = Integer.parseInt(scanner.nextLine());

                boolean found = false;
                for (Invoice invoice : searchResults) {
                    if (invoice.getInvoiceId() == invoiceId) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    displayInvoiceDetail(invoiceId);
                } else {
                    System.err.println("ID hóa đơn không có trong kết quả tìm kiếm.");
                }
            } catch (NumberFormatException e) {
                System.err.println("ID không hợp lệ");
            }
        }
    }

    private void displayInvoiceDetail(int invoiceId) {
        // Lấy thông tin hóa đơn
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice == null) {
            System.err.println("Không tìm thấy hóa đơn với ID " + invoiceId);
            return;
        }

        System.out.println("\u001B[32m===== THÔNG TIN HÓA ĐƠN =====\u001B[0m");
        System.out.println("ID hóa đơn: " + invoice.getInvoiceId());
        System.out.println("Khách hàng: " + invoice.getCustomerName() + " (ID: " + invoice.getCustomerId() + ")");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = (invoice.getCreateAt() != null) ? dateFormat.format(invoice.getCreateAt()) : "N/A";
        System.out.println("Ngày tạo: " + dateString);

        System.out.println("Trạng thái: " + invoice.getStatus());
        System.out.println("Tổng tiền: " + invoice.getTotalAmount() + " VND");

        List<InvoiceDetail> details = invoiceService.getInvoiceDetails(invoiceId);

        if (details.isEmpty()) {
            System.out.println("Hóa đơn này không có chi tiết nào.");
            return;
        }

        System.out.println("\n\u001B[32m===== CHI TIẾT HÓA ĐƠN =====\u001B[0m");
        System.out.println("\u001B[36m==========================================================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-30s | %-8s | %-12s | %-12s |\u001B[0m%n",
                "STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("\u001B[36m==========================================================================\u001B[0m");

        int count = 1;
        for (InvoiceDetail detail : details) {
            Product product = productService.findProductById(detail.getProId());
            String productName = (product != null) ? product.getProName() : "Sản phẩm #" + detail.getProId();

            double amount = detail.getQuantity() * detail.getUnitPrice();

            System.out.printf("| %-5d | %-30s | %-8d | %-12.2f | %-12.2f |\n",
                    count++,
                    productName,
                    detail.getQuantity(),
                    detail.getUnitPrice(),
                    amount);
        }

        System.out.println("\u001B[36m==========================================================================\u001B[0m");
        System.out.printf("\u001B[32mTổng cộng: %.2f VND\u001B[0m\n", invoice.getTotalAmount());
    }
}