# testing-simple-product-price-calculator
An example project showing simple testing approach for my blog post:
[https://grizzlysoftware.pl/source-code-testing-where-the-hell-and-how-should-i-start-part-1](https://grizzlysoftware.pl/source-code-testing-where-the-hell-and-how-should-i-start-part-1)

## Context

`ProductPriceCalculator` is able to calculate discounted product price for given `Product` and available `Product` 
related `Discount`'s and promo codes. It also uses `ValueRounder` to round result in desired manner.

There are two types of `Discount`'s
- `FlatDiscount` - reduces price by decreasing flat amount, i.e.:
```
discounted price = nominal price - discount
-------------------------------------------------
7 = 10 - 3
```
- `PercentDiscount` - reduces price by portion of price, i.e.:
```
discounted price = nominal price * (1 - discount)
-------------------------------------------------
9 = 10 * (1 - 0.1)
```

For the sake of simplicity:
- I FAIL-FAST - as soon as any given argument has inappropriate value I break execution throwing an exception
- I do not deal with any of `VAT` like taxes. 
- I do not care about potential `Discount`'s exclusions(Discount1 cannot be applied together with Discount2 and Discount6)
- I do not care about `Discount` appliance order
- and other stuff

## Used technologies
- java: source code
- groovy: test source code
- spock framework: testing framework 

## Pre requisites
- java16
- gradle (optionally)

## Building

Using standalone gradle
```
gradle build
```

Using gradle wrapper
```
./gradlew build
```

## Running tests

Using standalone gradle
```
gradle test
```

Using gradle wrapper
```
./gradlew test
```
