package us.deloitteinnovation.aca.entity;

import javax.persistence.*;

/**
 * Created by tthakore on 4/21/2016.
 */
@Entity
@Table(name = "IRS_ERROR_CODE_OWNER_MAP", schema = "dbo", catalog = "ACA1095b")
public class IrsErrorCodeOwnerMapEntity {
    private String errorCode;
    private String errorDescription;
    private String errorOwner;
    private String businessRule;
    private String errorReportCategory;

    public void setErrorOwner(String errorOwner) {
        this.errorOwner = errorOwner;
    }

    @Id
    @Column(name = "ERROR_CODE")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "ERROR_DESCRIPTION")
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Basic
    @Column(name = "ERROR_OWNER")
    public String getErrorOwner() {
        return errorOwner;
    }

    @Basic
    @Column(name = "ERROR_REPORT_CATEGORY")
    public String getErrorReportCategory() {
        return errorReportCategory;
    }

    public void setErrorReportCategory(String errorReportCategory) {
        this.errorReportCategory = errorReportCategory;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IrsErrorCodeOwnerMapEntity that = (IrsErrorCodeOwnerMapEntity) o;

        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorDescription != null ? !errorDescription.equals(that.errorDescription) : that.errorDescription != null)
            return false;
        if (errorOwner != null ? !errorOwner.equals(that.errorOwner) : that.errorOwner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = errorCode != null ? errorCode.hashCode() : 0;
        result = 31 * result + (errorDescription != null ? errorDescription.hashCode() : 0);
        result = 31 * result + (errorOwner != null ? errorOwner.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "BUSINESS_RULE")
    public String getBusinessRule() {
        return businessRule;
    }

    public void setBusinessRule(String businessRule) {
        this.businessRule = businessRule;
    }
}
