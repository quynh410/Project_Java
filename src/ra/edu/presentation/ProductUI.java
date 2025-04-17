package ra.edu.presentation;

import ra.edu.business.model.Product;
import ra.edu.business.service.product.ProductService;
import ra.edu.business.service.product.ProductServiceImp;

import java.util.List;
import java.util.Scanner;

public class ProductUI {
    private final ProductService productService = new ProductServiceImp();
    private final Scanner scanner = new Scanner(System.in);

    public void menuProduct() {
        int choice;
        do {
            System.out.println("===== QUẢN LÝ SẢN PHẨM =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Sửa thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo ID");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayAll();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    findProductById();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private void addProduct() {
        System.out.println("===== THÊM SẢN PHẨM MỚI =====");
        Product p = new Product();
        p.inputData(scanner); // Sử dụng phương thức từ IApp

        productService.addProduct(p);
        System.out.println("Thêm sản phẩm thành công!");
    }

    private void displayAll() {
        List<Product> products = productService.findAllProducts();

        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            System.out.println("===== DANH SÁCH SẢN PHẨM =====");
            System.out.printf("%-5s | %-20s | %-15s | %-15s | %-10s%n",
                    "ID", "Tên sản phẩm", "Thương hiệu", "Giá (VND)", "Tồn kho");
            System.out.println("------------------------------------------------------------------------");

            for (Product p : products) {
                System.out.printf("%-5d | %-20s | %-15s | %,15.0f | %-10d%n",
                        p.getProId(),
                        p.getProName(),
                        p.getProBrand(),
                        p.getProPrice(),
                        p.getStock());
            }
        }
    }

    private void updateProduct() {
        System.out.println("===== CẬP NHẬT THÔNG TIN SẢN PHẨM =====");
        System.out.print("Nhập ID sản phẩm cần sửa: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product existing = productService.findProductById(id);

            if (existing == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
                return;
            }

            System.out.println("Thông tin sản phẩm hiện tại:");
            System.out.println(existing);

            System.out.println("\nNhập thông tin mới (nhấn Enter để giữ nguyên):");

            System.out.print("Tên sản phẩm: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                existing.setProName(name);
            }

            System.out.print("Thương hiệu: ");
            String brand = scanner.nextLine();
            if (!brand.isEmpty()) {
                existing.setProBrand(brand);
            }

            System.out.print("Giá (VND): ");
            String priceStr = scanner.nextLine();
            if (!priceStr.isEmpty()) {
                try {
                    existing.setProPrice(Double.parseDouble(priceStr));
                } catch (NumberFormatException e) {
                    System.out.println("Giá không hợp lệ, giữ nguyên giá cũ");
                }
            }

            System.out.print("Số lượng tồn kho: ");
            String stockStr = scanner.nextLine();
            if (!stockStr.isEmpty()) {
                try {
                    existing.setStock(Integer.parseInt(stockStr));
                } catch (NumberFormatException e) {
                    System.out.println("Số lượng không hợp lệ, giữ nguyên số lượng cũ");
                }
            }

            productService.updateProduct(existing);
            System.out.println("Cập nhật sản phẩm thành công!");

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
        }
    }

    private void deleteProduct() {
        System.out.println("===== XÓA SẢN PHẨM =====");
        System.out.print("Nhập ID sản phẩm cần xóa: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product product = productService.findProductById(id);

            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
                return;
            }

            System.out.println("Thông tin sản phẩm sẽ bị xóa:");
            System.out.println(product);

            System.out.print("Bạn có chắc chắn muốn xóa sản phẩm này? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                productService.deleteProduct(id);
                System.out.println("Xóa sản phẩm thành công!");
            } else {
                System.out.println("Đã hủy thao tác xóa!");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
        }
    }

    private void findProductById() {
        System.out.println("===== TÌM KIẾM SẢN PHẨM =====");
        System.out.print("Nhập ID sản phẩm cần tìm: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product product = productService.findProductById(id);

            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
            } else {
                System.out.println("Thông tin sản phẩm:");
                System.out.printf("ID: %d%n", product.getProId());
                System.out.printf("Tên: %s%n", product.getProName());
                System.out.printf("Thương hiệu: %s%n", product.getProBrand());
                System.out.printf("Giá: %,.0f VND%n", product.getProPrice());
                System.out.printf("Tồn kho: %d%n", product.getStock());
            }

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
        }
    }
}