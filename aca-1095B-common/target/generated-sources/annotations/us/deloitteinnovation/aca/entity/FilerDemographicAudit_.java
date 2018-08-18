package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FilerDemographicAudit.class)
public abstract class FilerDemographicAudit_ {

	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientAddressLine2;
	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientAddressLine1;
	public static volatile SingularAttribute<FilerDemographicAudit, String> comments;
	public static volatile SingularAttribute<FilerDemographicAudit, String> updatedBy;
	public static volatile SingularAttribute<FilerDemographicAudit, Integer> auditSeqNo;
	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientState;
	public static volatile SingularAttribute<FilerDemographicAudit, Long> sourceUniqueId;
	public static volatile SingularAttribute<FilerDemographicAudit, String> sourceCd;
	public static volatile SingularAttribute<FilerDemographicAudit, Date> updatedDt;
	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientCity;
	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientZip5;
	public static volatile SingularAttribute<FilerDemographicAudit, String> recepientZip4;

}

