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

        // IMO this null validation should be in scanBarCode method, but I havent got idea how to test it
        if (barCode == null) {
            displayInvalidBarCodeMessage();
            return null;
        }

        Product product = productRepository.findByBarCode(barCode);

        if (product != null) {
            displayNameAndPriceOnLCD(product);
        } else {
            displayNotFoundMessage();
            return null;
        }
        return product;
    }

    private void displayInvalidBarCodeMessage() {
        lcdDisplay.display("Invalid bar-code");
    }

    private void displayNotFoundMessage() {
        lcdDisplay.display("Product not found");
    }

    private void displayNameAndPriceOnLCD(Product product) {
        String message = product.getName() + " " + product.getPrice().toString();

        lcdDisplay.display(message);
    }


}
