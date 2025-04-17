package ra.edu.validate;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminValidate {
    public static boolean validateUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9_]{6,30}$", username);
    }

    public static boolean validatePassword(String password) {
        // Mật khẩu phải:
        // - Có ít nhất 8 ký tự
        // - Chứa ít nhất một chữ cái in hoa
        // - Chứa ít nhất một chữ cái in thường
        // - Chứa ít nhất một chữ số
        // - Chứa ít nhất một ký tự đặc biệt

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    public static String getValidUsername(Scanner scanner) {
        String username;
        do {
            System.out.print("Nhập tên đăng nhập (6-30 ký tự, chỉ bao gồm chữ cái, số và dấu gạch dưới): ");
            username = scanner.nextLine().trim();
            if (!validateUsername(username)) {
                System.out.println("Tên đăng nhập không hợp lệ.");
            }
        } while (!validateUsername(username));
        return username;
    }

    public static String getValidPassword(Scanner scanner) {
        String password;
        do {
            System.out.print("Nhập mật khẩu (ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt): ");
            password = scanner.nextLine().trim();
            if (!validatePassword(password)) {
                System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt.");
            }
        } while (!validatePassword(password));
        return password;
    }

    public static boolean validateStatus(String status) {
        return status.equalsIgnoreCase("active") || status.equalsIgnoreCase("inactive");
    }

    public static String getValidStatus(Scanner scanner) {
        String status;
        do {
            System.out.print("Nhập trạng thái (active/inactive): ");
            status = scanner.nextLine().trim().toLowerCase();
            if (!validateStatus(status)) {
                System.out.println("Trạng thái không hợp lệ. Vui lòng nhập 'active' hoặc 'inactive'.");
            }
        } while (!validateStatus(status));
        return status;
    }

    public static boolean statusToBit(String status) {
        return status.equalsIgnoreCase("active");
    }

    public static String bitToStatus(boolean bit) {
        return bit ? "active" : "inactive";
    }
}