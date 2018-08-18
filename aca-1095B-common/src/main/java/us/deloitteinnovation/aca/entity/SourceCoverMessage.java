package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the source_cover_message database table.
 *
 */
@Entity
@Table(name="SOURCE_COVER_MESSAGE")
public class SourceCoverMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="COVER_MESSAGE_VT", columnDefinition = "nvarchar")
    private String coverMessageDe;

    @Column(name="COVER_MESSAGE_EN", columnDefinition = "nvarchar")
    private String coverMessageEn;

    @Column(name="COVER_MESSAGE_SP", columnDefinition = "nvarchar")
    private String coverMessageSp;

    @Column(name="LANGUAGE_CD")
    private String languageCd;

    @Id
    @Column(name="SOURCE_CD")
    private String sourceCd;

    public SourceCoverMessage() {
    }





    public String getCoverMessageDe() {
        return this.coverMessageDe;
    }

    public void setCoverMessageDe(String coverMessageDe) {
        this.coverMessageDe = coverMessageDe;
    }

    public String getCoverMessageEn() {
        return this.coverMessageEn;
    }

    public void setCoverMessageEn(String coverMessageEn) {
        this.coverMessageEn = coverMessageEn;
    }

    public String getCoverMessageSp() {
        return this.coverMessageSp;
    }

    public void setCoverMessageSp(String coverMessageSp) {
        this.coverMessageSp = coverMessageSp;
    }

    public String getLanguageCd() {
        return this.languageCd;
    }

    public void setLanguageCd(String languageCd) {
        this.languageCd = languageCd;
    }

    public String getSourceCd() {
        return this.sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

}