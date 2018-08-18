package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IrsTransmissionDetails.class)
public abstract class IrsTransmissionDetails_ {

	public static volatile SingularAttribute<IrsTransmissionDetails, Date> transmissionAckDate;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> updatedBy;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> sourceCd;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> transmissionAckStatus;
	public static volatile SingularAttribute<IrsTransmissionDetails, Date> updatedDate;
	public static volatile SingularAttribute<IrsTransmissionDetails, Date> transferDate;
	public static volatile SingularAttribute<IrsTransmissionDetails, Date> transmissionDate;
	public static volatile SingularAttribute<IrsTransmissionDetails, Integer> transmissionId;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> transmissionFormType;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> transmissionFileName;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> transmissionReceiptId;
	public static volatile SingularAttribute<IrsTransmissionDetails, Integer> taxYear;
	public static volatile SingularAttribute<IrsTransmissionDetails, String> transmissionTypeCd;

}

