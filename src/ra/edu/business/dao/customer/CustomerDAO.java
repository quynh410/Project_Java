package ra.edu.business.dao.customer;

import ra.edu.business.model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findAll();
    void save(Customer customer);
    void update(Customer customer);
    void delete(int id);
    Customer findById(int id);
}
