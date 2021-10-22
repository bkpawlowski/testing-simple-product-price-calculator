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

package pl.grizzlysoftware.showcase.testing.product_price_calculator;

import pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.Discount;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.requireNonNull;

/**
 * Simple output of ProductPriceCalculator containing informations about discounted product, price after discounts and applied discounts
 */
public record ProductPrice(Product product,
                           BigDecimal finalPrice,
                           Collection<Discount> appliedDiscounts) {
    public ProductPrice(Product product, BigDecimal finalPrice, Collection<Discount> appliedDiscounts) {
        this.product = requireNonNull(product, "'product' cannot be null");
        this.finalPrice = requireNonNull(finalPrice, "'finalPrice' cannot be null");
        this.appliedDiscounts = appliedDiscounts == null ? Collections.emptyList() : unmodifiableCollection(appliedDiscounts);
    }
}
