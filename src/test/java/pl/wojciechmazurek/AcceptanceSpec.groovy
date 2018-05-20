package pl.wojciechmazurek

import pl.wojciechmazurek.product.Product

import static org.assertj.core.api.Assertions.assertThat


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
            with(it) {
                contains("game")
                contains("124.50")
            }
            true
        })


        when: 'product is not found in products database'

        Product notFoundProduct = pointOfSale.findProduct(18L)

        assertThat(notFoundProduct).isNull()

        then: 'error message "Product not found" is printed on LCD display'

        1 * lcdDisplay.display("Product not found")

        when: 'scanned code is empty'

        pointOfSale.findProduct(null)

        then: 'error message "Invalid bar-code is printed on LCD display'

        1 * lcdDisplay.display("Invalid bar-code")




    }

    def "should print receipt, display sum when exit is input"() {

        when: 'exit is input'

        Scanner exitInput = new Scanner("exit")
        Scanner nonExitInput = new Scanner("other")

        // Just another test to make sure, that breaking loop in doTransaction() will work
        boolean exitBool = pointOfSale.checkExitCondition(exitInput)
        boolean nonExitBool = pointOfSale.checkExitCondition(nonExitInput)

        pointOfSale.doTransaction(transaction, testScanner)

        then: 'printer prints receipt with all scanned products, prices and sum. Sum is displayed on LCD'


        assertThat(exitBool).isTrue()
        assertThat(nonExitBool).isFalse()

        1 * printer.printReceipt({
            with(it) {
                contains("game")
                contains("banana")
                contains("book")
                contains("124.50")
                contains("2.90")
                contains("49.25")
                contains("176.65") // sum of all products
            }
            true
        })

        1 * lcdDisplay.display("176.65")
    }
}
