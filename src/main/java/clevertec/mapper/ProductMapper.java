package clevertec.mapper;

import clevertec.dto.ProductDto;
import clevertec.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    Product toProduct(ProductDto productDto);

    Product merge(@MappingTarget Product product, ProductDto productDto);
}
