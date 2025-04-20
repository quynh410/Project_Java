package ra.edu.business.dao.customer;

import ra.edu.business.model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findAll();
    boolean save(Customer customer);
    boolean update(Customer customer);
    boolean delete(int id);
    Customer findById(int id);
}
