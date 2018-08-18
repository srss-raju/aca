package us.deloitteinnovation.aca.model;

public class SourceCoverMessage {
    private String sourceCd;
    private String languageCd;
    private String coverMessageEn;
    private String coverMessageSp;
    private String coverMessageVt;


    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getLanguageCd() {
        return languageCd;
    }

    public void setLanguageCd(String languageCd) {
        this.languageCd = languageCd;
    }

    public String getCoverMessageEn() {
        return coverMessageEn;
    }

    public void setCoverMessageEn(String coverMessageEn) {
         this.coverMessageEn = ((coverMessageEn != null))?coverMessageEn.replace("\r\n"," "):null;
    }

    public String getCoverMessageSp() {
        return coverMessageSp;
    }

    public void setCoverMessageSp(String coverMessageSp) {
        this.coverMessageSp  = ((coverMessageSp != null))?coverMessageSp.replace("\r\n"," "):null;
    }

    public String getCoverMessageVt() {
        return coverMessageVt;
    }

    public void setCoverMessageVt(String coverMessageVt) {
        this.coverMessageVt =((coverMessageVt != null))?coverMessageVt.replace("\r\n"," "):null;
    }
}
