package us.deloitteinnovation.aca.util;

import org.apache.commons.lang3.StringUtils;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;

/**
 * Utility methods to convert simple data objects and structures to other types.
 */
public abstract class Convert {

    /**
     * @param zipCode
     * @return  Will return a String array of length 2.  The first value will be the first 5 digits of the zip, the second the last 4 (if applicable). Both values may be null!
     */
    public static String[] toZipCode(String zipCode) {
        String [] zipFull = new String[2] ;
        String zip = zipCode ;
        if (zip != null) {
            // Remove non-numeric characters
            zip = zip.replaceAll("[^0-9]", "");
            if (zip.length() > 5) {
                zipFull[1] = zip.substring(5) ;
            }
            zipFull[0] = StringUtils.substring(zip, 0, 5) ;
        }
        return zipFull ;
    }

    /**
     *
     * @param filer
     * @return
     */
    public static CoveredPerson getCoveredPersonFromFiler(Filer filer){
        CoveredPerson coveredPerson = new CoveredPerson();
        coveredPerson.setSsn(filer.getRecipientSSN());
        coveredPerson.setTin(filer.getRecipientTIN());
        coveredPerson.setName(filer.getRecipientName());
        coveredPerson.setFirstName(filer.getRecipientFirstName());
        coveredPerson.setMiddleName(filer.getRecipientMiddleName());
        coveredPerson.setLastName(filer.getRecipientLastName());
        coveredPerson.setSuffix(filer.getRecipientSuffix());

        coveredPerson.setDob(filer.getRecipientDOB());

        coveredPerson.setDec(filer.getDec());
        coveredPerson.setJan(filer.getJan());
        coveredPerson.setFeb(filer.getFeb());
        coveredPerson.setMar(filer.getMar());
        coveredPerson.setApr(filer.getApr());
        coveredPerson.setMay(filer.getMay());
        coveredPerson.setJun(filer.getJun());
        coveredPerson.setJul(filer.getJul());
        coveredPerson.setAug(filer.getAug());
        coveredPerson.setSep(filer.getSep());
        coveredPerson.setOct(filer.getOct());
        coveredPerson.setNov(filer.getNov());
        coveredPerson.setDec(filer.getDec());

        if(filer.getJan().equals(CommonEntityConstants.COVERED)&&
                filer.getFeb().equals(CommonEntityConstants.COVERED)&&
                filer.getMar().equals(CommonEntityConstants.COVERED)&&
                filer.getApr().equals(CommonEntityConstants.COVERED)&&
                filer.getMay().equals(CommonEntityConstants.COVERED)&&
                filer.getJun().equals(CommonEntityConstants.COVERED)&&
                filer.getJul().equals(CommonEntityConstants.COVERED)&&
                filer.getAug().equals(CommonEntityConstants.COVERED)&&
                filer.getSep().equals(CommonEntityConstants.COVERED)&&
                filer.getOct().equals(CommonEntityConstants.COVERED)&&
                filer.getNov().equals(CommonEntityConstants.COVERED)&&
                filer.getDec().equals(CommonEntityConstants.COVERED)){
            coveredPerson.setAll(CommonEntityConstants.CHECKED);
        } else {
            coveredPerson.setAll(CommonEntityConstants.UNCHECKED);
        }
        return coveredPerson;
    }

}
