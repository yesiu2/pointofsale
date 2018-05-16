package pl.wojciechmazurek

import spock.lang.Specification

class AcceptanceSpec extends Specification {

    def "single product sale"() {

        given: 'product bar code is scanned'

        when: 'product is found in database'

        then: 'its name and price is printed on LCD display'

        when: 'product is not found in database'

        then: 'error message "Product not found" is printed on LCD display'

        when: 'scanned code is empty'

        then: 'error message "Invalid bar code is printed on LCD display'
    }
}
