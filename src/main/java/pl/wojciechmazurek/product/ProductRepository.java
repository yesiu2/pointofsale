package pl.wojciechmazurek.product;

public interface ProductRepository {

    Product findByBarCode(Long barCode);
}
