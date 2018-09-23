import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionProcessorTest extends TestCase {

    List<TransactionDTO> transactionDTOS;
    Map<String, Integer> expectedInstrumentDeltaMap;
    TransactionProcessor transactionProcessor;


    String eIBMSodPosition = "IBM,101,E,100000";
    String iIBMSodPosition = "IBM,102,I,-100000";
    String eIBMEodPositionExpected = "IBM,101,E,94550,-5450";
    String iIBMEodPositionExpected = "IBM,102,I,-94550,5450";

    String eAMZNSodPosition = "AMZN,101,E,5000";
    String iAMZNSodPosition = "AMZN,102,I,-5000";
    String eAMZNEodPositionExpected = "AMZN,101,E,6050,1050";
    String iAMZNEodPositionExpected = "AMZN,102,I,-6050,-1050";

    String eDELLSodPosition = "DELL,101,E,500";
    String iDELLSodPosition = "DELL,102,I,-500";
    String eDELLEodPositionExpected = "DELL,101,E,-250,-750";
    String iDELLEodPositionExpected = "DELL,102,I,250,750";

    String eEBAYSodPosition = "EBAY,101,E,60000";
    String iEBAYSodPosition = "EBAY,102,I,-60000";
    String eEBAYEodPositionExpected = "EBAY,101,E,60000,0";
    String iEBAYEodPositionExpected = "EBAY,102,I,-60000,0";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        transactionDTOS = new ArrayList<>();
        expectedInstrumentDeltaMap = new HashMap<>();

        transactionDTOS.add(new TransactionDTO(1,"IBM","B",1000));
        transactionDTOS.add(new TransactionDTO(1,"IBM","S",500));
        transactionDTOS.add(new TransactionDTO(1,"IBM","B",50));
        transactionDTOS.add(new TransactionDTO(1,"IBM","S",6000));
        //IBM Delta = 1000 -500 + 50 -6000 = -5450 = Sell: 5450
        expectedInstrumentDeltaMap.put("IBM", -5450);

        transactionDTOS.add(new TransactionDTO(1,"AMZN","B",300));
        transactionDTOS.add(new TransactionDTO(1,"AMZN","S",250));
        transactionDTOS.add(new TransactionDTO(1,"AMZN","B",1000));
        //AMZN Delta = 300 -250 + 1000 = 1050 = Buy: 1050
        expectedInstrumentDeltaMap.put("AMZN",1050);


        transactionDTOS.add(new TransactionDTO(1,"DELL","S",750));
        //DELL Delta = -750 = Sell: 750
        expectedInstrumentDeltaMap.put("DELL",-750);

        transactionProcessor = new TransactionProcessor();
    }

    @Test
    public void testAggregateTransactionByInstrument() {
        Map<String, Integer> actualInstrumentDeltaMap = transactionProcessor.aggregateTransactionByInstrument(transactionDTOS);
        assertNotNull(actualInstrumentDeltaMap);
        assertEquals(expectedInstrumentDeltaMap.size(), actualInstrumentDeltaMap.size());
        for(String instrument : expectedInstrumentDeltaMap.keySet()){
            assertEquals(expectedInstrumentDeltaMap.get(instrument), actualInstrumentDeltaMap.get(instrument));
        }
    }

    @Test
    public void testGetEODPosition() {
        String eIBMEodPositionActual = transactionProcessor.getEODPosition(eIBMSodPosition, expectedInstrumentDeltaMap);
        assertEquals(eIBMEodPositionExpected, eIBMEodPositionActual);
        String iIBMEodPositionActual = transactionProcessor.getEODPosition(iIBMSodPosition, expectedInstrumentDeltaMap);
        assertEquals(iIBMEodPositionExpected, iIBMEodPositionActual);

        String eAMZNEodPositionActual = transactionProcessor.getEODPosition(eAMZNSodPosition, expectedInstrumentDeltaMap);
        assertEquals(eAMZNEodPositionExpected, eAMZNEodPositionActual);
        String iAMZNEodPositionActual = transactionProcessor.getEODPosition(iAMZNSodPosition, expectedInstrumentDeltaMap);
        assertEquals(iAMZNEodPositionExpected, iAMZNEodPositionActual);

        String eDELLEodPositionActual = transactionProcessor.getEODPosition(eDELLSodPosition, expectedInstrumentDeltaMap);
        assertEquals(eDELLEodPositionExpected, eDELLEodPositionActual);
        String iDELLEodPositionActual = transactionProcessor.getEODPosition(iDELLSodPosition, expectedInstrumentDeltaMap);
        assertEquals(iDELLEodPositionExpected, iDELLEodPositionActual);

        String eEBAYEodPositionActual = transactionProcessor.getEODPosition(eEBAYSodPosition, expectedInstrumentDeltaMap);
        assertEquals(eEBAYEodPositionExpected, eEBAYEodPositionActual);
        String iEBAYEodPositionActual = transactionProcessor.getEODPosition(iEBAYSodPosition, expectedInstrumentDeltaMap);
        assertEquals(iEBAYEodPositionExpected, iEBAYEodPositionActual);
    }
}