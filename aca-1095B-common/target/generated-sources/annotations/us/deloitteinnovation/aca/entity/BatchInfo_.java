package us.deloitteinnovation.aca.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BatchInfo.class)
public abstract class BatchInfo_ {

	public static volatile SingularAttribute<BatchInfo, Integer> totalPass;
	public static volatile SingularAttribute<BatchInfo, Date> receiveDt;
	public static volatile SingularAttribute<BatchInfo, String> fileName;
	public static volatile SingularAttribute<BatchInfo, Integer> batchId;
	public static volatile SingularAttribute<BatchInfo, String> agencyCd;
	public static volatile SingularAttribute<BatchInfo, Integer> totalCount;
	public static volatile ListAttribute<BatchInfo, FilerDemographic> filerDemographics;
	public static volatile SingularAttribute<BatchInfo, String> requisitionId;
	public static volatile SingularAttribute<BatchInfo, Integer> totalFail;
	public static volatile SingularAttribute<BatchInfo, String> invoiceUid;
	public static volatile SingularAttribute<BatchInfo, String> systemCd;
	public static volatile SingularAttribute<BatchInfo, String> stateCd;
	public static volatile SingularAttribute<BatchInfo, Integer> exceptionReportId;
	public static volatile SingularAttribute<BatchInfo, String> batchType;

}

