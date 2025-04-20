package ra.edu.validate;

import ra.edu.business.model.Customer;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static boolean isValidDate(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date getValidDate(String message, Scanner scanner, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        System.out.println(message);
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                Date date = dateFormat.parse(input);
                return date;
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng " + format);
            }
        }
    }

    public static boolean isValidPhone(String phone) {
        // Vietnamese phone number validation: start with 0, followed by 9-10 digits
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

    public static int getValidInt(String message, Scanner scanner, int min, int max) {
        System.out.println(message);
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                throw new IllegalArgumentException("Giá trị phải nằm trong khoảng từ " + min + " đến " + max);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static double getValidDouble(String message, Scanner scanner, double min, double max) {
        System.out.println(message);
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                throw new IllegalArgumentException("Giá trị phải nằm trong khoảng từ " + min + " đến " + max);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số thực hợp lệ.");
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