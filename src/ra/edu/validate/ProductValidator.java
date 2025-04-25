package ra.edu.validate;
import java.util.Scanner;

public class ProductValidator {

    public static boolean validateProductName(String name) {
        return Validator.isValidText(name, 2, 100);
    }

    public static boolean validateBrand(String brand) {
        return Validator.isValidText(brand, 1, 50);
    }

    public static boolean validatePrice(double price) {
        return price > 0;
    }

    public static boolean validateStock(int stock) {
        return stock >= 0;
    }

    // Nhập và kiểm tra tên sản phẩm
    public static String getValidProductName(Scanner scanner) {
        String name;
        do {
            name = scanner.nextLine().trim();
            if (!validateProductName(name)) {
                System.out.println("Tên sản phẩm không hợp lệ. Vui lòng nhập từ 2 đến 100 ký tự.");
            }
        } while (!validateProductName(name));
        return name;
    }

    public static String getValidBrand(Scanner scanner) {
        String brand;
        do {
            brand = scanner.nextLine().trim();
            if (!validateBrand(brand)) {
                System.out.println("Thương hiệu không hợp lệ. Vui lòng nhập tên thương hiệu không rỗng (tối đa 50 ký tự).");
            }
        } while (!validateBrand(brand));
        return brand;
    }

    public static double getValidPrice(Scanner scanner) {
        double price;
        do {
            try {
                price = Double.parseDouble(scanner.nextLine().trim());
                if (!validatePrice(price)) {
                    System.out.println("Giá không hợp lệ. Giá phải lớn hơn 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số hợp lệ.");
                price = -1;
            }
        } while (!validatePrice(price));
        return price;
    }

    public static int getValidStock(Scanner scanner) {
        int stock;
        do {
            try {
                stock = Integer.parseInt(scanner.nextLine().trim());
                if (!validateStock(stock)) {
                    System.out.println("Số lượng tồn kho không hợp lệ. Phải lớn hơn hoặc bằng 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ. Vui lòng nhập một số nguyên.");
                stock = -1;
            }
        } while (!validateStock(stock));
        return stock;
    }
}
