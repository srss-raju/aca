package us.deloitteinnovation.aca.batch.export.step2;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 * Renders each Form1094BUpstreamDetailType as encapsulated within the list of
 * Step2Form1094Dto objects. Stores the entire Step2Form1094Dto, along with
 * rendered XML to the ExportJob1095Repository.
 * 
 * @see Form1094BUpstreamDetailType
 * @see ExportJob1095Repository
 * @see Step2Form1094Dto
 */
public class Step2Form1094PrintVendorWriter implements
		ItemWriter<Step2Form1094Dto> {

	private static final Logger LOG = LoggerFactory.getLogger(Step2Form1094PrintVendorWriter.class);
	StepExecution stepExecution;

	@Autowired
	protected Jaxb2Marshaller jaxb2Marshaller;

	@Override
	public void write(List<? extends Step2Form1094Dto> aca1094Forms)
			throws Exception {
		stepExecution.getJobExecution().getExecutionContext().put("FORM1094BList",aca1094Forms) ;
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
