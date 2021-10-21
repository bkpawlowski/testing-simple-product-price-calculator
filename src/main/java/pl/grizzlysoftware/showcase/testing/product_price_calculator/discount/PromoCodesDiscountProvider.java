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
 * discount provider dedicated for promo-codes,
 * it returns discount for given promotion code or EMPTY discount if product was not found
 */
public class PromoCodesDiscountProvider implements DiscountProvider {

    private final Map<String, Discount> discountsRegistry;

    public PromoCodesDiscountProvider() {
        this.discountsRegistry = Stream.of(
                new FlatDiscount("F1_5", valueOf(1.5)),
                new FlatDiscount("F10_0", valueOf(10)),
                new FlatDiscount("XQW2137PLK", valueOf(50.0)),
                new PercentDiscount("P010", valueOf(0.1)),
                new PercentDiscount("SH0PP1NGG0D", valueOf(0.99))
        ).collect(toMap(e -> e.code, e -> e));
    }

    @Override
    public Discount getDiscount(String code) {
        return discountsRegistry.getOrDefault(code, getDefaultDiscount());
    }
}
