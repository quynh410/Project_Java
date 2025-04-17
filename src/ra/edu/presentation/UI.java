package ra.edu.presentation;

import ra.edu.business.service.admin.AdminService;

import java.util.Scanner;

public class UI {
    private AdminService adminService;
    private Scanner scanner;

    public UI() {
        this.adminService = new AdminService();
        this.scanner = new Scanner(System.in);
    }

    public boolean showLoginScreen() {
        System.out.println("===== ĐĂNG NHẬP HỆ THỐNG =====");

        boolean loginSuccess = false;
        int attempts = 0;

        while (!loginSuccess && attempts < 3) {
            System.out.print("Tên đăng nhập: ");
            String username = scanner.nextLine();

            System.out.print("Mật khẩu: ");
            String password = readPassword();

            loginSuccess = adminService.login(username, password);

            if (!loginSuccess) {
                System.out.println("Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng thử lại.");
                attempts++;
            }
        }

        if (loginSuccess) {
            return true;
        } else {
            System.out.println("Đăng nhập thất bại. Quá số lần thử.");
            return false;
        }
    }

    public void logout() {
        adminService.logout();
        System.out.println("Đã đăng xuất khỏi hệ thống.");
    }

    private String readPassword() {
        StringBuilder password = new StringBuilder();
        char input;

        try {
            while ((input = (char) System.console().reader().read()) != '\n' && input != '\r') {
                password.append(input);
                System.out.print("*");
            }
            System.out.println();
        } catch (Exception e) {
            String pass = scanner.nextLine();
            for (int i = 0; i < pass.length(); i++) {
                password.append(pass.charAt(i));
            }
        }

        return password.toString();
    }
}