package us.deloitteinnovation.aca.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExceptionReport.class)
public abstract class ExceptionReport_ {

	public static volatile SingularAttribute<ExceptionReport, String> exDetails;
	public static volatile SingularAttribute<ExceptionReport, Integer> exceptionReportId;
	public static volatile SingularAttribute<ExceptionReport, Long> sourceUniqueId;
	public static volatile SingularAttribute<ExceptionReport, BatchInfo> batchInfo;
	public static volatile SingularAttribute<ExceptionReport, Integer> rowNumber;

}

