public class PriceEngine {

    public static Money quote(Order order, Customer customer) {
        validateInputs(order, customer);

        String currency = order.lines().get(0).unit().currency();
        Money subtotal = calculateSubtotal(order, currency);

        Money running = subtotal;
        running = applyExpeditedSurcharge(running, order, currency);
        running = running.subtract(calculatePromoDiscount(order, customer, subtotal));
        running = running.subtract(calculateLoyaltyDiscount(customer, subtotal));
        running = applyRegionalTax(running, customer);

        return running;
    }

    private static void validateInputs(Order order, Customer customer) {
        if (order == null || order.lines() == null || order.lines().isEmpty()) {
            throw new IllegalArgumentException("order has no lines");
        }
        if (customer == null) {
            throw new IllegalArgumentException("customer is null");
        }
    }

    private static Money calculateSubtotal(Order order, String currency) {
        Money subtotal = Money.of(currency, 0.0);
        for (Order.Line line : order.lines()) {
            if (line.qty() > 0) {
                subtotal = subtotal.add(line.unit().times(line.qty()));
            }
        }
        return subtotal;
    }

    private static Money applyExpeditedSurcharge(Money running, Order order, String currency) {
        if (order.expedited()) {
            return running.add(Money.of(currency, 15.0));
        }
        return running;
    }

    private static Money calculatePromoDiscount(Order order, Customer customer, Money subtotal) {
        String code = order.promoCode();
        if (code == null) {
            return subtotal.times(0.0);
        }

        if ("WELCOME10".equals(code)) {
            return subtotal.times(0.10);
        }
        if ("SUMMER15".equals(code)) {
            return subtotal.times(0.15);
        }
        if ("VIP20".equals(code) && customer.loyaltyYears() >= 3) {
            return subtotal.times(0.20);
        }

        return subtotal.times(0.0);
    }

    private static Money calculateLoyaltyDiscount(Customer customer, Money subtotal) {
        double rate = getLoyaltyDiscountRate(customer.loyaltyYears());
        return subtotal.times(rate);
    }

    private static double getLoyaltyDiscountRate(int years) {
        if (years >= 5) {
            return 0.10;
        }
        if (years >= 3) {
            return 0.05;
        }
        if (years >= 1) {
            return 0.02;
        }
        return 0.0;
    }

    private static Money applyRegionalTax(Money running, Customer customer) {
        double taxRate = getTaxRate(customer.region());
        if (taxRate > 0.0) {
            return running.add(running.times(taxRate));
        }
        return running;
    }

    private static double getTaxRate(String region) {
        if ("EU".equals(region)) {
            return 0.20;
        }
        if ("US".equals(region)) {
            return 0.07;
        }
        if ("IN".equals(region)) {
            return 0.18;
        }
        return 0.0;
    }
}