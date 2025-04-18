package ra.edu.business.dao.product;
import ra.edu.business.model.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(int id);
    Product findById(int id);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    List<Product> findByStockRange(int minStock, int maxStock);
    List<Product> findByBrand(String brand);
}