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

package pl.grizzlysoftware.showcase.testing.product_price_calculator.discount;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * abstract discount implementing discount, providing attributes definition for corresponding interface methods
 */
public abstract class AbstractDiscount implements Discount {
    protected final BigDecimal amount;
    protected final String code;

    protected AbstractDiscount(String code, BigDecimal amount) {
        this.amount = requireNonNull(amount, "'amount' cannot be null");
        validateAmount(amount);
        this.code = requireNonNull(code, "'code' cannot be null");
    }

    @Override
    public String getCode() {
        return code;
    }

    /**
     * @param amount - amount to be validated
     */
    protected void validateAmount(BigDecimal amount) {
        //nothing
    }
}
