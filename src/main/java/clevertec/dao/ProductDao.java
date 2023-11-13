package clevertec.dao;

import clevertec.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDao {
    public Product findById(UUID uuid);
    List<Product> findAllproducts();
    Product save(Product product);
    void delete(UUID uuid);
}
