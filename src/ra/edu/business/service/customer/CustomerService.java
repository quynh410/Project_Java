package ra.edu.business.service.customer;

import ra.edu.business.model.Customer;
import java.util.List;

public interface CustomerService {
    boolean displayAll();
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int id);

    List<Customer> findAllCustomers();
    Customer findCustomerById(int id);
    boolean createCustomer(Customer customer);
    boolean modifyCustomer(Customer customer);
}
