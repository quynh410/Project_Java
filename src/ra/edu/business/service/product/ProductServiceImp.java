package ra.edu.business.service.product;

import ra.edu.business.dao.product.ProductDAO;
import ra.edu.business.dao.product.ProductDAOImp;
import ra.edu.business.model.Product;

import java.util.List;

public class ProductServiceImp implements ProductService {
    private ProductDAO productDAO = new ProductDAOImp();

    @Override
    public void displayAll() {
        List<Product> list = productDAO.findAllProducts();
        if (list.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            for (Product p : list) {
                System.out.println(p);
            }
        }
    }

    @Override
    public void addProduct(Product product) {
        productDAO.addProducts(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDAO.updateProducts(product);
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return productDAO.findAllProducts();
    }

    @Override
    public Product findProductById(int id) {
        return productDAO.findById(id);
    }

    @Override
    public List<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
        return productDAO.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public List<Product> findProductsByStockRange(int minStock, int maxStock) {
        return productDAO.findByStockRange(minStock, maxStock);
    }

    @Override
    public List<Product> findProductsByBrand(String brand) {
        return productDAO.findByBrand(brand);
    }
}