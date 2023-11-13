package clevertec.service;

import clevertec.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto get(UUID uuid);
    List<ProductDto> getAll();
    void update(UUID uuid, ProductDto productDto);
    UUID create(ProductDto productDto);
    void delete(UUID uuid);
}
