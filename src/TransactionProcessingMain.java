import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransactionProcessingMain {
    final static String filePath = ".\\resources\\";
    final static String sodFileName = "Input_StartOfDay_Positions.txt";
    final static String transactionFileName = "Input_Transactions.txt";
    final static String eodFileName = "Expected_EndOfDay_Positions.txt";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<TransactionDTO> transactionDTOS = null;
        try{
            transactionDTOS = objectMapper.readValue(new File(filePath + transactionFileName),
                    new TypeReference<List<TransactionDTO>>(){});
        }catch (IOException e){
            System.out.println("Exception occurred while processing transaction file" + e);
            System.exit(1);
        }

        TransactionProcessor transactionProcessor = new TransactionProcessor();
        Map<String, Integer> instrumentDeltaMap = transactionProcessor.aggregateTransactionByInstrument(transactionDTOS);

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath + sodFileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath + eodFileName)));
            String header = reader.readLine();
            writer.write(header + ",Delta");
            writer.newLine();
            String sodPostion = reader.readLine();
            while(sodPostion != null){
                writer.write(transactionProcessor.getEODPosition(sodPostion, instrumentDeltaMap));
                writer.newLine();
                sodPostion = reader.readLine();
            }
            reader.close();
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occurred while reading SOD file. : " + e);
        }catch (IOException e){
            System.out.println("Exception occurred while processing sod file" + e);
        }
        System.out.println("Success");
    }
}
