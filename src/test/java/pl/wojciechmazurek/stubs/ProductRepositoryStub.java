package pl.wojciechmazurek.stubs;

import pl.wojciechmazurek.product.Product;
import pl.wojciechmazurek.product.ProductRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryStub implements ProductRepository {

    private Map<Long, Product> map;

    public ProductRepositoryStub() {
        this.map = new HashMap<>();
    }

    @Override
    public Product findByBarCode(Long barCode) {
        return map.get(barCode);
    }

    public void save(Long barCode, Product product) {
        map.put(barCode, product);
    }
}
