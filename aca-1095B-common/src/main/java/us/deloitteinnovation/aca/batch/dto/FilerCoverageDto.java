package us.deloitteinnovation.aca.batch.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 */
public class FilerCoverageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private FilerDemographicPKDto id;

    private String apr;

    private String aug;

    private String comments;

    private String dec;

    private String feb;

    private String jan;

    private String jul;

    private String jun;

    private String mar;

    private String may;

    private String nov;

    private String oct;


    private String origCoverageBeginDtStr;

    @NotNull(message = "{original.coverage.begin.date.length.invalid}")
    @DateTimeFormat(pattern = BatchConstants.DATE_FORMAT)
    private Date origCoverageBeginDt;

    private String origCoverageEndDtStr;

    @NotNull(message = "{original.coverage.end.date.length.invalid}")
    @DateTimeFormat(pattern = BatchConstants.DATE_FORMAT)
    private Date origCoverageEndDt;

    private String sep;

    private String updatedBy;

    private String updatedDt;

    @NotBlank(message = "{case.id.field.length.invalid}")
    @Pattern.List({
            @Pattern(regexp = "^.{1,15}$", message = "{case.id.field.length.invalid}"),
            @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{case.id.content.invalid}")
    })
    private String recipientCaseApplicationId;

    @NotNull(message = "{policy.program.name.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{0,50}$", message = "{policy.program.name.length.invalid}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{policy.program.name.spchar.num.error}")
            }
    )
    private String programName;

    private String taxYear;

    private FilerDemographicDto filerDemographic;

    public FilerCoverageDto() {
    }

    public FilerDemographicPKDto getId() {
        return this.id;
    }

    public void setId(FilerDemographicPKDto id) {
        this.id = id;
    }

    public String getApr() {
        return this.apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getAug() {
        return this.aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDec() {
        return this.dec;
    }

    public void setDec(String dece) {
        this.dec = dece;
    }

    public String getFeb() {
        return this.feb;
    }

    public void setFeb(String feb) {
        this.feb = feb;
    }

    public String getJan() {
        return this.jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getJul() {
        return this.jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getJun() {
        return this.jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getMar() {
        return this.mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getMay() {
        return this.may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getNov() {
        return this.nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getOct() {
        return this.oct;
    }

    public void setOct(String oct) {
        this.oct = oct;
    }

    public Date getOrigCoverageBeginDt() {
        return this.origCoverageBeginDt;
    }

    public void setOrigCoverageBeginDt(Date origCoverageBeginDt) {
        this.origCoverageBeginDt = origCoverageBeginDt;
    }

    public Date getOrigCoverageEndDt() {
        return this.origCoverageEndDt;
    }

    public void setOrigCoverageEndDt(Date origCoverageEndDt) {
        this.origCoverageEndDt = origCoverageEndDt;
    }

    public String getSep() {
        return this.sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDt() {
        return this.updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getOrigCoverageBeginDtStr() {
        return origCoverageBeginDtStr;
    }

    public void setOrigCoverageBeginDtStr(String origCoverageBeginDtStr) {
        this.origCoverageBeginDtStr = origCoverageBeginDtStr;
    }

    public String getOrigCoverageEndDtStr() {
        return origCoverageEndDtStr;
    }

    public void setOrigCoverageEndDtStr(String origCoverageEndDtStr) {
        this.origCoverageEndDtStr = origCoverageEndDtStr;
    }

    public FilerDemographicDto getFilerDemographic() {
        return filerDemographic;
    }

    public void setFilerDemographic(FilerDemographicDto filerDemographic) {
        this.filerDemographic = filerDemographic;
    }

    public String getRecipientCaseApplicationId() {
        return this.recipientCaseApplicationId;
    }

    public void setRecipientCaseApplicationId(String recipientCaseApplicationId) {
        this.recipientCaseApplicationId = recipientCaseApplicationId;
    }

    public String getProgramName() {
        return this.programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    @Override
    public String toString() {
        return String.format("FilerCoverageDto=[Primary-Key=[%s], Coverages=[%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s]]",
                id, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);
    }
}
