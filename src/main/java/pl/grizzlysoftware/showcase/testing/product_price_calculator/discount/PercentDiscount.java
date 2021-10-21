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
 * percent discount decreases nominal price by a portion of nominal price i.e.
 * <p>
 * nominal price = 10
 * discount = 0.1
 * <p>
 * <p>
 * discounted price = nominal price * (1 - discount)
 * <p>
 * 9 = 10 * (1 - 0.1)
 */
public class PercentDiscount extends AbstractDiscount {
    public PercentDiscount(String name, BigDecimal amount) {
        super(name, amount);
    }

    @Override
    public BigDecimal apply(BigDecimal price) {
        requireNonNull(price);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("'price' to be discounted cannot be negative value");
        }
        return price.multiply(BigDecimal.ONE.subtract(amount))
                .max(BigDecimal.ZERO);
    }

    @Override
    protected void validateAmount(BigDecimal amount) {
        super.validateAmount(amount);
        if (amount.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("'amount' cannot be greater than 1 (100%)");
        } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("'amount' cannot be lesser than 0 (0%)");
        }
    }
}
