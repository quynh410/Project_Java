package ra.edu.business.service.customer;

import ra.edu.business.model.Customer;
import java.util.List;

public interface CustomerService {
    void displayAll();
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);

    List<Customer> findAllCustomers();
    Customer findCustomerById(int id);
    void createCustomer(Customer customer);
    void modifyCustomer(Customer customer);
}
