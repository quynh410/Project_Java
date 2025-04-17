package ra.edu.business.dao.customer;
import ra.edu.business.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImp implements CustomerDAO {
    private static final List<Customer> customers = new ArrayList<>();
    private static int autoId = 1;

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    @Override
    public void save(Customer customer) {
        customer.setCusId(autoId++);
        customers.add(customer);
    }

    @Override
    public void update(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCusId() == customer.getCusId()) {
                customers.set(i, customer);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        customers.removeIf(customer -> customer.getCusId() == id);
    }

    @Override
    public Customer findById(int id) {
        for (Customer customer : customers) {
            if (customer.getCusId() == id) {
                return customer;
            }
        }
        return null;
    }
}

