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

import pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.DiscountProvider
import pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.ProductDiscountProvider
import pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.PromoCodesDiscountProvider
import pl.grizzlysoftware.showcase.testing.product_price_calculator.util.TwoDigitValueHalfUpRounder
import pl.grizzlysoftware.showcase.testing.product_price_calculator.util.ValueRounder
import spock.lang.Specification
import spock.lang.Unroll

class ProductPriceCalculatorTest extends Specification {

    ProductPriceCalculator calculator
    ValueRounder valueRounder
    DiscountProvider productDiscountProvider
    DiscountProvider promoCodeDiscountProvider

    def setup() {
        valueRounder = Spy(TwoDigitValueHalfUpRounder)
        productDiscountProvider = Spy(ProductDiscountProvider)
        promoCodeDiscountProvider = Spy(PromoCodesDiscountProvider)
        calculator = new ProductPriceCalculator(valueRounder, productDiscountProvider, promoCodeDiscountProvider)
    }

    @Unroll
    def "constructor - it should throw NullPointerException when any of the given args is null"(ValueRounder valueRounder, DiscountProvider productDiscountProvider, DiscountProvider promoCodeDiscountProvider) {
        when:
            new ProductPriceCalculator(valueRounder, productDiscountProvider, promoCodeDiscountProvider)
        then:
            thrown(NullPointerException)
        where:
            valueRounder       | productDiscountProvider | promoCodeDiscountProvider
            null               | Mock(DiscountProvider)  | Mock(DiscountProvider)
            Mock(ValueRounder) | null                    | Mock(DiscountProvider)
            Mock(ValueRounder) | Mock(DiscountProvider)  | null
    }

    @Unroll
    def "calculate - it should throw NullPointerException when any of the given args is null"(Product product, Collection<String> promoCodes) {
        when:
            calculator.calculate(product, promoCodes)
        then:
            thrown(NullPointerException)
        where:
            product             | promoCodes
            null                | []
            GroovyMock(Product) | null
    }

    @Unroll
    def "calculate - it should calculate finalPrice='#expectedPrice' for '#productName' with nominalPrice='#price' when #description"(
            productName, price, promoCodes, expectedPrice, expectedAppliedDiscounts, expectedPCPInv, description) {
        given: "Product that has (no)discounts"
            def product = new Product(productName, price)
        when: "no promo codes were provided"
            def result = calculator.calculate(product, promoCodes)
        then: "'finalPrice' should be the same like product 'nominalPrice'"
            expectedPrice == result.finalPrice()
        then: "applied discounts should be included in result"
            expectedAppliedDiscounts == result.appliedDiscounts().size()
        then: "product should be contained in result"
            product == result.product()
        then: "'promoCodeDiscountProvider' should not be invoked"
            expectedPCPInv * promoCodeDiscountProvider.getDiscount(_)
        and: "'productDiscountProvider' should be invoked 1 time"
            1 * productDiscountProvider.getDiscount(_)
        and: "'valueRounder' should be invoked 1 time"
            1 * valueRounder.round(_)
        where:
            productName     | price | promoCodes               || expectedPrice || expectedAppliedDiscounts || expectedPCPInv || description
            "CONDOMS 32PCs" | 29.99 | []                       || 29.99         || 0                        || 0              || "no discounts applied"
            "CONDOMS 32PCs" | 29.99 | ["F4KE"]                 || 29.99         || 0                        || 1              || "non existing promo code is provided"
            "CONDOMS 3PCs"  | 4.98  | []                       || 2.49          || 1                        || 0              || "product discount 50% is applied"
            "CONDOMS 3PCs"  | 4.98  | ["F1_5"]                 || 1.74          || 2                        || 1              || "promo code for 1.5 flat discount and product discount 50% are applied"
            "CONDOMS 1PCs"  | 4.98  | ["F1_5", "F4KE"]         || 3.48          || 1                        || 2              || "promo code for 1.5 flat discount and not existing discount are applied"
            "CONDOMS 4PCs"  | 4.98  | ["F1_5", "P010"]         || 3.13          || 2                        || 2              || "promo code for 1.5 flat discount and promo code for 10% discount are applied"
            "CONDOMS 4PCs"  | 4.98  | ["P010", "F1_5"]         || 2.98          || 2                        || 2              || "promo code for 10% discount and promo code for 1.5 flat discount are applied"
            "CONDOMS 3PCs"  | 4.98  | ["F4KE", "P010", "F1_5"] || 1.49          || 3                        || 3              || "non existing promo code and promo code for 10% discount and promo code for 1.5 flat discount and product discount 50% are applied"
    }

}
