package ra.edu.business.dao.product;
import ra.edu.business.model.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> findAllProducts();
    boolean addProducts(Product product);
    boolean updateProducts(Product product);
    boolean delete(int id);
    Product findById(int id);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    List<Product> findByStockRange(int minStock, int maxStock);
    List<Product> findByBrand(String brand);
}