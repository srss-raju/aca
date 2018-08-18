package us.deloitteinnovation.aca.batch.export.step3;

/**
 */
public enum Form10945Type {
    FORM_10945_B("1094/1095B"),
    FORM_10945_C("1094/1095C");

    /** Actual value to be used on the XML transmittal. */
    private final String value ;

    Form10945Type(String s) {
        value = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : value.equals(otherName);
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return this.value ;
    }
}
