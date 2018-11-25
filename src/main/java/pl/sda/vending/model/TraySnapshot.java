package pl.sda.vending.model;

public class TraySnapshot {
    private final String symbol;
    private final Long price;
    private final String product;

    public TraySnapshot(String symbol, Long price, String product) {
        this.symbol = symbol;
        this.price = price;
        this.product = product;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getPrice() {
        return price;
    }

    public String getProduct() {
        return product;
    }
}
