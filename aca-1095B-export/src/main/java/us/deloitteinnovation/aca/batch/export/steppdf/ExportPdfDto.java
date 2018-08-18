package us.deloitteinnovation.aca.batch.export.steppdf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.deloitteinnovation.aca.model.Filer;

/**
 * 
 * @author rbongurala
 *
 */
public class ExportPdfDto {

    /**
     * Filer detailed in the FORM 1095B.  Data pulled from FILER_DEMOGRAPHICS.
     */
    Filer filer;
    List<Filer> coveredPersonList = null;
    Map<String,List<Filer>> coveredPersonMap = new HashMap<>();
    List<Filer> coveredResponsiblePersonList = null;
    

    public ExportPdfDto() {
    }

    /**
     * @param filer
     */
    public ExportPdfDto(Filer filer) {
        assert (filer != null);
        this.filer = filer;
    }

    public Filer getFiler() {
        return filer;
    }

    public void setFiler(Filer filer) {
        this.filer = filer;
    }

    public List<Filer> getCoveredPersonList() {
        return coveredPersonList;
    }

    public void setCoveredPersonList(List<Filer> coveredPersonList) {
        this.coveredPersonList = coveredPersonList;
    }

    public Map<String, List<Filer>> getCoveredPersonMap() {
        return coveredPersonMap;
    }

    public void setCoveredPersonMap(Map<String, List<Filer>> coveredPersonMap) {
        this.coveredPersonMap = coveredPersonMap;
    }

    public List<Filer> getCoveredResponsiblePersonList() {
        return coveredResponsiblePersonList;
    }

    public void setCoveredResponsiblePersonList(
            List<Filer> coveredResponsiblePersonList) {
        this.coveredResponsiblePersonList = coveredResponsiblePersonList;
    }

    

}
