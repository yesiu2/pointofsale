package pl.wojciechmazurek

import pl.wojciechmazurek.display.LCDDisplay
import pl.wojciechmazurek.pointofsale.PointOfSale
import pl.wojciechmazurek.pointofsale.Transaction
import pl.wojciechmazurek.printer.Printer
import pl.wojciechmazurek.product.Product
import pl.wojciechmazurek.stubs.BarCodeScannerStub
import pl.wojciechmazurek.stubs.ProductRepositoryStub
import spock.lang.Specification

abstract class SetupSpec extends Specification {

    BarCodeScannerStub scanner
    ProductRepositoryStub repository
    LCDDisplay lcdDisplay
    PointOfSale pointOfSale
    Printer printer
    Transaction transaction
    Scanner testScanner


    def setup() {
        scanner = new BarCodeScannerStub()

        repository = new ProductRepositoryStub()

        printer = Mock(Printer.class)

        Product banana = new Product("banana", new BigDecimal(2.90))
        Product game = new Product("game", new BigDecimal(124.50))
        Product book = new Product("book", new BigDecimal(49.25))

        repository.save(1L, banana)
        repository.save(2L, game)
        repository.save(3L, book)


        lcdDisplay = Mock(LCDDisplay.class)

        pointOfSale = new PointOfSale(lcdDisplay, scanner, repository, printer)

        List<Product> boughtProducts = new ArrayList<>()
        boughtProducts.add(banana)
        boughtProducts.add(game)
        boughtProducts.add(book)

        BigDecimal sum = BigDecimal.ZERO

        transaction = new Transaction(boughtProducts, sum)

        testScanner = new Scanner("exit")




    }
}
