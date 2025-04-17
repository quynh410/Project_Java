package ra.edu.presentation;
import ra.edu.business.model.Customer;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;

import java.util.List;
import java.util.Scanner;

public class CustomerUI {
    private final CustomerService customerService = new CustomerServiceImp();
    private final Scanner scanner = new Scanner(System.in);

    public void menuCustomer() {
        int choice;
        do {
            System.out.println("===== QUẢN LÝ KHÁCH HÀNG =====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Hiển thị danh sách khách hàng");
            System.out.println("3. Sửa thông tin khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("0. Quay lại");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    showAllCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
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

    private void addCustomer() {

    }

    private void showAllCustomers() {

    }

    private void updateCustomer() {

    }

    private void deleteCustomer() {

    }
}