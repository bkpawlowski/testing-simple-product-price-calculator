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

package pl.grizzlysoftware.showcase.testing.product_price_calculator.discount

import spock.lang.Unroll


class FlatDiscountTest extends AbstractDiscountTest {

    def discount = new FlatDiscount("DSCNT_001", BigDecimal.ONE)

    @Override
    AbstractDiscount getInstanceForConstructorThrowingTest(String code, BigDecimal amount) {
        return new FlatDiscount(code, amount)
    }

    def "apply - it should throw NullPointerException when given 'price' is null"() {
        when:
            discount.apply(null)
        then:
            thrown(NullPointerException)
    }

    def "apply - it should throw IllegalArgumentException when given 'price' is lesser than zero"() {
        when:
            discount.apply(BigDecimal.valueOf(-1))
        then:
            thrown(IllegalArgumentException)
    }

    @Unroll
    def "apply - it should apply flat discount '#discountAmount' on price '#price' expecting result '#expectedDiscountedPrice'"(BigDecimal discountAmount, BigDecimal price, BigDecimal expectedDiscountedPrice) {
        given:
            def discount = new FlatDiscount("DSCNT_001", discountAmount)
        when:
            def result = discount.apply(price)
        then:
            expectedDiscountedPrice == result
        where:
            discountAmount | price || expectedDiscountedPrice
            1              | 10.00 || 9
            1              | 1     || 0
            1              | 0     || 0
            1              | 0.01  || 0
    }

}
