package ra.edu.business.service.product;
import ra.edu.business.model.Product;

import java.util.List;

public interface ProductService {
    void displayAll();
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int id);
    List<Product> findAllProducts();
    Product findProductById(int id);
    List<Product> findProductsByPriceRange(double minPrice, double maxPrice);
    List<Product> findProductsByStockRange(int minStock, int maxStock);
    List<Product> findProductsByBrand(String brand);
}