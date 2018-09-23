import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionProcessor {

    /**
     * This method is responsible for aggregating transactions by instrument from BUY perspective
     * @param transactionDTOList
     * @return
     */
    public Map<String, Integer> aggregateTransactionByInstrument(List<TransactionDTO> transactionDTOList){
        Map<String, Integer> instrumrntDeltaMap = new HashMap<>();
        for(TransactionDTO transactionDTO : transactionDTOList){
            int delta = 0;
            if(TransactionType.BUY.code.equals(transactionDTO.getTransactionType())){
                delta = transactionDTO.getTransactionQuantity();
            }else if(TransactionType.SELL.code.equals(transactionDTO.getTransactionType())){
                //Negative because delta in map is getting stored from buy perspective
                delta = - transactionDTO.getTransactionQuantity();
            }

            if(instrumrntDeltaMap.get(transactionDTO.getInstrument())==null){
                instrumrntDeltaMap.put(transactionDTO.getInstrument(), delta);
            }else{
                instrumrntDeltaMap.put(transactionDTO.getInstrument(), instrumrntDeltaMap.get(transactionDTO.getInstrument()) + delta);
            }
        }
        return instrumrntDeltaMap;
    }

    /**
     * This method is responsible for processing sod position to generate eod position
     * @param sodPostion
     * @param instrumentDeltaMap
     * @return
     */
    public String getEODPosition(String sodPostion, Map<String, Integer> instrumentDeltaMap){
        String sodToken[] = sodPostion.split(",");
        int delta = instrumentDeltaMap.get(sodToken[0])==null? 0 : instrumentDeltaMap.get(sodToken[0]);
        //Negative because delta in map is being stored from buy perspective
        if(sodToken[2].equals("I")){
            delta = - delta;
        }
        int eodQuantity = Integer.parseInt(sodToken[3]) + delta;

        return sodToken[0] + "," + sodToken[1] + "," + sodToken[2] + "," + eodQuantity + "," + delta;
    }
}
