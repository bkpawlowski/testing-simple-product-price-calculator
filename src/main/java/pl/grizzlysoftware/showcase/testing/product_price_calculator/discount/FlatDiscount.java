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
 * flat discount decreases nominal price by a flat value i.e.
 * <p>
 * nominal price = 10
 * discount = 3
 * <p>
 * <p>
 * discounted price = nominal price - discount
 * <p>
 * 7 = 10 - 3
 */
public class FlatDiscount extends AbstractDiscount {
    public FlatDiscount(String name, BigDecimal amount) {
        super(name, amount);
    }

    @Override
    public BigDecimal apply(BigDecimal price) {
        requireNonNull(price);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("'price' to be discounted cannot be negative value");
        }
        return price.subtract(amount)
                .max(BigDecimal.ZERO);
    }
}
