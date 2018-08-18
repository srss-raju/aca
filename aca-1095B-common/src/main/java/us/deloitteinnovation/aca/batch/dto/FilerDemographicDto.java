package us.deloitteinnovation.aca.batch.dto;

import com.google.common.collect.ListMultimap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.validator.AcceptedValues;
import us.deloitteinnovation.aca.validator.StateCode;
import us.deloitteinnovation.aca.validator.USStateCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.COVERAGE_MONTHS;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.MMDDYYYY;

/**
 * Created by rgopalani on 10/20/2015.
 */
public class FilerDemographicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private FilerDemographicPKDto id;

    private boolean
            active = true,
            insert;

    private String originalCoverageBeginDtStr;

    private String comments;

    private String transmissionStatusCode;
    
    @NotEmpty(message = "{recipient.commmunication.preference.value.required.error}")
    @AcceptedValues(acceptValues = {"E","P","B"}, message = "{recipient.commmunication.preference.value.required.error}")
    private String communicationPreference;

    @NotNull(message = "{correction.code.value.invalid}")
    @Pattern(regexp = "[OUC]", message = "{correction.code.value.invalid}")
    private String correction;

    private Date correctionDt;

    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,100}$", message = "{recipient.email.format.invalid}"),
                    @Pattern(regexp = "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,63}$", message = "{recipient.email.format.invalid}")
            }
    )
    private String eMail;

    @Pattern(regexp = "^.{0,30}$", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_1_REQ_VALUE)
    private String employerAddressLine1;

    @Pattern(regexp = "^.{0,30}$", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_1_REQ_VALUE)
    private String employerAddressLine2;

    private String employerCityOrTown;

    @Pattern(regexp = "[0-9]{10}$", message = ErrorMessageConstants.EMPLOYER_CONTACT_NO_VALUE)
    private String employerContactNo;

    @Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_COUNTRY_REQ_VALUE)
    private String employerCountry;

    @Pattern(regexp = "^[0-9]{0,25}$", message = ErrorMessageConstants.EMPLOYER_IDENTIFICATION_NUMBER_REQ_VALUE)
    private String employerIdentificationNumber;

    @Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_NAME_REQ_VALUE)
    private String employerName;

    @Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_STATE_OR_PROVINCE_REQ_VALUE)
    private String employerStateOrProvince;

    @NotEmpty(message = "{recipient.filer.status.value.required.error}")
    @AcceptedValues(acceptValues = {"R","C"}, message = "{recipient.filer.status.value.required.error}")
	private String filerStatus;

    private String formStatus;

    @Pattern(regexp = "^.{0}$", message = "{recipient.language.preference.value.error}")
    private String languagePreference;

    @NotEmpty(message = "{policy.origin.length.invalid}")
    @Pattern(regexp = "^[C]$", message = "{policy.origin.length.invalid}")
    private String policyOrigin;

    @NotNull(message = "{provider.address.line1.length.invalid}")
    @Pattern.List({
            @Pattern(regexp = "^.{1,35}$", message = "{provider.address.line1.length.invalid}"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.address.line1.spchar.num.error}"),
    })
    private String providerAddressLine1;

    @Pattern.List({
            @Pattern(regexp = "^.{1,35}$", message = "{provider.address.line2.length.invalid}"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.address.line2.spchar.num.error}"),
    })
    private String providerAddressLine2;

    @NotNull(message = "{provider.city.or.town.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,22}$", message = "{provider.city.or.town.length.invalid}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.city.or.town.spchar.num.error}"),
                    @Pattern(regexp = "^[ A-Za-z0-9\\.\\-\\'\\,\\*]*$", message = "{provider.city.or.town.allowed.spchar.error}")
            }
    )
    private String providerCityOrTown;

    @NotBlank(message = "{provider.contact.number.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,10}$", message = "{provider.contact.number.length.invalid}"),
                    @Pattern(regexp = "[0-9]*", message = "{provider.contact.number.format.invalid}")
            }
    )
    private String providerContactNo;

    @NotNull(message = "{provider.country.length.invalid}")
    @Pattern(regexp = "^[A-z]{1,25}$", message = "{provider.country.length.invalid}")
    private String providerCountry;

    @NotNull(message = "{provider.ein.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,10}$", message = "{provider.ein.length.invalid}"),
                    @Pattern(regexp = "(\\d{9}|\\d{2}-\\d{7})", message = "{provider.ein.format.invalid}")
            }
    )
    private String providerIdentificationNumber;

    @NotBlank(message = "{provider.name.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,75}$", message = "{provider.name.length.invalid}"),
                    @Pattern(regexp = "(^[^\\&\\'\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{provider.name.spchar.exclusion.error}"),
                    @Pattern(regexp = "^(?:(?!--).)+$", message = "{provider.name.spchar.exclusion.error}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.name.spchar.num.error}")
            }
    )
    private String providerName;

    @NotNull(message = "{provider.state.or.province.length.invalid}")
    @Pattern(regexp = "^.{1,2}$", message = "{provider.state.or.province.length.invalid}")
    @USStateCode(acceptValues = StateCode.class, message = "{provider.state.or.province.mismatch}")
    private String providerStateOrProvince;

    @NotNull(message = "{provider.zip.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "[0-9]*", message = "{provider.zip.content.invalid}"),
                    @Pattern(regexp = "^.{5,9}$", message = "{provider.zip.min.max.length.invalid}"),
                    @Pattern(regexp = "^(?!00000).+", message = "{provider.zip.all.zeros.error}")
            }
    )
    private String providerZipOrPostalCode;

    @NotNull(message = "{recipient.address.line1.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,35}$", message = "{recipient.address.line1.length.invalid}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.address.line1.spchar.num.error}"),
            }
    )
    private String recipientAddressLine1;

    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,35}$", message = "{recipient.address.line2.length.invalid}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.address.line2.spchar.num.error}"),
            }
    )
    private String recipientAddressLine2;

    @NotNull(message = "{recipient.city.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,22}$", message = "{recipient.city.length.invalid}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.city.spchar.num.error}"),
                    @Pattern(regexp = "^[ A-Za-z0-9\\.\\-\\'\\,]*$", message = "{recipient.city.allowed.spchar.error}")
            }
    )
    private String recepientCity;

    private String recipientDobStr;

    @NotNull(message = "{recipient.dob.length.invalid}")
    @DateTimeFormat(pattern = BatchConstants.DATE_FORMAT)
    private Date recipientDob;

    @NotBlank(message = "{recipient.first.name.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,20}$", message = "{recipient.first.name.length.invalid}"),
                    @Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.first.name.spchar.exclusion.error}"),
                    @Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.first.name.spchar.exclusion.error}"),
                    @Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.first.name.spchar.num.error}")
            }
    )
    private String recipientFirstName;

    @NotBlank(message = "{recipient.last.name.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,20}$", message = "{recipient.last.name.length.invalid}"),
                    @Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.last.name.spchar.exclusion.error}"),
                    @Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.last.name.spchar.exclusion.error}"),
                    @Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.last.name.spchar.num.error}")
            }
    )
    private String recipientLastName;

    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,20}$", message = "{recipient.middle.name.length.invalid}"),
                    @Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.middle.name.spchar.exclusion.error}"),
                    @Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.middle.name.spchar.exclusion.error}"),
                    @Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.middle.name.spchar.num.error}")
            }
    )
    private String recipientMiddleName;

    @Pattern.List(
            {
                    @Pattern(regexp = "^\\d{9}$", message = "{recipient.ssn.length.invalid}"),
                    @Pattern(regexp = "^(?!666).+", message = "{recipient.ssn.begin.value.error}"),
                    @Pattern(regexp = "^(?!000).+", message = "{recipient.ssn.begin.value.error}"),
                    @Pattern(regexp = "(^[0-8]\\w*$)", message = "{recipient.ssn.begin.value.error}")
            }
    )
    private String recipientSsn;

    @NotNull(message = "{recipient.state.code.invalid}")
    @Pattern(regexp = "[a-zA-Z]{2}", message = "{recipient.state.code.invalid}")
    @USStateCode(acceptValues = StateCode.class, message = "{recipient.state.code.mismatch}")
    private String recipientState;

    @Pattern.List(
            {
                    @Pattern(regexp = "^.{1,10}$", message = "{recipient.suffix.name.length.invalid}"),
                    @Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.suffix.name.spchar.exclusion.error}"),
                    @Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.suffix.name.spchar.exclusion.error}"),
                    @Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.suffix.name.spchar.num.error}")
            }
    )
    private String recepientSuffixName;

    @Pattern(regexp = "^[0-9]{9}$", message = "{recipient.tin.length.invalid}")
    private String recipientTin;

    @Pattern(
            regexp = "^\\d{4}$", message = "{recipient.zip4.length.invalid}"
    )
    private String recepientZip4;

    @NotNull(message = "{recipient.zip5.length.invalid}")
    @Pattern.List(
            {
                    @Pattern(regexp = "^[0-9]{5}$", message = "{recipient.zip5.length.invalid}"),
            }
    )
    private String recepientZip5;
    @Pattern(regexp = "[0-9]{0,15}", message = "{responsible.person.unique.id.value.num.length.error}")
    private String responsiblePersonUniqueId;

    @Pattern(regexp = "^.{0}$", message = "{shop.identifier.value.error}")
    private String shopIdentifier;

    @NotEmpty(message = "{recipient.email.value.required.error}")
    @AcceptedValues(acceptValues = {"Y", "N"}, message = "{recipient.email.value.required.error}")
    private String mailedForm;

    private String updatedBy;

    private String updatedDt;

    @Pattern(regexp = "[0-9]{0,25}", message = ErrorMessageConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE_REQ_VALUE)
    private String zipOrPostalCode;

    private List<FilerCoverageDto> filerCoverages = new ArrayList<>();

    private BatchInfoDto batchInfo;

    private String
            jan = "0",
            feb = "0",
            mar = "0",
            apr = "0",
            may = "0",
            jun = "0",
            jul = "0",
            aug = "0",
            sep = "0",
            oct = "0",
            nov = "0",
            dec = "0";
    private int lineNumber;

    public FilerDemographicDto() {
    }

    /* This is a constructor to deep-clone another object */
    public FilerDemographicDto(FilerDemographicDto another) {
        /* Clone immutable objects */
        this.active = another.isActive();
        this.originalCoverageBeginDtStr = another.getOriginalCoverageBeginDtStr();
        this.comments = another.getComments();
        this.transmissionStatusCode = another.getTransmissionStatusCode();
        this.communicationPreference = another.getCommunicationPreference();
        this.correction = another.getCorrection();
        this.correctionDt = another.getCorrectionDt();
        this.eMail = another.geteMail();
        this.employerAddressLine1 = another.getEmployerAddressLine1();
        this.employerAddressLine2 = another.getEmployerAddressLine2();
        this.employerCityOrTown = another.getEmployerCityOrTown();
        this.employerContactNo = another.getEmployerContactNo();
        this.employerCountry = another.getEmployerCountry();
        this.employerIdentificationNumber = another.getEmployerIdentificationNumber();
        this.employerName = another.getEmployerName();
        this.employerStateOrProvince = another.getEmployerStateOrProvince();
        this.filerStatus = another.getFilerStatus();
        this.formStatus = another.getFormStatus();
        this.languagePreference = another.getLanguagePreference();
        this.policyOrigin = another.getPolicyOrigin();
        this.providerAddressLine1 = another.getProviderAddressLine1();
        this.providerAddressLine2 = another.getProviderAddressLine2();
        this.providerCityOrTown = another.getProviderCityOrTown();
        this.providerContactNo = another.getProviderContactNo();
        this.providerCountry = another.getProviderCountry();
        this.providerIdentificationNumber = another.getProviderIdentificationNumber();
        this.providerName = another.getProviderName();
        this.providerStateOrProvince = another.getProviderStateOrProvince();
        this.providerZipOrPostalCode = another.getProviderZipOrPostalCode();
        this.recipientAddressLine1 = another.getRecipientAddressLine1();
        this.recipientAddressLine2 = another.getRecipientAddressLine2();
        this.recepientCity = another.getRecipientCity();
        this.recipientDobStr = another.getRecipientDobStr();
        this.recipientDob = another.getRecipientDob();
        this.recipientFirstName = another.getRecipientFirstName();
        this.recipientLastName = another.getRecipientLastName();
        this.recipientMiddleName = another.getRecipientMiddleName();
        this.recipientSsn = another.getRecipientSsn();
        this.recipientState = another.getRecipientState();
        this.recepientSuffixName = another.getRecepientSuffixName();
        this.recipientTin = another.getRecipientTin();
        this.recepientZip4 = another.getRecepientZip4();
        this.recepientZip5 = another.getRecepientZip5();
        this.responsiblePersonUniqueId = another.getResponsiblePersonUniqueId();
        this.shopIdentifier = another.getShopIdentifier();
        this.mailedForm = another.getMailedForm();
        this.updatedBy = another.getUpdatedBy();
        this.updatedDt = another.getUpdatedDt();
        this.zipOrPostalCode = another.getZipOrPostalCode();
        this.jan = another.getJan();
        this.feb = another.getFeb();
        this.mar = another.getMar();
        this.apr = another.getApr();
        this.may = another.getMay();
        this.jun = another.getJun();
        this.jul = another.getJul();
        this.aug = another.getAug();
        this.sep = another.getSep();
        this.oct = another.getOct();
        this.nov = another.getNov();
        this.dec = another.getDec();
        this.lineNumber = another.getLineNumber();


        /* Clone mutable objects */
        /* Current doing shallow-clone for mutable objects (quick and dirty) */
        /* TODO: create deep-clone constructors for these mutable objects */
        this.id = another.getId();
        this.filerCoverages = another.getFilerCoverages();
        this.batchInfo = another.getBatchInfo();

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getOriginalCoverageBeginDtStr() {
        return originalCoverageBeginDtStr;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTransmissionStatusCode() {
        return transmissionStatusCode;
    }

    public String getCommunicationPreference() {
        return this.communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getCorrection() {
        return this.correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public Date getCorrectionDt() {
        return this.correctionDt;
    }

    public void setCorrectionDt(Date correctionDt) {
        this.correctionDt = correctionDt;
    }

    /**
     * @return the eMail
     */
    public String geteMail() {
        return eMail;
    }

    public String getEmployerAddressLine1() {
        return this.employerAddressLine1;
    }

    public void setEmployerAddressLine1(String employerAddressLine1) {
        this.employerAddressLine1 = employerAddressLine1;
    }

    public String getEmployerAddressLine2() {
        return this.employerAddressLine2;
    }

    public void setEmployerAddressLine2(String employerAddressLine2) {
        this.employerAddressLine2 = employerAddressLine2;
    }

    public String getEmployerCityOrTown() {
        return this.employerCityOrTown;
    }

    public void setEmployerCityOrTown(String employerCityOrTown) {
        this.employerCityOrTown = employerCityOrTown;
    }

    public String getEmployerContactNo() {
        return this.employerContactNo;
    }

    public void setEmployerContactNo(String employerContactNo) {
        this.employerContactNo = employerContactNo;
    }

    public String getEmployerCountry() {
        return this.employerCountry;
    }

    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    public String getEmployerIdentificationNumber() {
        return this.employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public String getEmployerName() {
        return this.employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerStateOrProvince() {
        return this.employerStateOrProvince;
    }

    public void setEmployerStateOrProvince(String employerStateOrProvince) {
        this.employerStateOrProvince = employerStateOrProvince;
    }

    public String getFilerStatus() {
        return this.filerStatus;
    }

    public void setFilerStatus(String filerStatus) {
        this.filerStatus = filerStatus;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(final String formStatus) {
        this.formStatus = formStatus;
    }

    public String getLanguagePreference() {
        return this.languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getPolicyOrigin() {
        return this.policyOrigin;
    }

    public void setPolicyOrigin(String policyOrigin) {
        this.policyOrigin = policyOrigin;
    }

    public String getProviderAddressLine1() {
        return this.providerAddressLine1;
    }

    public void setProviderAddressLine1(String providerAddressLine1) {
        this.providerAddressLine1 = providerAddressLine1;
    }

    public String getProviderAddressLine2() {
        return this.providerAddressLine2;
    }

    public void setProviderAddressLine2(String providerAddressLine2) {
        this.providerAddressLine2 = providerAddressLine2;
    }

    public String getProviderCityOrTown() {
        return this.providerCityOrTown;
    }

    public void setProviderCityOrTown(String providerCityOrTown) {
        this.providerCityOrTown = providerCityOrTown;
    }

    public String getProviderContactNo() {
        return this.providerContactNo;
    }

    public void setProviderContactNo(String providerContactNo) {
        this.providerContactNo = providerContactNo;
    }

    public String getProviderCountry() {
        return this.providerCountry;
    }

    public void setProviderCountry(String providerCountry) {
        this.providerCountry = providerCountry;
    }

    public String getProviderIdentificationNumber() {
        return this.providerIdentificationNumber;
    }

    public void setProviderIdentificationNumber(String providerIdentificationNumber) {
        this.providerIdentificationNumber = providerIdentificationNumber;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderStateOrProvince() {
        return this.providerStateOrProvince;
    }

    public void setProviderStateOrProvince(String providerStateOrProvince) {
        this.providerStateOrProvince = providerStateOrProvince;
    }

    public String getProviderZipOrPostalCode() {
        return this.providerZipOrPostalCode;
    }

    public void setProviderZipOrPostalCode(String providerZipOrPostalCode) {
        this.providerZipOrPostalCode = providerZipOrPostalCode;
    }

    public String getRecipientAddressLine1() {
        return this.recipientAddressLine1;
    }

    public void setRecipientAddressLine1(String recipientAddressLine1) {
        this.recipientAddressLine1 = recipientAddressLine1;
    }

    public String getRecipientAddressLine2() {
        return this.recipientAddressLine2;
    }

    public void setRecipientAddressLine2(String recipientAddressLine2) {
        this.recipientAddressLine2 = recipientAddressLine2;
    }

    public String getRecipientCity() {
        return this.recepientCity;
    }

    public void setRecipientCity(String recepientCity) {
        this.recepientCity = recepientCity;
    }

    public String getRecipientDobStr() {
        return this.recipientDobStr;
    }

    public void setRecipientDobStr(String recipientDobStr) {
        this.recipientDobStr = recipientDobStr;
    }

    public Date getRecipientDob() {
        return this.recipientDob;
    }

    public void setRecipientDob(Date recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getRecipientFirstName() {
        return this.recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return this.recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public String getRecipientMiddleName() {
        return this.recipientMiddleName;
    }

    public void setRecipientMiddleName(String recipientMiddleName) {
        this.recipientMiddleName = recipientMiddleName;
    }

    public String getRecipientSsn() {
        return this.recipientSsn;
    }

    public void setRecipientSsn(String recipientSsn) {
        this.recipientSsn = recipientSsn;
    }

    public String getRecipientState() {
        return this.recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecepientSuffixName() {
        return this.recepientSuffixName;
    }

    public void setRecepientSuffixName(String recepientSuffixName) {
        this.recepientSuffixName = recepientSuffixName;
    }

    public String getRecipientTin() {
        return this.recipientTin;
    }

    public void setRecipientTin(String recipientTin) {
        this.recipientTin = recipientTin;
    }

    public String getRecepientZip4() {
        return this.recepientZip4;
    }

    public String getRecepientZip5() {
        return this.recepientZip5;
    }

    public String getResponsiblePersonUniqueId() {
        return this.responsiblePersonUniqueId;
    }

    public void setResponsiblePersonUniqueId(String responsiblePersonUniqueId) {
        this.responsiblePersonUniqueId = responsiblePersonUniqueId;
    }

    public String getShopIdentifier() {
        return this.shopIdentifier;
    }

    public void setShopIdentifier(String shopIdentifier) {
        this.shopIdentifier = shopIdentifier;
    }

    public String getMailedForm() {
        return mailedForm;
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

    public String getZipOrPostalCode() {
        return this.zipOrPostalCode;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getFeb() {
        return feb;
    }

    public void setFeb(String feb) {
        this.feb = feb;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getOct() {
        return oct;
    }

    public void setOct(String oct) {
        this.oct = oct;
    }

    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public FilerDemographicPKDto getId() {
        return this.id;
    }

    public void setId(FilerDemographicPKDto id) {
        this.id = id;
    }

    /**
     * @return the filerCoverages
     */
    public List<FilerCoverageDto> getFilerCoverages() {
        return filerCoverages;
    }

    /**
     * @return the batchInfo
     */
    public BatchInfoDto getBatchInfo() {
        return batchInfo;
    }

    /**
     * @param batchInfo the batchInfo to set
     */
    public void setBatchInfo(BatchInfoDto batchInfo) {
        this.batchInfo = batchInfo;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setZipOrPostalCode(String zipOrPostalCode) {
        this.zipOrPostalCode = zipOrPostalCode;
    }

    public void setMailedForm(final String mailedForm) {
        this.mailedForm = mailedForm;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setTransmissionStatusCode(final String transmissionStatusCode) {
        this.transmissionStatusCode = transmissionStatusCode;
    }

    public static Date getHighestCoverageEndDate(final List<FilerDemographicCacheDto> cacheDtos) {
        if (CollectionUtils.isNotEmpty(cacheDtos)) {
            Collections.sort(cacheDtos, new Comparator<FilerDemographicCacheDto>() {
                @Override
                public int compare(FilerDemographicCacheDto o1, FilerDemographicCacheDto o2) {
                    return ObjectUtils.compare(
                            o2.originalCoverageEndDt, o1.originalCoverageEndDt);
                }
            });
            final String originalCoverageEndDtStr = cacheDtos.get(0).originalCoverageEndDt;
            try {
                return MMDDYYYY.parse(originalCoverageEndDtStr);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public static int indexOfByPK(
            final FilerDemographicPKDto pk,
            final List<? extends FilerDemographicDto> itemsToSearch) {
        if (CollectionUtils.isNotEmpty(itemsToSearch)) {
            final int numItems = itemsToSearch.size();
            for (int i = 0; i < numItems; i++) {
                final FilerDemographicDto fd = itemsToSearch.get(i);
                if (Objects.equals(pk, fd.getId())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static Set<FilerDemographicPKDto> getAllIds(final List<FilerDemographicDto> items) {
        final Set<FilerDemographicPKDto> ids = new LinkedHashSet<>(items.size());
        for (final FilerDemographicDto fd : items) {
            ids.add(fd.getId());
        }
        return ids;
    }

    public boolean hasIndividualIdentity() {
        return (recipientSsn != null || recipientTin != null) && recipientDobStr != null;
    }

    public boolean isInsert() {
        return insert;
    }

    public void isInsert(final boolean insert) {
        this.insert = insert;
    }

    public String getRecordKey() {
        return id == null ? null : id.toString();
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setRecipientZip4(String recepientZip4) {
        this.recepientZip4 = recepientZip4;
    }

    public void setRecipientZip5(String recepientZip5) {
        this.recepientZip5 = recepientZip5;
    }

    public void addFilerCoverage(final FilerCoverageDto filerCoverage) {
        updateCoverageMonths(filerCoverage);
        originalCoverageBeginDtStr = filerCoverage.getOrigCoverageBeginDtStr();
        filerCoverages.add(filerCoverage);
    }

    public void updateCoverageMonths(final FilerCoverageDto filerCoverage) {
        for (final String upperCaseMonth : COVERAGE_MONTHS) {
            final String month = upperCaseMonth.toLowerCase();
            try {
                final String coverageDtoMonth = BeanUtils
                        .getSimpleProperty(filerCoverage, month);
                if ("1".equals(coverageDtoMonth)) {
                    BeanUtils.copyProperty(this, month, "1");
                }
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void updateCoverageMonths(final FilerDemographicCacheDto cacheDto) {
        if (!"N".equals(filerStatus)) {
            for (int i = 0; i < COVERAGE_MONTHS.length; i++) {
                final String month = COVERAGE_MONTHS[i].toLowerCase();
                try {
                    final boolean dtoCovered = "1".equals(BeanUtils.getProperty(this, month));
                    final boolean cacheDtoCovered = cacheDto.coverageMonths[i];
                    if (dtoCovered || cacheDtoCovered) {    // if one is true, make sure both are true
                        BeanUtils.setProperty(this, month, "1");
                        cacheDto.coverageMonths[i] = true;
                    }
                } catch (final Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void clearCoverageMonths() {
        for (int i = 0; i < COVERAGE_MONTHS.length; i++) {
            final String month = COVERAGE_MONTHS[i].toLowerCase();
            try {
                BeanUtils.setProperty(this, month, "0");
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public FilerCoverageDto getFilerCoverage() {
        return CollectionUtils.isEmpty(filerCoverages) ? null : filerCoverages.get(0);
    }

    public Set<FilerDemographicPKDto> getDifferentIds(
            final Collection<FilerDemographicCacheDto> cacheDtos) {
        final Set<FilerDemographicPKDto> ids = new HashSet<>();
        for (final FilerDemographicCacheDto cacheDto : cacheDtos) {
            final FilerDemographicPKDto cacheDtoId = cacheDto.getUniqueId();
            if (!this.id.equals(cacheDtoId)) {
                ids.add(cacheDto.getUniqueId());
            }
        }
        return ids;
    }

    public boolean isSameIndividual(final FilerDemographicCacheDto that) {
        final boolean idNumbersMatch =
                Objects.equals(this.recipientSsn, that.recipientSsn)
                        || Objects.equals(this.recipientTin, that.recipientTin);
        final boolean birthDatesMatch = Objects.equals(
                this.recipientDobStr, that.recipientDob);
        return idNumbersMatch && birthDatesMatch;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilerDemographicDto that = (FilerDemographicDto) o;

        return getId().equals(that.getId());
    }

    @Override
    public String toString() {
        return String.format(
                "FilerDemographicDto=[Primary-Key=[%s], Individual-Key=[%s]]",
                this.getId(), this.getIndividualKey());
    }

    public FilerDemographicCacheDto asCacheDto() {
        final FilerDemographicCacheDto cacheDto = new FilerDemographicCacheDto();
        cacheDto.individualKey = this.getIndividualKey();
        cacheDto.uniqueId = this.id;

        if (CollectionUtils.isNotEmpty(filerCoverages)) {
            final FilerCoverageDto filerCoverage = this.filerCoverages.get(0);
            if (filerCoverage != null) {
                cacheDto.caseApplicationId = filerCoverage.getRecipientCaseApplicationId();
                cacheDto.originalCoverageBeginDt = filerCoverage.getOrigCoverageBeginDtStr();
                cacheDto.originalCoverageEndDt = filerCoverage.getOrigCoverageEndDtStr();
                cacheDto.programName = filerCoverage.getProgramName();
            }
            for (int i = 0; i < COVERAGE_MONTHS.length; i++) {
                final String month = COVERAGE_MONTHS[i].toLowerCase();
                try {
                    final String coverageDtoMonth = BeanUtils.getSimpleProperty(this, month);
                    cacheDto.coverageMonths[i] = "1".equals(coverageDtoMonth);
                } catch (final Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        cacheDto.active = this.active;
        cacheDto.correction = this.correction;
        cacheDto.filerStatus = this.filerStatus;
        cacheDto.recipientDob = this.recipientDobStr;
        cacheDto.recipientSsn = this.recipientSsn;
        cacheDto.recipientTin = this.recipientTin;
        cacheDto.responsiblePersonUniqueId = this.responsiblePersonUniqueId;
        cacheDto.transmissionStatusCode = this.transmissionStatusCode;

        return cacheDto;
    }

    public String getIndividualKey() {
        final String individualId = this.recipientSsn != null
                ? this.recipientSsn : this.recipientTin;
        return individualId == null ? id.toString() : individualId + "-" + this.recipientDobStr;
    }

    public FilerDemographicPKDto getResponsiblePersonPK(final ListMultimap<String, FilerDemographicCacheDto> multiMap) {
        if (responsiblePersonUniqueId != null && id != null && id.getSourceCd() != null) {
            FilerDemographicPKDto pk = new FilerDemographicPKDto();
            pk.setSourceCd(id.getSourceCd());
            pk.setSourceUniqueId(responsiblePersonUniqueId);

            final FilerDemographicCacheDto cacheDto =
                    FilerDemographicCacheDto.getMostRecentForKey(pk, multiMap);
            return cacheDto != null ? cacheDto.getUniqueId() : null;
        }
        return null;
    }

    /**
     * Used to provide an immutable, lightweight representation
     * of a FilerDemographicDto object for caching.
     * <p>
     * Created by ergreen on 11/24/2015.
     */
    public static class FilerDemographicCacheDto {

        private String individualKey;
        private FilerDemographicPKDto uniqueId;
        private String caseApplicationId;
        private String correction;
        private String recipientSsn;
        private String recipientTin;
        private String recipientDob;
        private String programName;
        private String originalCoverageBeginDt;
        private String originalCoverageEndDt;
        private String filerStatus;
        private String responsiblePersonUniqueId;
        private String transmissionStatusCode;
        private boolean active;
        private boolean deactivate;
        private boolean previouslyLoaded;
        private boolean[] coverageMonths = new boolean[COVERAGE_MONTHS.length];

        public static FilerDemographicCacheDto getMostRecentForKey(
                final FilerDemographicPKDto pk,
                final ListMultimap<String, FilerDemographicCacheDto> multimap) {
            return getMostRecentForKey(pk.toString(), multimap);
        }

        public static FilerDemographicCacheDto getMostRecentForKey(
                final String key,
                final ListMultimap<String, FilerDemographicCacheDto> multimap) {
            if (multimap.containsKey(key)) {
                final List<FilerDemographicCacheDto> cacheDtos = multimap.get(key);
                final int mostRecentItemIdx = cacheDtos.size() - 1;
                return cacheDtos.get(mostRecentItemIdx);
            }
            return null;
        }

        public static Map<FilerDemographicPKDto, FilerDemographicCacheDto> byPK(final List<FilerDemographicCacheDto> cacheDtos) {
            final Map<FilerDemographicPKDto, FilerDemographicCacheDto> dtosForPK = new HashMap<>();
            for (final FilerDemographicCacheDto cacheDto : cacheDtos) {
                dtosForPK.put(cacheDto.getUniqueId(), cacheDto);
            }
            return dtosForPK;
        }

        public FilerDemographicPKDto getUniqueId() {
            return uniqueId;
        }

        public String getIndividualKey() {
            return individualKey;
        }

        public String getRecordKey() {
            return uniqueId.toString();
        }

        public String getCaseId() {
            return caseApplicationId;
        }

        public String getCorrection() {
            return correction;
        }

        public String getRecipientSsn() {
            return recipientSsn;
        }

        public String getRecipientTin() {
            return recipientTin;
        }

        public String getRecipientDob() {
            return recipientDob;
        }

        public String getProgramName() {
            return programName;
        }

        public String getOriginalCoverageBeginDt() {
            return originalCoverageBeginDt;
        }

        public String getOriginalCoverageEndDt() {
            return originalCoverageEndDt;
        }

        public String getFilerStatus() {
            return filerStatus;
        }

        public String getResponsiblePersonUniqueId() {
            return responsiblePersonUniqueId;
        }

        public boolean setCovered(final int month) {
            return this.coverageMonths[month];
        }

        public String getTransmissionStatusCode() {
            return transmissionStatusCode;
        }

        public boolean isActive() {
            return active;
        }

        public boolean isDeactivate() {
            return deactivate;
        }

        public void setDeactivate(final boolean deactivate) {
            this.deactivate = deactivate;
        }

        public boolean isPreviouslyLoaded() {
            return previouslyLoaded;
        }

        public void setPreviouslyLoaded(final boolean previouslyLoaded) {
            this.previouslyLoaded = previouslyLoaded;
        }

        public void updateIndividualKey(final FilerDemographicDto fd) {
            this.recipientSsn = fd.getRecipientSsn();
            this.recipientTin = fd.getRecipientTin();
            this.recipientDob = fd.getRecipientDobStr();
            this.individualKey = fd.getIndividualKey();
        }

        public void orCoverageMonths(final FilerDemographicCacheDto that) {
            for (int i = 0; i < coverageMonths.length; i++) {
                if (this.coverageMonths[i] || that.coverageMonths[i]) {
                    this.coverageMonths[i] = that.coverageMonths[i] = true;
                }
            }
        }

        public String getDeactivateCompareKey() {
            return originalCoverageBeginDt + '-' + caseApplicationId;
        }

        @Override
        public String toString() {
            return String.format(
                    "FilerDemographicDto=[Primary-Key=[%s], Individual-Key=[%s]]",
                    uniqueId, individualKey);
        }
    }
}
