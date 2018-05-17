package pl.wojciechmazurek.pointofsale;

import pl.wojciechmazurek.display.LCDDisplay;
import pl.wojciechmazurek.product.Product;
import pl.wojciechmazurek.product.ProductRepository;
import pl.wojciechmazurek.scanner.BarCodeScanner;

public class PointOfSale {

    private LCDDisplay lcdDisplay;
    private BarCodeScanner barCodeScanner;
    private ProductRepository productRepository;


    public PointOfSale(LCDDisplay lcdDisplay, BarCodeScanner barCodeScanner, ProductRepository productRepository) {
        this.lcdDisplay = lcdDisplay;
        this.barCodeScanner = barCodeScanner;
        this.productRepository = productRepository;
    }

    public LCDDisplay getLcdDisplay() {
        return lcdDisplay;
    }

    public Long scanBarCode() {

        return barCodeScanner.scan();
    }

    public Product findProduct(Long barCode) {
        return productRepository.findByBarCode(barCode);
    }

    public void displayNameAndPriceOnLCD(Product product) {
        String message = product.getName() + " " + product.getPrice().toString();

        lcdDisplay.display(message);
    }


}
