package pl.wojciechmazurek.pointofsale;

import pl.wojciechmazurek.display.LCDDisplay;
import pl.wojciechmazurek.printer.Printer;
import pl.wojciechmazurek.product.Product;
import pl.wojciechmazurek.product.ProductRepository;
import pl.wojciechmazurek.scanner.BarCodeScanner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class PointOfSale {

    private LCDDisplay lcdDisplay;
    private BarCodeScanner barCodeScanner;
    private ProductRepository productRepository;
    private Printer printer;




    public PointOfSale(LCDDisplay lcdDisplay, BarCodeScanner barCodeScanner, ProductRepository productRepository, Printer printer) {
        this.lcdDisplay = lcdDisplay;
        this.barCodeScanner = barCodeScanner;
        this.productRepository = productRepository;
        this.printer = printer;
    }


    public void doTransaction(Transaction transaction, Scanner scanner) {


        transactionLoop(transaction.getBoughtProducts(), scanner);

        BigDecimal sum = sumAllProducts(transaction.getBoughtProducts());
        displaySumOnLCDDisplay(sum);
        printReceipt(transaction.getBoughtProducts(), sum);
    }

    private void transactionLoop(List<Product> boughtProducts, Scanner input) {


        do {
            if (checkExitCondition(input)) break;

            Long barCode = scanBarCode();
            if (barCode == null) {
                continue;
            }
            Product product = findProduct(barCode);

            if (product == null) {
                continue;
            }

            boughtProducts.add(product);


        } while (true);
    }

    private void printReceipt(List<Product> boughtProducts, BigDecimal sum) {
        StringBuilder sb = makeReceipt(boughtProducts, sum);

        printer.printReceipt(sb.toString());
    }

    private StringBuilder makeReceipt(List<Product> boughtProducts, BigDecimal sum) {
        StringBuilder sb = new StringBuilder();

        String result = bigDecimalToFormattedString(sum);
        boughtProducts.stream()
                .forEach(p -> sb.append(p.getName().concat(" ").concat(bigDecimalToFormattedString(p.getPrice())).concat("\n")));

        sb.append("-------" + "\n");
        sb.append("sum: ").append(result);
        return sb;
    }

    private String bigDecimalToFormattedString(BigDecimal sum) {
        sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

        return sum.toString();
    }

    private void displaySumOnLCDDisplay(BigDecimal sum) {

        String result = bigDecimalToFormattedString(sum);
        lcdDisplay.display(result);

    }

    private BigDecimal sumAllProducts(List<Product> boughtProducts) {

        BigDecimal sum = BigDecimal.ZERO;

        for (Product p : boughtProducts) {
            sum = sum.add(p.getPrice());
        }
        return sum;

    }

    public boolean checkExitCondition(Scanner scanner) {

        String exitMessage = scanner.nextLine();

        return exitMessage.equals("exit");
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

        String price = bigDecimalToFormattedString(product.getPrice());
        String message = product.getName() + " " + price;

        lcdDisplay.display(message);
    }


}
