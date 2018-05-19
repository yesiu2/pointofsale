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

    public Long scanBarCode() {

        return barCodeScanner.scan();
    }

    public Product findProduct(Long barCode) {

        Product product = productRepository.findByBarCode(barCode);

        if (product != null) {
            displayNameAndPriceOnLCD(product);
        } else {
            displayNotFoundMessage();
        }
        return product;
    }

    private void displayNotFoundMessage() {
        lcdDisplay.display("Product not found");
    }

    private void displayNameAndPriceOnLCD(Product product) {
        String message = product.getName() + " " + product.getPrice().toString();

        lcdDisplay.display(message);
    }


}
