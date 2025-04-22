package ra.edu.validate;

import ra.edu.business.model.Invoice;
import ra.edu.business.model.InvoiceDetail;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InvoiceValidator {

    // Validate invoice customer ID
    public static boolean validateCustomerId(int customerId) {
        return customerId > 0;
    }

    // Validate invoice total amount
    public static boolean validateTotalAmount(Double totalAmount) {
        return totalAmount != null && totalAmount >= 0;
    }

    // Validate invoice status
    public static boolean validateInvoiceStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        // Status values according to database enum: pending, confrimed, delivered, falied
        return status.equalsIgnoreCase("pending") ||
                status.equalsIgnoreCase("confrimed") ||
                status.equalsIgnoreCase("delivered") ||
                status.equalsIgnoreCase("falied");
    }

    // Validate invoice date
    public static boolean validateInvoiceDate(Date date) {
        if (date == null) {
            return false;
        }
        // Invoice date should not be in the future
        return !date.after(new Date());
    }

    // Validate invoice details
    public static boolean validateInvoiceDetails(List<InvoiceDetail> details) {
        if (details == null || details.isEmpty()) {
            return false;
        }

        for (InvoiceDetail detail : details) {
            if (!validateInvoiceDetail(detail)) {
                return false;
            }
        }

        return true;
    }

    // Validate a single invoice detail
    public static boolean validateInvoiceDetail(InvoiceDetail detail) {
        if (detail == null) {
            return false;
        }

        // Validate product ID
        if (detail.getProId() <= 0) {
            return false;
        }

        // Validate quantity
        if (detail.getQuantity() <= 0) {
            return false;
        }

        // Validate unit price
        if (detail.getUnitPrice() <= 0) {
            return false;
        }

        return true;
    }

    // Complete validation of invoice
    public static boolean validateInvoice(Invoice invoice) {
        if (invoice == null) {
            System.out.println("\u001B[31mĐối tượng hóa đơn không được null.\u001B[0m");
            return false;
        }

        if (!validateCustomerId(invoice.getCustomerId())) {
            System.out.println("\u001B[31mID khách hàng không hợp lệ.\u001B[0m");
            return false;
        }

        if (!validateTotalAmount(invoice.getTotalAmount())) {
            System.out.println("\u001B[31mTổng tiền không hợp lệ.\u001B[0m");
            return false;
        }

        if (invoice.getStatus() != null && !validateInvoiceStatus(invoice.getStatus())) {
            System.out.println("\u001B[31mTrạng thái hóa đơn không hợp lệ. Phải là một trong: pending, confrimed, delivered, falied\u001B[0m");
            return false;
        }

        if (invoice.getCreateAt() != null && !validateInvoiceDate(invoice.getCreateAt())) {
            System.out.println("\u001B[31mNgày tạo hóa đơn không hợp lệ.\u001B[0m");
            return false;
        }

        return true;
    }

    // Helper methods for user input

    // Get valid customer ID from scanner
    public static int getValidCustomerId(Scanner scanner) {
        int customerId;
        do {
            try {
                System.out.print("Nhập ID khách hàng: ");
                customerId = Integer.parseInt(scanner.nextLine().trim());
                if (!validateCustomerId(customerId)) {
                    System.out.println("ID khách hàng không hợp lệ. Vui lòng nhập ID > 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ. Vui lòng nhập một số nguyên.");
                customerId = -1;
            }
        } while (!validateCustomerId(customerId));
        return customerId;
    }

    // Get valid total amount from scanner
    public static double getValidTotalAmount(Scanner scanner) {
        Double totalAmount;
        do {
            try {
                System.out.print("Nhập tổng tiền hóa đơn: ");
                totalAmount = Double.parseDouble(scanner.nextLine().trim());
                if (!validateTotalAmount(totalAmount)) {
                    System.out.println("Tổng tiền không hợp lệ. Vui lòng nhập số >= 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số thực.");
                totalAmount = -1.0;
            }
        } while (!validateTotalAmount(totalAmount));
        return totalAmount;
    }

    public static String getValidStatus(Scanner scanner) {
        String status;
        do {
            System.out.print("Nhập trạng thái hóa đơn (pending/confrimed/delivered/falied): ");
            status = scanner.nextLine().trim();
            if (!validateInvoiceStatus(status)) {
                System.out.println("Trạng thái không hợp lệ. Vui lòng nhập một trong các trạng thái: pending, confrimed, delivered, falied");
            }
        } while (!validateInvoiceStatus(status));
        return status;
    }

    public static int getValidProductId(Scanner scanner) {
        int productId;
        do {
            try {
                System.out.print("Nhập ID sản phẩm: ");
                productId = Integer.parseInt(scanner.nextLine().trim());
                if (productId <= 0) {
                    System.out.println("ID sản phẩm không hợp lệ. Vui lòng nhập ID > 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ. Vui lòng nhập một số nguyên.");
                productId = -1;
            }
        } while (productId <= 0);
        return productId;
    }

    public static int getValidQuantity(Scanner scanner) {
        int quantity;
        do {
            try {
                System.out.print("Nhập số lượng: ");
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Số lượng không hợp lệ. Vui lòng nhập số lượng > 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số nguyên.");
                quantity = -1;
            }
        } while (quantity <= 0);
        return quantity;
    }

    public static double getValidUnitPrice(Scanner scanner) {
        double unitPrice;
        do {
            try {
                System.out.print("Nhập đơn giá: ");
                unitPrice = Double.parseDouble(scanner.nextLine().trim());
                if (unitPrice <= 0) {
                    System.out.println("Đơn giá không hợp lệ. Vui lòng nhập đơn giá > 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số thực.");
                unitPrice = -1;
            }
        } while (unitPrice <= 0);
        return unitPrice;
    }
}