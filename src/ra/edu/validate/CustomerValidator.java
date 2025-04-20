package ra.edu.validate;

import ra.edu.business.model.Customer;

import java.util.List;

public class CustomerValidator {
    // Kiểm tra tên khách hàng (không rỗng, độ dài từ 1-30 ký tự)
    public static boolean validateCustomerName(String name) {
        return name != null && name.trim().length() >= 1 && name.trim().length() <= 30;
    }

    // Kiểm tra số điện thoại (không rỗng)
    public static boolean validateCustomerPhone(String phone) {
        return phone != null && !phone.trim().isEmpty();
    }

    // Kiểm tra email (không rỗng và định dạng hợp lệ)
    public static boolean validateCustomerEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra định dạng email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // Kiểm tra địa chỉ (không rỗng và tối đa 200 ký tự)
    public static boolean validateCustomerAddress(String address) {
        return address != null && !address.trim().isEmpty() && address.length() <= 200;
    }

    // Kiểm tra số điện thoại đã tồn tại trong danh sách khách hàng chưa
    public static boolean isPhoneDuplicate(String phone, List<Customer> customerList) {
        return customerList.stream().anyMatch(c -> c.getCusPhone().equalsIgnoreCase(phone));
    }

    // Kiểm tra email đã tồn tại trong danh sách khách hàng chưa
    public static boolean isEmailDuplicate(String email, List<Customer> customerList) {
        return customerList.stream().anyMatch(c -> c.getCusEmail().equalsIgnoreCase(email));
    }

    // Kiểm tra toàn bộ thông tin khách hàng (bao gồm kiểm tra trùng số điện thoại và email)
    public static boolean validateCustomer(Customer customer, List<Customer> customerList) {
        if (customer == null) {
            System.out.println("\u001B[31mĐối tượng khách hàng không được null.\u001B[0m");
            return false;
        }
        if (!validateCustomerName(customer.getCusName())) {
            System.out.println("\u001B[31mTên khách hàng không hợp lệ.\u001B[0m");
            return false;
        }
        if (!validateCustomerPhone(customer.getCusPhone())) {
            System.out.println("\u001B[31mSố điện thoại không hợp lệ.\u001B[0m");
            return false;
        }
        if (!validateCustomerEmail(customer.getCusEmail())) {
            System.out.println("\u001B[31mEmail không hợp lệ.\u001B[0m");
            return false;
        }
        if (!validateCustomerAddress(customer.getCusAddress())) {
            System.out.println("\u001B[31mĐịa chỉ không hợp lệ.\u001B[0m");
            return false;
        }
        if (customer.getCusGender() == null) {
            System.out.println("\u001B[31mGiới tính không được null.\u001B[0m");
            return false;
        }
        if (isPhoneDuplicate(customer.getCusPhone(), customerList)) {
            System.out.println("\u001B[31mSố điện thoại đã tồn tại.\u001B[0m");
            return false;
        }
        if (isEmailDuplicate(customer.getCusEmail(), customerList)) {
            System.out.println("\u001B[31mEmail đã tồn tại.\u001B[0m");
            return false;
        }
        return true;
    }
}