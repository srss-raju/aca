package us.deloitteinnovation.aca.batch.export.step3;

import org.junit.Before;
import org.junit.Test;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @see Step3Form109495Processor
 */
public class Step3Form109495ProcessorTest {

    Step3Form109495Processor processor ;

    Step3Form109495Pairing pairing ;

    @Before
    public void before() {
        processor = new Step3Form109495Processor() ;

        pairing = new Step3Form109495Pairing(new Step2Form1094Dto()) ;
    }

    @Test
    public void processLessThanMax() throws Exception {
        processor.setMaxForm1095bPerDoc(10);

        // Add X 1095s
        for (int i = 0 ; i < 7 ; i++) {
            pairing.step1Form1095bDtoList.add(new Step1Form1095Dto()) ;
        }

        Step3ProcessorResult result = processor.process(pairing) ;
        assertNotNull(result) ;
        assertNotNull(result.getPairings()) ;
        assertEquals(1, result.getPairings().size());

        assertPairing(result.getPairings().get(0),7) ;
    }


    @Test
    public void processMoreThanMax() throws Exception {
        processor.setMaxForm1095bPerDoc(10);

        // Add X 1095s
        for (int i = 0 ; i < 27 ; i++) {
            pairing.step1Form1095bDtoList.add(new Step1Form1095Dto()) ;
        }

        Step3ProcessorResult result = processor.process(pairing) ;
        assertNotNull(result) ;
        assertNotNull(result.getPairings()) ;
        assertEquals(3, result.getPairings().size());

        assertPairing(result.getPairings().get(0),10) ;
        assertPairing(result.getPairings().get(1),10) ;
        assertPairing(result.getPairings().get(2),7) ;
    }


    @Test
    public void processNone() throws Exception {
        processor.setMaxForm1095bPerDoc(10);

        // Add zero 1095s

        Step3ProcessorResult result = processor.process(pairing) ;
        assertNotNull(result) ;
        assertNotNull(result.getPairings()) ;
        assertEquals(1, result.getPairings().size());

        assertPairing(result.getPairings().get(0),0) ;
    }

    public static void assertPairing(Step3Form109495Pairing pair, int form1095bCount) {
        assertNotNull(pair) ;
        assertNotNull(pair.step2Form1094bDto) ;
        assertNotNull(pair.step1Form1095bDtoList) ;
        assertEquals(form1095bCount, pair.step1Form1095bDtoList.size()) ;
    }


}