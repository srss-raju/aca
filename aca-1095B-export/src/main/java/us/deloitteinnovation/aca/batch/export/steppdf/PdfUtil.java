package us.deloitteinnovation.aca.batch.export.steppdf;

/**
 * 
 * @author RajeshKumar B
 *
 */
public class PdfUtil {
    
    private PdfUtil(){}
    
    /**
     * @param date
     * @return String
     */
    public static String returnDate(String date){
        String[] dates = date.split("-");
        return dates[1] + dates[2] + dates[0];  // YYYY[0]-MM[1]-DD[2] -> MM[1]-DD[2]-YYYY[0]
    }

}
