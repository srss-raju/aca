package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrintDetail.class)
public abstract class PrintDetail_ {

	public static volatile SingularAttribute<PrintDetail, String> printReason;
	public static volatile SingularAttribute<PrintDetail, String> updatedBy;
	public static volatile SingularAttribute<PrintDetail, Date> createdDate;
	public static volatile SingularAttribute<PrintDetail, String> printStatus;
	public static volatile SingularAttribute<PrintDetail, String> originalFormStatus;
	public static volatile SingularAttribute<PrintDetail, Date> updatedDt;
	public static volatile SingularAttribute<PrintDetail, PrintDetailPK> id;
	public static volatile SingularAttribute<PrintDetail, Integer> correctionIndicator;
	public static volatile SingularAttribute<PrintDetail, Integer> batchId;
	public static volatile SingularAttribute<PrintDetail, Date> lastMailRequestedDate;
	public static volatile SingularAttribute<PrintDetail, Date> mailedDate;
	public static volatile SingularAttribute<PrintDetail, Date> acknowledgeDate;

}

