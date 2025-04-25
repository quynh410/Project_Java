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
            System.out.println("========= QUẢN LÝ SẢN PHẨM =========");
            System.out.println("|1. Thêm sản phẩm                   |");
            System.out.println("|2. Hiển thị danh sách sản phẩm     |");
            System.out.println("|3. Sửa thông tin sản phẩm          |");
            System.out.println("|4. Xóa sản phẩm                    |");
            System.out.println("|5. Tìm kiếm sản phẩm theo ID       |");
            System.out.println("|6. Tìm kiếm sản phẩm theo giá      |");
            System.out.println("|7. Tìm kiếm sản phẩm theo tồn kho  |");
            System.out.println("|8. Tìm kiếm sản phẩm theo Brand    |");
            System.out.println("|0. Quay lại                        |");
            System.out.println("=====================================");
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
                case 6:
                    SearchProductByPriceRange();
                    break;
                case 7:
                    findProductByStock();
                    break;
                case 8:
                    findProductByBrand();
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
        System.out.println("\u001B[34m========= THÊM SẢN PHẨM MỚI =========\u001B[0m");

        Product p = new Product();
        p.inputData(scanner);

        productService.addProduct(p);
        System.out.println("\u001B[32mThêm sản phẩm thành công!\u001B[0m");

        System.out.println("\u001B[34m================================== SẢN PHẨM ĐÃ THÊM ==================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-25s | %-15s | %-15s | %-10s |\u001B[0m%n",
                "ID", "Tên sản phẩm", "Thương hiệu", "Giá (VND)", "Tồn kho");
        System.out.println("\u001B[36m--------------------------------------------------------------------------------------\u001B[0m");

        System.out.printf("\u001B[32 m| %-5d | %-25s | %-15s | %,15.0f | %-10d |\u001B[0m%n",
                p.getProId(),
                p.getProName(),
                p.getProBrand(),
                p.getProPrice(),
                p.getStock());
                p.isStatus();
    }



    private void displayAll() {
        List<Product> products = productService.findAllProducts();

        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            System.out.println("\u001B[34m======================================= DANH SÁCH SẢN PHẨM =======================================\u001B[0m");

            System.out.printf("\u001B[36m| %-5s | %-20s | %-15s | %-15s | %-10s |%-15s |\u001B[0m%n",
                    "ID", "Tên sản phẩm", "Thương hiệu", "Giá (VND)", "Tồn kho", "Trạng thái");

            System.out.println("----------------------------------------------------------------------------------------------------");

            for (Product p : products) {
                System.out.printf("\u001B[32m| %-5d | %-20s | %-15s | %,15.0f | %-10d |%-15s |\u001B[0m%n",
                        p.getProId(),
                        p.getProName(),
                        p.getProBrand(),
                        p.getProPrice(),
                        p.getStock(),
                        p.isStatus() ? "Hoạt động" : "Không hoạt động");
            }

        }
    }

    private void updateProduct() {
        System.out.println("\u001B[34m=============================== CẬP NHẬT THÔNG TIN SẢN PHẨM =============================\u001B[0m");
        System.out.print("Nhập ID sản phẩm cần sửa: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product existing = productService.findProductById(id);

            if (existing == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
                return;
            }

            System.out.println("\u001B[36m============================== THÔNG TIN SẢN PHẨM HIỆN TẠI ==============================\u001B[0m");
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s%n", "ID", existing.getProId());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s%n", "Tên sản phẩm", existing.getProName());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s%n", "Thương hiệu", existing.getProBrand());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %.2f VND%n", "Giá", existing.getProPrice());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %d%n", "Số lượng tồn kho", existing.getStock());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s%n", "Trạng thái", existing.isStatus());
            System.out.println("\u001B[36m=========================================================================================\u001B[0m");



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
            System.out.println("\u001B[35mBạn có muốn cập nhật trạng thái ko ? (Y/N) :\u001B[0m");
            if(scanner.nextLine().equalsIgnoreCase("Y")) {
                System.out.println("Trạng thái mới true/false: ");
                String statusStr = scanner.nextLine();
                if (statusStr.equalsIgnoreCase("true") || statusStr.equalsIgnoreCase("false")) {
                    existing.setStatus(Boolean.parseBoolean(statusStr));
                } else {
                    System.out.println("Trạng thái không hợp lệ, giữ nguyên trạng thái cũ");
                }
            }

            productService.updateProduct(existing);
            System.out.println("Cập nhật sản phẩm thành công!");

        } catch (NumberFormatException e) {
            System.err.println("ID không hợp lệ!");
        }
    }

    private void deleteProduct() {
        System.out.println("\u001B[34m=============================== XÓA SẢN PHẨM ===============================\u001B[0m");
        System.out.print("Nhập ID sản phẩm cần xóa: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product product = productService.findProductById(id);

            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
                return;
            }

            System.out.println("\n\u001B[33m====== Thông tin sản phẩm xóa ======\u001B[0m");
            System.out.printf("%-10s: %s\n", "ID", product.getProId());
            System.out.printf("%-10s: %s\n", "Tên", product.getProName());
            System.out.printf("%-10s: %.2f\n", "Giá", product.getProPrice());
            System.out.printf("%-10s: %d\n", "Tồn kho", product.getStock());
            System.out.println("\n\u001B[33m------------------------------------------------\n\u001B[0m");

            System.out.print("\u001B[35mBạn có chắc chắn muốn xóa sản phẩm này? (Y/N): \u001B[0m");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                productService.deleteProduct(id);
                System.out.println("\u001B[32mXóa sản phẩm thành công!\u001B[0m");
            } else {
                System.out.println("Đã hủy thao tác xóa!");
            }

        } catch (NumberFormatException e) {
            System.err.println("ID không hợp lệ!");
        }
    }


    private void findProductById() {
        System.out.println("\u001B[0m===== TÌM KIẾM SẢN PHẨM =====\u001B[0m");
        System.out.print("Nhập ID sản phẩm cần tìm: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Product product = productService.findProductById(id);

            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
            } else {
                System.out.println("\u001B[32m========Thông tin sản phẩm========\u001B[0m");
                System.out.printf("\u001B[33mID: %d%n\u001B[0m", product.getProId());
                System.out.printf("\u001B[33mTên: %s%n\u001B[0m", product.getProName());
                System.out.printf("\u001B[33mThương hiệu: %s%n\u001B[0m", product.getProBrand());
                System.out.printf("\u001B[33mGiá: %,.0f VND%n\u001B[0m", product.getProPrice());
                System.out.printf("\u001B[33mTồn kho: %d%n\u001B[0m", product.getStock());
                System.out.println("\u001B[32m==================================\u001B[0m");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
        }
    }
    private void SearchProductByPriceRange() {
        System.out.println("===== TÌM KIẾM SẢN PHẨM THEO KHOẢNG GIÁ =====");
        double minPrice = 0;
        double maxPrice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Nhập giá tối thiểu (VND): ");
                minPrice = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Nhập giá tối đa (VND): ");
                maxPrice = Double.parseDouble(scanner.nextLine().trim());

                if (minPrice < 0 || maxPrice < 0) {
                    System.out.println("Giá không được âm. Vui lòng nhập lại.");
                } else if (minPrice > maxPrice) {
                    System.out.println("Giá tối thiểu không được lớn hơn giá tối đa. Vui lòng nhập lại.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Giá trị không hợp lệ. Vui lòng nhập một số.");
            }
        }

        List<Product> result = productService.findProductsByPriceRange(minPrice, maxPrice);
        displaySearchResults(result, "khoảng giá " + String.format("%,.0f", minPrice) + " - " + String.format("%,.0f", maxPrice) + " VND");
    }

    private void findProductByStock() {
        System.out.println("===== TÌM KIẾM SẢN PHẨM THEO SỐ LƯỢNG TỒN KHO =====");
        int minStock = 0;
        int maxStock = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Nhập số lượng tồn kho tối thiểu: ");
                minStock = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Nhập số lượng tồn kho tối đa: ");
                maxStock = Integer.parseInt(scanner.nextLine().trim());

                if (minStock < 0 || maxStock < 0) {
                    System.err.println("Số lượng tồn kho không được âm. Vui lòng nhập lại.");
                } else if (minStock > maxStock) {
                    System.err.println("Số lượng tối thiểu không được lớn hơn số lượng tối đa. Vui lòng nhập lại.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Giá trị không hợp lệ. Vui lòng nhập một số nguyên.");
            }
        }

        List<Product> result = productService.findProductsByStockRange(minStock, maxStock);
        displaySearchResults(result, "số lượng tồn kho " + minStock + " - " + maxStock);
    }

    private void findProductByBrand() {
        System.out.println("===== TÌM KIẾM SẢN PHẨM THEO THƯƠNG HIỆU =====");
        System.out.print("Nhập tên thương hiệu cần tìm: ");
        String searchBrand = scanner.nextLine().trim();

        if (searchBrand.isEmpty()) {
            System.err.println("Tên thương hiệu không được để trống!");
            return;
        }

        List<Product> result = productService.findProductsByBrand(searchBrand);
        displaySearchResults(result, "thương hiệu \"" + searchBrand + "\"");
    }

    private void displaySearchResults(List<Product> results, String searchCriteria) {
        if (results.isEmpty()) {
            System.err.println("Không tìm thấy sản phẩm nào với " + searchCriteria + ".");
        } else {
            System.out.println("\u001B[34m===== KẾT QUẢ TÌM KIẾM: " + results.size() + " SẢN PHẨM VỚI " + searchCriteria.toUpperCase() + " =====\u001B[0m");

            System.out.printf("\u001B[36m| %-5s | %-20s | %-15s | %-15s | %-10s |\u001B[0m%n",
                    "ID", "Tên sản phẩm", "Thương hiệu", "Giá (VND)", "Tồn kho");

            System.out.println("----------------------------------------------------------------------------");

            for (Product p : results) {
                System.out.printf("\u001B[32m| %-5d | %-20s | %-15s | %,15.0f | %-10d |\u001B[0m%n",
                        p.getProId(),
                        p.getProName(),
                        p.getProBrand(),
                        p.getProPrice(),
                        p.getStock());
            }
        }
    }
}