public enum TransactionType {
    BUY("B"),
    SELL("S");

    public String code;

    TransactionType(String code) {
        this.code = code;
    }
}
