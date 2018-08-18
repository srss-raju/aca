package us.deloitteinnovation.aca.batch.constants;

/**
 * Created by rgopalani on 10/20/2015.
 */
public enum Months {
    JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);
    int monthOrdinal = 0;

    /**
     * @param ord
     */
    Months(int ord) {
        this.monthOrdinal = ord;
    }

    /**
     * @param ord
     * @return
     */
    public static Months byOrdinal(int ord) {
        for (Months m : Months.values()) {
            if (m.monthOrdinal == ord) {
                return m;
            }
        }
        return null;
    }

}

