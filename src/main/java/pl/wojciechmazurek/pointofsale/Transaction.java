package pl.wojciechmazurek.pointofsale;

import pl.wojciechmazurek.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class Transaction {

    private List<Product> boughtProducts;
    private BigDecimal sum;

    public Transaction(List<Product> boughtProducts, BigDecimal sum) {
        this.boughtProducts = boughtProducts;
        this.sum = sum;
    }

    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
