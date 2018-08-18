package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IrsSubmissionDetails.class)
public abstract class IrsSubmissionDetails_ {

	public static volatile SingularAttribute<IrsSubmissionDetails, String> updatedBy;
	public static volatile SingularAttribute<IrsSubmissionDetails, String> submissionStatus;
	public static volatile SingularAttribute<IrsSubmissionDetails, Long> originalTransmissionId;
	public static volatile SingularAttribute<IrsSubmissionDetails, IrsSubmissionDetailsPK> id;
	public static volatile SingularAttribute<IrsSubmissionDetails, Date> updatedDate;
	public static volatile SingularAttribute<IrsSubmissionDetails, Long> originalSubmissionId;

}

