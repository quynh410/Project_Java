package ra.edu.validate;
import java.util.Scanner;

public class ProductValidator {

    // Kiểm tra tên sản phẩm
    public static boolean validateProductName(String name) {
        return Validator.isValidText(name, 6, 100);
    }

    // Kiểm tra thương hiệu
    public static boolean validateBrand(String brand) {
        return Validator.isValidText(brand, 1, 50);
    }

    // Kiểm tra giá sản phẩm
    public static boolean validatePrice(double price) {
        return price > 0;
    }

    // Kiểm tra số lượng tồn kho
    public static boolean validateStock(int stock) {
        return stock >= 0;
    }

    // Nhập và kiểm tra tên sản phẩm
    public static String getValidProductName(Scanner scanner) {
        String name;
        do {
            System.out.print("Nhập tên sản phẩm (6-100 ký tự): ");
            name = scanner.nextLine().trim();
            if (!validateProductName(name)) {
                System.out.println("Tên sản phẩm không hợp lệ. Vui lòng nhập từ 6 đến 100 ký tự.");
            }
        } while (!validateProductName(name));
        return name;
    }

    // Nhập và kiểm tra thương hiệu
    public static String getValidBrand(Scanner scanner) {
        String brand;
        do {
            System.out.print("Nhập tên thương hiệu: ");
            brand = scanner.nextLine().trim();
            if (!validateBrand(brand)) {
                System.out.println("Thương hiệu không hợp lệ. Vui lòng nhập tên thương hiệu không rỗng (tối đa 50 ký tự).");
            }
        } while (!validateBrand(brand));
        return brand;
    }

    // Nhập và kiểm tra giá sản phẩm
    public static double getValidPrice(Scanner scanner) {
        double price;
        do {
            System.out.print("Nhập giá sản phẩm: ");
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

    // Nhập và kiểm tra số lượng tồn kho
    public static int getValidStock(Scanner scanner) {
        int stock;
        do {
            System.out.print("Nhập số lượng tồn kho: ");
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
