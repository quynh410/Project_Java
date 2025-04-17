package ra.edu.presentation;

import java.util.Scanner;

public class InvoiceUI {
    private final Scanner scanner = new Scanner(System.in);

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
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }
}