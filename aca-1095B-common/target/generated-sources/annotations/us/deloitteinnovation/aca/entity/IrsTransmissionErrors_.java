package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IrsTransmissionErrors.class)
public abstract class IrsTransmissionErrors_ {

	public static volatile SingularAttribute<IrsTransmissionErrors, Integer> recordId;
	public static volatile SingularAttribute<IrsTransmissionErrors, Integer> transmissionId;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> errorMsgText;
	public static volatile SingularAttribute<IrsTransmissionErrors, Integer> submissionId;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> updatedBy;
	public static volatile SingularAttribute<IrsTransmissionErrors, Integer> id;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> errorId;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> errorMsgCode;
	public static volatile SingularAttribute<IrsTransmissionErrors, Date> updatedDate;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> errorElementName;
	public static volatile SingularAttribute<IrsTransmissionErrors, String> receiptId;

}

