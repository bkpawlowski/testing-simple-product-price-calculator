/*
 * Copyright © 2021 Grizzly Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.grizzlysoftware.showcase.testing.product_price_calculator

import spock.lang.Specification
import spock.lang.Unroll

class ProductTest extends Specification {
    @Unroll
    def "constructor - it should throw NullPointerException when any of the given args is null"(String name, BigDecimal nominalPrice) {
        when:
            new Product(name, nominalPrice)
        then:
            thrown(NullPointerException)
        where:
            name           | nominalPrice
            null           | BigDecimal.ZERO
            "CONDOMS 3PCs" | null
            null           | null
    }

    @Unroll
    def "constructor - it should throw IllegalArgumentException when 'nominalPrice' is lesser than or equal to 'zero'"(BigDecimal nominalPrice) {
        when:
            new Product("CONDOMS 3PCs", nominalPrice)
        then:
            thrown(IllegalArgumentException)
        where:
            nominalPrice << [-10, -1, 0,]
    }

}
