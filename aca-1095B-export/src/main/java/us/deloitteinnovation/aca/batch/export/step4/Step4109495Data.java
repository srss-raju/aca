package us.deloitteinnovation.aca.batch.export.step4;

import java.io.File;

/**
 * <p>
 * Encapsulates information about the 109495B file written during Step 3, that will be required for Step 4 processing.
 * Since this object will be passed via the Job ExecutionContext, it is important to store and access it as a string,
 * so that it is persisted correctly to the data store between Steps.
 *</p>
 * <p>The data can be serialized to a String using toString() and reconstituted using fromString().</p>
 */
public class Step4109495Data {
    /** Full path and name of the file output in Step 3 writer. */
    public File filename ;

    /** Number of payee (1094B) forms written in the file. */
    public int form1094bCount ;

    /** Number of payer (1095B) forms written in the file. */
    public int form1095bCount ;

    /** The transmission type code O,R or C */
    public String transmissionTypeCd;

    public Integer transmissionId;

    public Step4109495Data() {
        // Intentionally empty
    }

    public Step4109495Data(File filename, int form1094bCount, int form1095bCount) {
        this.filename = filename;
        this.form1094bCount = form1094bCount;
        this.form1095bCount = form1095bCount;
    }

    public File getFilename() {
        return filename;
    }

    public void setFilename(File filename) {
        this.filename = filename;
    }

    public int getForm1094bCount() {
        return form1094bCount;
    }

    public void setForm1094bCount(int form1094bCount) {
        this.form1094bCount = form1094bCount;
    }

    public int getForm1095bCount() {
        return form1095bCount;
    }

    public void setForm1095bCount(int form1095bCount) {
        this.form1095bCount = form1095bCount;
    }

    public String getTransmissionTypeCd() {
        return transmissionTypeCd;
    }

    public void setTransmissionTypeCd(String transmissionTypeCd) {
        this.transmissionTypeCd = transmissionTypeCd;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    @Override
    public String toString() {
        return filename.getAbsolutePath() + "|" + form1094bCount + "|" + form1095bCount ;
    }

    /**
     *
     * @param input
     * @return  Creates a new object based on the parameters from a toString() call.
     * @see #toString()
     */
    public static Step4109495Data fromString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string is required.") ;
        }

        String [] parse = input.split("\\|") ;
        if (parse.length != 3) {
            throw new IllegalArgumentException("Input string '" + input + "' should be pipe delimited with 3 values.") ;
        }

        Step4109495Data data = new Step4109495Data() ;
        data.filename = new File(parse[0]) ;
        data.form1094bCount = Integer.parseInt(parse[1]) ;
        data.form1095bCount = Integer.parseInt(parse[2]) ;
        return data ;
    }

}
