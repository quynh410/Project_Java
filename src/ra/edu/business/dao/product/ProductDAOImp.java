package ra.edu.business.dao.product;

import ra.edu.business.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements ProductDAO {
    private static final List<Product> products = new ArrayList<>();
    private static int autoId = 1;


    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public void save(Product product) {
        product.setProId(autoId++);
        products.add(product);
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProId() == product.getProId()) {
                products.set(i, product);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        products.removeIf(product -> product.getProId() == id);
    }

    @Override
    public Product findById(int id) {
        for (Product product : products) {
            if (product.getProId() == id) {
                return product;
            }
        }
        return null;
    }
}