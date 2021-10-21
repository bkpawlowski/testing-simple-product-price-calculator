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
import pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.DiscountProvider;
import pl.grizzlysoftware.showcase.testing.product_price_calculator.util.ValueRounder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static pl.grizzlysoftware.showcase.testing.product_price_calculator.discount.DiscountProvider.ZERO_DISCOUNT;

public class ProductPriceCalculator {
    private final DiscountProvider productDiscountProvider;
    private final DiscountProvider promoCodeDiscountProvider;
    private final ValueRounder valueRounder;

    public ProductPriceCalculator(ValueRounder valueRounder, DiscountProvider productDiscountProvider, DiscountProvider promoCodeDiscountProvider) {
        this.productDiscountProvider = requireNonNull(productDiscountProvider, "'productDiscountProvider' cannot be null");
        this.promoCodeDiscountProvider = requireNonNull(promoCodeDiscountProvider, "'promoCodeDiscountProvider' cannot be null");
        this.valueRounder = requireNonNull(valueRounder, "'valueRounder' cannot be null");
    }

    ProductPrice calculate(Product product, Collection<String> promoCodes) {
        requireNonNull(product, "given 'product' cannot be null");
        requireNonNull(promoCodes, "given 'promoCodes' cannot be null");
        final var promoCodesDiscountsStream = promoCodes
                .stream()
                .map(promoCodeDiscountProvider::getDiscount);
        final var productDiscountStream = Stream.of(productDiscountProvider.getDiscount(product.name()));

        final var discountCollector = new DiscountCollector();
        final var holder = new ValueHolder(product.nominalPrice());
        Stream.concat(promoCodesDiscountsStream, productDiscountStream)
                .filter(discount -> !ZERO_DISCOUNT.equals(discount))
                .peek(discountCollector)
                .forEach(discount -> holder.value = discount.apply(holder.value));

        final var finalPrice = valueRounder.round(holder.value);

        return new ProductPrice(
                product,
                finalPrice,
                discountCollector.discounts
        );
    }

    private static final class DiscountCollector implements Consumer<Discount> {
        private final Collection<Discount> discounts;

        public DiscountCollector() {
            discounts = new LinkedList<>();
        }

        @Override
        public void accept(Discount discount) {
            discounts.add(discount);
        }
    }

    private static final class ValueHolder {
        public BigDecimal value;

        public ValueHolder(BigDecimal value) {
            this.value = value;
        }
    }
}
