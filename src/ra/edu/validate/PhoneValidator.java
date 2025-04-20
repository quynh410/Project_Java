package ra.edu.validate;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PhoneValidator {

    public static boolean validateVietnamesePhone(String phone) {
        return Pattern.matches("^0\\d{9,10}$", phone);
    }

    public static String getValidVietnamesePhone(Scanner scanner) {
        String phone;
        do {
            phone = scanner.nextLine().trim();
            if (!validateVietnamesePhone(phone)) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.");
            }
        } while (!validateVietnamesePhone(phone));
        return phone;
    }

    // Lấy nhà mạng dựa trên đầu số
    public static String getNetworkProvider(String phone) {
        if (!validateVietnamesePhone(phone)) {
            return "Không phải số điện thoại Việt Nam";
        }

        String prefix = phone.substring(0, 3);

        switch (prefix) {
            case "086":
            case "096":
            case "097":
            case "098":
            case "032":
            case "033":
            case "034":
            case "035":
            case "036":
            case "037":
            case "038":
            case "039":
                return "Viettel";
            case "088":
            case "091":
            case "094":
            case "083":
            case "084":
            case "085":
            case "081":
            case "082":
                return "Vinaphone";
            case "089":
            case "090":
            case "093":
            case "070":
            case "079":
            case "077":
            case "076":
            case "078":
                return "Mobifone";
            case "092":
            case "056":
            case "058":
                return "Vietnamobile";
            case "099":
            case "059":
                return "Gmobile";
            default:
                return "Không xác định";
        }
    }
}
