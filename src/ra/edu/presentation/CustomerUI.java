package ra.edu.presentation;

import ra.edu.business.model.Customer;
import ra.edu.business.service.customer.CustomerService;
import ra.edu.business.service.customer.CustomerServiceImp;
import ra.edu.validate.CustomerValidator;
import ra.edu.validate.Validator;

import java.util.List;
import java.util.Scanner;

public class CustomerUI {
    private final CustomerService customerService = new CustomerServiceImp();
    private final Scanner scanner = new Scanner(System.in);

    public void menuCustomer() {
        int choice;
        do {
            System.out.println("\u001B[34m========= QUẢN LÝ KHÁCH HÀNG ==========\u001B[0m");
            System.out.println("|1. Thêm mới khách hàng               |");
            System.out.println("|2. Hiển thị danh sách khách hàng     |");
            System.out.println("|3. Cập nhật thông tin khách hàng     |");
            System.out.println("|4. Xóa khách hàng                    |");
            System.out.println("|5. Tìm kiếm theo ID                  |");
            System.out.println("|0. Quay lại                          |");
            System.out.println("=======================================");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addCustomers();
                    break;
                case 2:
                    displayAllCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    findCustomerById();
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

    private void displayAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();

        if (customers.isEmpty()) {
            System.err.println("Chưa có khách hàng nào trong hệ thống.");
            return;
        }

        System.out.println("\u001B[34m============================================== DANH SÁCH KHÁCH HÀNG ==============================================\u001B[0m");
        System.out.printf("\u001B[36m| %-5s | %-20s | %-15s | %-25s | %-20s | %-10s |\u001B[0m\n",
                "ID", "Tên", "SĐT", "Email", "Địa chỉ", "Giới tính");
        System.out.println("\u001B[36m------------------------------------------------------------------------------------------------------------------\u001B[0m");

        for (Customer customer : customers) {
            System.out.printf("\u001B[32m| %-5d | %-20s | %-15s | %-25s | %-20s | %-10s |\u001B[0m\n",
                    customer.getCusId(),
                    customer.getCusName(),
                    customer.getCusPhone(),
                    customer.getCusEmail(),
                    customer.getCusAddress(),
                    customer.getCusGender());
        }

    }

    private void addCustomers() {
        System.out.println("\u001B[34m========= THÊM MỚI KHÁCH HÀNG =========\u001B[0m");

        Customer customer = new Customer();

        customer.inputData(scanner);

        List<Customer> customerList = customerService.findAllCustomers();
        if (CustomerValidator.validateCustomer(customer, customerList)) {
            customerService.createCustomer(customer);
            System.out.println("\u001B[32mThêm khách hàng thành công!\u001B[0m");

            System.out.println("\u001B[34m============================================== KHÁCH HÀNG ĐÃ THÊM ================================================\u001B[0m");
            System.out.printf("\u001B[36m| %-5s | %-20s | %-15s | %-25s | %-20s | %-10s |\u001B[0m\n",
                    "ID", "Tên", "SĐT", "Email", "Địa chỉ", "Giới tính");
            System.out.println("\u001B[36m------------------------------------------------------------------------------------------------------------------\u001B[0m");

            System.out.printf("\u001B[32m| %-5d | %-20s | %-15s | %-25s | %-20s | %-10s |\u001B[0m\n",
                    customer.getCusId(),
                    customer.getCusName(),
                    customer.getCusPhone(),
                    customer.getCusEmail(),
                    customer.getCusAddress(),
                    customer.getCusGender());

        } else {
            System.out.println("\u001B[31mThông tin khách hàng không hợp lệ. Vui lòng kiểm tra lại.\u001B[0m");
        }
    }

    private void updateCustomer() {
        System.out.println("\u001B[34m============================= CẬP NHẬT THÔNG TIN KHÁCH HÀNG =============================\u001B[0m");
        System.out.print("Nhập ID khách hàng cần cập nhật: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Customer customer = customerService.findCustomerById(id);

            if (customer == null) {
                System.out.println("\u001B[31mKhông tìm thấy khách hàng với ID: " + id + "\u001B[0m");
                return;
            }

            System.out.println("\u001B[36m============================= THÔNG TIN KHÁCH HÀNG HIỆN TẠI =============================\u001B[0m");
            System.out.printf("\u001B[33m%-20s\u001B[0m: %d\n", "ID", customer.getCusId());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Tên khách hàng", customer.getCusName());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Số điện thoại", customer.getCusPhone());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Email", customer.getCusEmail());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Địa chỉ", customer.getCusAddress());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Giới tính", customer.getCusGender());
            System.out.println("\u001B[36m===========================================================================================\u001B[0m");

            System.out.println("\nNhập thông tin mới (nhấn Enter để giữ nguyên):");

            // Update customer name
            System.out.print("Tên khách hàng: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                if (CustomerValidator.validateCustomerName(name)) {
                    customer.setCusName(name);
                } else {
                    System.out.println("\u001B[31mTên không hợp lệ. Giữ nguyên tên cũ.\u001B[0m");
                }
            }

            // Update phone
            System.out.print("Số điện thoại: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                if (CustomerValidator.validateCustomerPhone(phone)) {
                    List<Customer> customerList = customerService.findAllCustomers();
                    if (!CustomerValidator.isPhoneDuplicate(phone, customerList.stream()
                            .filter(c -> c.getCusId() != customer.getCusId())
                            .toList())) {
                        customer.setCusPhone(phone);
                    } else {
                        System.out.println("\u001B[31mSố điện thoại đã tồn tại. Giữ nguyên số điện thoại cũ.\u001B[0m");
                    }
                } else {
                    System.out.println("\u001B[31mSố điện thoại không hợp lệ. Giữ nguyên số điện thoại cũ.\u001B[0m");
                }
            }

            // Update email
            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (CustomerValidator.validateCustomerEmail(email)) {
                    List<Customer> customerList = customerService.findAllCustomers();
                    if (!CustomerValidator.isEmailDuplicate(email, customerList.stream()
                            .filter(c -> c.getCusId() != customer.getCusId())
                            .toList())) {
                        customer.setCusEmail(email);
                    } else {
                        System.out.println("\u001B[31mEmail đã tồn tại. Giữ nguyên email cũ.\u001B[0m");
                    }
                } else {
                    System.out.println("\u001B[31mEmail không hợp lệ. Giữ nguyên email cũ.\u001B[0m");
                }
            }

            // Update address
            System.out.print("Địa chỉ: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                if (CustomerValidator.validateCustomerAddress(address)) {
                    customer.setCusAddress(address);
                } else {
                    System.out.println("\u001B[31mĐịa chỉ không hợp lệ. Giữ nguyên địa chỉ cũ.\u001B[0m");
                }
            }

            // Update gender
            System.out.println("Giới tính hiện tại: " + customer.getCusGender());
            System.out.print("\u001B[35mBạn có muốn thay đổi giới tính? (Y/N): \u001B[0m");
            String changeGender = scanner.nextLine();
            if (changeGender.equalsIgnoreCase("Y")) {
                System.out.print("Nhập giới tính mới (MALE/FEMALE/OTHER): ");
                Customer.Gender newGender = Validator.getValidEnum(scanner, Customer.Gender.class);
                customer.setCusGender(newGender);
            }


            // Validate and update customer
            List<Customer> customerList = customerService.findAllCustomers();
            if (CustomerValidator.validateCustomer(customer, customerList.stream()
                    .filter(c -> c.getCusId() != customer.getCusId())
                    .toList())) {
                customerService.modifyCustomer(customer);
                System.out.println("\u001B[32mCập nhật khách hàng thành công!\u001B[0m");
            } else {
                System.out.println("\u001B[31mThông tin khách hàng không hợp lệ. Không thể cập nhật.\u001B[0m");
            }

        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mID khách hàng phải là số.\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mLỗi khi cập nhật khách hàng: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void deleteCustomer() {
        System.out.println("\u001B[34m============================= XÓA KHÁCH HÀNG =============================\u001B[0m");
        System.out.print("Nhập ID khách hàng cần xóa: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Customer customer = customerService.findCustomerById(id);

            if (customer == null) {
                System.out.println("\u001B[31mKhông tìm thấy khách hàng với ID: " + id + "\u001B[0m");
                return;
            }

            System.out.println("Thông tin khách hàng sẽ bị xóa:");
            System.out.println("\u001B[36m========================== THÔNG TIN KHÁCH HÀNG ===========================\u001B[0m");
            System.out.printf("\u001B[33m%-20s\u001B[0m: %d\n", "ID", customer.getCusId());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Tên khách hàng", customer.getCusName());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Số điện thoại", customer.getCusPhone());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Email", customer.getCusEmail());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Địa chỉ", customer.getCusAddress());
            System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Giới tính", customer.getCusGender());
            System.out.println("\u001B[36m===========================================================================\u001B[0m");


            System.out.print("\u001B[35mBạn có chắc chắn muốn xóa khách hàng này? (Y/N): \u001B[0m");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                customerService.deleteCustomer(id);
                System.out.println("\u001B[32mXóa khách hàng thành công!\u001B[0m");
            } else {
                System.out.println("Đã hủy thao tác xóa khách hàng.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mID khách hàng phải là số.\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mLỗi khi xóa khách hàng: " + e.getMessage() + "\u001B[0m");
        }
    }

    private void findCustomerById() {
        System.out.println("\u001B[34m======================= TÌM KIẾM KHÁCH HÀNG THEO ID =======================\u001B[0m");
        System.out.print("Nhập ID khách hàng cần tìm: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Customer customer = customerService.findCustomerById(id);

            if (customer == null) {
                System.out.println("\u001B[31mKhông tìm thấy khách hàng với ID: " + id + "\u001B[0m");
            } else {
                System.out.println("\u001B[36m========================== THÔNG TIN KHÁCH HÀNG ===========================\u001B[0m");
                System.out.printf("\u001B[33m%-20s\u001B[0m: %d\n", "ID", customer.getCusId());
                System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Tên khách hàng", customer.getCusName());
                System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Số điện thoại", customer.getCusPhone());
                System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Email", customer.getCusEmail());
                System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Địa chỉ", customer.getCusAddress());
                System.out.printf("\u001B[33m%-20s\u001B[0m: %s\n", "Giới tính", customer.getCusGender());
                System.out.println("\u001B[36m==========================================================================\u001B[0m");
            }

        } catch (NumberFormatException e) {
            System.out.println("\u001B[31mID khách hàng phải là số.\u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31mLỗi khi tìm kiếm khách hàng: " + e.getMessage() + "\u001B[0m");
        }
    }

}