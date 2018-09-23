import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDTO {
    private int transactionId;
    private String instrument;
    private String transactionType;
    private int transactionQuantity;

    public TransactionDTO() {
    }

    public TransactionDTO(int transactionId, String instrument, String transactionType, int transactionQuantity) {
        this.transactionId = transactionId;
        this.instrument = instrument;
        this.transactionType = transactionType;
        this.transactionQuantity = transactionQuantity;
    }

    @JsonProperty("TransactionId")
    public int getTransactionId() {
        return transactionId;
    }

    @JsonProperty("TransactionId")
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("Instrument")
    public String getInstrument() {
        return instrument;
    }

    @JsonProperty("Instrument")
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    @JsonProperty("TransactionType")
    public String getTransactionType() {
        return transactionType;
    }

    @JsonProperty("TransactionType")
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @JsonProperty("TransactionQuantity")
    public int getTransactionQuantity() {
        return transactionQuantity;
    }

    @JsonProperty("TransactionQuantity")
    public void setTransactionQuantity(int transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }
}
