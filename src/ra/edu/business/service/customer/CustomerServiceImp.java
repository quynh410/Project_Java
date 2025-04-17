package ra.edu.business.service.customer;

import ra.edu.business.dao.customer.CustomerDAO;
import ra.edu.business.dao.customer.CustomerDAOImp;
import ra.edu.business.model.Customer;

import java.util.List;

public class CustomerServiceImp implements CustomerService {
    private CustomerDAO customerDAO = new CustomerDAOImp();

    @Override
    public void displayAll() {
        List<Customer> list = customerDAO.findAll();
        for (Customer c : list) {
            System.out.println(c);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerDAO.delete(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDAO.findById(id);
    }

    @Override
    public void createCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void modifyCustomer(Customer customer) {
        customerDAO.update(customer);
    }
}

