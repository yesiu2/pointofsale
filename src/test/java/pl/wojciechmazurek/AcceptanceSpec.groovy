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

import static org.assertj.core.api.Assertions.*

class AcceptanceSpec extends SetupSpec {



    def "single product sale"() {

        given: 'product bar code is scanned'

        Long barCode = pointOfSale.scanBarCode()

        when: 'product is found in products database'


        Product foundProduct = pointOfSale.findProduct(barCode)

        assertThat(foundProduct).isNotNull()
        assertThat(foundProduct.getName().is("game"))
        assertThat(foundProduct.getPrice() == new BigDecimal(124.50))

        pointOfSale.displayNameAndPriceOnLCD(foundProduct)

        then: 'its name and price is printed on LCD display'

        1 * lcdDisplay.display({
            with(it){
                contains("game")
                contains("124.5")
            }
            true
        })


        when: 'product is not found in products database'

       Product notFoundProduct =  pointOfSale.findProduct(18L)

        assertThat(notFoundProduct).isNull()

        then: 'error message "Product not found" is printed on LCD display'

        1 * lcdDisplay.display("Product not found")

        when: 'scanned code is empty'

        then: 'error message "Invalid bar code is printed on LCD display'

        when: 'exit is input'

        then: 'printer prints receipt with all scanned products, prices and sum. Sum is displayed on LCD'
    }
}
