package ra.edu.validate;


import java.util.Scanner;
import java.util.regex.Pattern;
public class Validator {
    public static String validateEmail(Scanner scanner) {
        while (true) {
            try {
                String inputString = scanner.nextLine();
                if (Pattern.matches("^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$", inputString)) {
                    return inputString;
                }
                throw new IllegalArgumentException("Dữ liệu nhập vào không hợp lệ, vui lòng nhập lại");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static String validateInputString(Scanner scanner, StringRule stringRule) {
        while (true) {
            try {
                String inputString = scanner.nextLine().trim();
                if (stringRule.isValidString(inputString)) {
                    return inputString;
                }
                throw new IllegalArgumentException("Dữ liệu nhập vào không hợp lệ, vui lòng nhập lại");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static boolean isValidText(String text, int minLength, int maxLength) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        int length = text.trim().length();

        return length >= minLength && length <= maxLength;

    }

    public static boolean isValidPhone(String phone) {
        return Pattern.matches("^0\\d{9,10}$", phone);
    }

    public static String getValidPhone(String message, Scanner scanner) {
        System.out.println(message);
        while (true) {
            try {
                String phone = scanner.nextLine().trim();
                if (isValidPhone(phone)) {
                    return phone;
                }
                throw new IllegalArgumentException("Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public static <E extends Enum<E>> E getValidEnum(Scanner scanner, Class<E> enumClass) {
        while (true) {
            try {
                String input = scanner.nextLine().trim().toUpperCase();
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Giá trị không hợp lệ. Các giá trị hợp lệ là: " + String.join(", ", getEnumNames(enumClass)));
            }
        }
    }

    private static <E extends Enum<E>> String[] getEnumNames(Class<E> enumClass) {
        E[] enumConstants = enumClass.getEnumConstants();
        String[] names = new String[enumConstants.length];
        for (int i = 0; i < enumConstants.length; i++) {
            names[i] = enumConstants[i].name();
        }
        return names;
    }


}