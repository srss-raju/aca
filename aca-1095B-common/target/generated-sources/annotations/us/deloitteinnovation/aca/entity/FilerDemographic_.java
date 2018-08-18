package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FilerDemographic.class)
public abstract class FilerDemographic_ {

	public static volatile SingularAttribute<FilerDemographic, Character> jul;
	public static volatile SingularAttribute<FilerDemographic, Character> communicationPreference;
	public static volatile SingularAttribute<FilerDemographic, Character> feb;
	public static volatile SingularAttribute<FilerDemographic, Character> jun;
	public static volatile SingularAttribute<FilerDemographic, Character> dec;
	public static volatile SingularAttribute<FilerDemographic, String> recepientFirstName;
	public static volatile SingularAttribute<FilerDemographic, String> providerIdentificationNumber;
	public static volatile SingularAttribute<FilerDemographic, String> eMail;
	public static volatile SingularAttribute<FilerDemographic, Date> recepientDob;
	public static volatile SingularAttribute<FilerDemographic, Character> jan;
	public static volatile SingularAttribute<FilerDemographic, FilerDemographicPK> id;
	public static volatile SingularAttribute<FilerDemographic, String> recepientCity;
	public static volatile SingularAttribute<FilerDemographic, String> providerName;
	public static volatile SingularAttribute<FilerDemographic, String> recepientLastName;
	public static volatile SingularAttribute<FilerDemographic, Character> oct;
	public static volatile SingularAttribute<FilerDemographic, String> providerAddressLine2;
	public static volatile SingularAttribute<FilerDemographic, String> recepientAddressLine2;
	public static volatile SingularAttribute<FilerDemographic, Character> apr;
	public static volatile SingularAttribute<FilerDemographic, String> providerAddressLine1;
	public static volatile SingularAttribute<FilerDemographic, String> recepientAddressLine1;
	public static volatile SingularAttribute<FilerDemographic, String> updatedBy;
	public static volatile SingularAttribute<FilerDemographic, Character> filerStatus;
	public static volatile SingularAttribute<FilerDemographic, BatchInfo> batchInfo;
	public static volatile ListAttribute<FilerDemographic, FilerDemographicAudit> demographicAudits;
	public static volatile SingularAttribute<FilerDemographic, Long> filerDemoSeq;
	public static volatile SingularAttribute<FilerDemographic, String> employerAddressLine2;
	public static volatile SingularAttribute<FilerDemographic, String> employerAddressLine1;
	public static volatile SingularAttribute<FilerDemographic, String> languagePreference;
	public static volatile SingularAttribute<FilerDemographic, Character> aug;
	public static volatile SingularAttribute<FilerDemographic, Long> providerContactNo;
	public static volatile SingularAttribute<FilerDemographic, String> formStatus;
	public static volatile SingularAttribute<FilerDemographic, String> correction;
	public static volatile SingularAttribute<FilerDemographic, String> status;
	public static volatile ListAttribute<FilerDemographic, FilerCoverage> filerCoverages;
	public static volatile SingularAttribute<FilerDemographic, String> recepientSuffixName;
	public static volatile SingularAttribute<FilerDemographic, String> employerName;
	public static volatile SingularAttribute<FilerDemographic, String> employerStateOrProvince;
	public static volatile SingularAttribute<FilerDemographic, String> recepientZip5;
	public static volatile SingularAttribute<FilerDemographic, String> recepientZip4;
	public static volatile SingularAttribute<FilerDemographic, String> employerCityOrTown;
	public static volatile SingularAttribute<FilerDemographic, Long> employerContactNo;
	public static volatile SingularAttribute<FilerDemographic, String> employerIdentificationNumber;
	public static volatile SingularAttribute<FilerDemographic, String> mailedForm;
	public static volatile SingularAttribute<FilerDemographic, Character> nov;
	public static volatile SingularAttribute<FilerDemographic, String> providerStateOrProvince;
	public static volatile SingularAttribute<FilerDemographic, Date> correctionDt;
	public static volatile SingularAttribute<FilerDemographic, Character> mar;
	public static volatile SingularAttribute<FilerDemographic, Character> sep;
	public static volatile SingularAttribute<FilerDemographic, String> employerCountry;
	public static volatile SingularAttribute<FilerDemographic, String> providerCountry;
	public static volatile SingularAttribute<FilerDemographic, String> comments;
	public static volatile SingularAttribute<FilerDemographic, Character> policyOrigin;
	public static volatile SingularAttribute<FilerDemographic, Character> may;
	public static volatile SingularAttribute<FilerDemographic, String> providerZipOrPostalCode;
	public static volatile SingularAttribute<FilerDemographic, String> recepientState;
	public static volatile SingularAttribute<FilerDemographic, String> recepientMiddleName;
	public static volatile ListAttribute<FilerDemographic, PrintDetail> printDetail;
	public static volatile SingularAttribute<FilerDemographic, String> employerZipOrPostalCode;
	public static volatile SingularAttribute<FilerDemographic, String> irsTransmissionStatusCd;
	public static volatile SingularAttribute<FilerDemographic, String> recepientTin;
	public static volatile SingularAttribute<FilerDemographic, String> shopIdentifier;
	public static volatile SingularAttribute<FilerDemographic, Date> updatedDt;
	public static volatile SingularAttribute<FilerDemographic, Long> responsiblePersonUniqueId;
	public static volatile SingularAttribute<FilerDemographic, String> recepientSsn;
	public static volatile SingularAttribute<FilerDemographic, String> providerCityOrTown;

}

