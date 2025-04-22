package ra.edu;

import ra.edu.presentation.*;
//import ra.edu.presentation.InvoiceUI;

import java.util.Scanner;

public class Main {
    private static UI loginController;
    private static Scanner scanner;

    public static void main(String[] args) {
        loginController = new UI();
        scanner = new Scanner(System.in);

        boolean isRunning = true;

        System.out.println("===== PHẦN MỀM QUẢN LÝ ĐIỆN THOẠI =====");
        boolean loggedIn = loginController.showLoginScreen();

        if (loggedIn) {
            while (isRunning) {
                showMainMenu();
                int choice = getChoice();

                switch (choice) {
                    case 1:
                        // Quản lý khách hàng
                        CustomerUI customerUI = new CustomerUI();
                        customerUI.menuCustomer();
                        break;

                    case 2:
                        // Quản lý sản phẩm
                        ProductUI productUI = new ProductUI();
                        productUI.menuProduct();
                        break;

                    case 3:
                        // Quản lý hóa đơn
                        InvoiceUI invoiceUI = new InvoiceUI();
                        invoiceUI.menuInvoice();
                        break;
                    case 4 :
                        // thống theo ngày,tháng, năm
                        StatisticsUI statisticsUI = new StatisticsUI();
                        statisticsUI.menuStatistics();
                        break;
                    case 0:
                        // Đăng xuất
                        loginController.logout();
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                        break;
                }
            }
        }

        System.out.println("===== KẾT THÚC CHƯƠNG TRÌNH =====");
    }

    private static void showMainMenu() {
        System.out.println("\n======== MENU CHÍNH ========");
        System.out.println("|1. Quản lý khách hàng     |");
        System.out.println("|2. Quản lý sản phẩm       |");
        System.out.println("|3. Quản lý hóa đơn        |");
        System.out.println("|4. Thống kê               |");
        System.out.println("|0. Đăng xuất              |");
        System.out.println("============================");
        System.out.print("Lựa chọn của bạn: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
