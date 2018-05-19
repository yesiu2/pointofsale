package pl.wojciechmazurek

import pl.wojciechmazurek.display.LCDDisplay
import pl.wojciechmazurek.pointofsale.PointOfSale
import pl.wojciechmazurek.product.Product
import pl.wojciechmazurek.product.ProductRepository
import pl.wojciechmazurek.scanner.BarCodeScanner
import pl.wojciechmazurek.stubs.BarCodeScannerStub
import pl.wojciechmazurek.stubs.LCDDisplayStub
import pl.wojciechmazurek.stubs.ProductRepositoryStub
import spock.lang.Specification

abstract class SetupSpec extends Specification {

    BarCodeScannerStub scanner
    ProductRepositoryStub repository
    LCDDisplay lcdDisplay
    PointOfSale pointOfSale


    def setup() {
        scanner = new BarCodeScannerStub()

        repository = new ProductRepositoryStub()

        Product banana = new Product("banana", new BigDecimal(2.9))
        Product game = new Product("game", new BigDecimal(124.50))
        Product book = new Product("book", new BigDecimal(49.25))

        repository.save(1L, banana)
        repository.save(2L, game)
        repository.save(3L, book)


        lcdDisplay = Mock(LCDDisplay.class)

        pointOfSale = new PointOfSale(lcdDisplay, scanner, repository)




    }
}
