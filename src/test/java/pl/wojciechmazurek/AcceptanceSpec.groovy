package pl.wojciechmazurek

import pl.wojciechmazurek.product.Product
import pl.wojciechmazurek.stubs.BarCodeScannerStub

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

        pointOfSale.findProduct(null)

        then: 'error message "Invalid bar-code is printed on LCD display'

        1 * lcdDisplay.display("Invalid bar-code")

        when: 'exit is input'

        pointOfSale.exit()

        then: 'printer prints receipt with all scanned products, prices and sum. Sum is displayed on LCD'

        1 * printer.printReceipt({
            with(it) {
                contains("game")
                contains("banana")
                contains("book")
                contains("124.5")
                contains("2.9")
                contains("49.25")
                contains("176.25") // sum of all products
            }
            true
        })

        1 * lcdDisplay.display("176.25")
    }
}
