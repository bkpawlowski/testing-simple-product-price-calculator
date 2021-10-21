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

package pl.grizzlysoftware.showcase.testing.product_price_calculator.util

import spock.lang.Specification
import spock.lang.Unroll

class TwoDigitValueHalfUpRounderTest extends Specification {

    def rounder = new TwoDigitValueHalfUpRounder()

    def "apply - it should throw NullPointerException when given argument is null"() {
        when:
            rounder.round(null)
        then:
            thrown(NullPointerException)
    }

    @Unroll
    def "apply - it should round '#givenValue' to '#expectedValue'"(BigDecimal givenValue, BigDecimal expectedValue) {
        when:
            def result = rounder.round(givenValue)
        then:
            expectedValue == result
        where:
            givenValue                     || expectedValue
            21.370000                      || 21.37
            21.370001                      || 21.37
            21.375                         || 21.38
            21.3777                        || 21.38
            BigDecimal.valueOf(4.11111111) || 4.11
            BigDecimal.valueOf(4.1990001)  || 4.20
    }
}
