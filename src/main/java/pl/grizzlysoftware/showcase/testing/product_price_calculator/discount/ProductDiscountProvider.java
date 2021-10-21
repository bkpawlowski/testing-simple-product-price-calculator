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

import java.util.Map;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toMap;

/**
 * discount provider dedicated for products,
 * it returns discount for given product name or EMPTY discount if product was not found
 */
public class ProductDiscountProvider implements DiscountProvider {

    private final Map<String, Discount> discountsRegistry;

    public ProductDiscountProvider() {
        this.discountsRegistry = Stream.of(
                new PercentDiscount("BEER ALC. 12% 0.5L", valueOf(0.05)),
                new PercentDiscount("NACHOS SPICY 500G", valueOf(0.11)),
                new PercentDiscount("ORANGE JUICE 0.75L", valueOf(0.7)),
                new PercentDiscount("CONDOMS 3PCs", valueOf(0.5))
        ).collect(toMap(e -> e.code, e -> e));
    }

    @Override
    public Discount getDiscount(String code) {
        return discountsRegistry.getOrDefault(code, getDefaultDiscount());
    }
}
