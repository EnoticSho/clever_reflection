package clevertec;

import clevertec.dao.ProductDaoImpl;
import clevertec.dbConnection.DatabaseConnectionManager;
import clevertec.dto.InfoProductDto;
import clevertec.cache.LruCache;
import clevertec.dto.ProductDto;
import clevertec.mapper.ProductMapperImpl;
import clevertec.proxy.DaoProxy;
import clevertec.service.ProductService;
import clevertec.service.ProductServiceImpl;

import java.io.IOException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        ProductService service = new ProductServiceImpl(new DaoProxy(new ProductDaoImpl(new DatabaseConnectionManager())), new ProductMapperImpl());
        InfoProductDto infoProductDto = service.get(UUID.fromString("1d9411b4-53cc-42fc-8eeb-ab5d4c3820ba"));
        service.getAllProducts().forEach(System.out::println);
        System.out.println(infoProductDto);
        InfoProductDto infoProductDto1 = service.get(UUID.fromString("1d9411b4-53cc-42fc-8eeb-ab5d4c3820ba"));
        InfoProductDto infoProductDto2 = service.get(UUID.fromString("1d9411b4-53cc-42fc-8eeb-ab5d4c3820ba"));
        InfoProductDto infoProductDto3 = service.get(UUID.fromString("1d9411b4-53cc-42fc-8eeb-ab5d4c3820ba"));
        service.getAllProducts().forEach(System.out::println);
        System.out.println(infoProductDto);
    }
}
