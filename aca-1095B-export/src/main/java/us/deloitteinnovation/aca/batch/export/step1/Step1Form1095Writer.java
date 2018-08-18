package us.deloitteinnovation.aca.batch.export.step1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Renders each Form1095BUpstreamDetailType as encapsulated within the list of Form1095bProcessDto objects.
 * Stores the entire Form1095bProcessDto, along with rendered XML to the ExportJob1095Repository.
 *
 * @see Form1095BUpstreamDetailType
 * @see ExportJob1095Repository
 * @see Step1Form1095Dto
 */
@Component
public class Step1Form1095Writer implements ItemWriter<Step1Form1095Dto> {
    private static final Logger LOG = LoggerFactory.getLogger(Step1Form1095Writer.class);

    StepExecution stepExecution;

    @Autowired
    @Qualifier("jaxb2FragmentMarshaller")
    protected Jaxb2Marshaller jaxb2FragmentMarshaller;

    private ObjectFactory air7ObjectFactory = new ObjectFactory();

    @Autowired
    ExportJob1095Repository exportJobRepository;


    /**
     * Renders the Form1095bProcessDto data as XML and stores it within a Step1Form1095Dto object
     * in the ExportJob1095Repository.
     *
     * @param aca1095Forms
     * @throws Exception
     */
    @Override
    public void write(List<? extends Step1Form1095Dto> aca1095Forms) throws Exception {
        String xmlString;
        for (Step1Form1095Dto dto : aca1095Forms) {
            xmlString = writeAsXml(dto.form1095BUpstreamDetailType, this.jaxb2FragmentMarshaller, this.air7ObjectFactory);

            if (LOG.isDebugEnabled()) {
                LOG.trace("Form 1095B for source code {} and source unique id {}:\n{}", dto.getFiler().getSourceCd(), dto.getFiler().getSourceUniqueId(), xmlString);
            }
            dto.setRawXml(xmlString.getBytes(Charset.forName("UTF-8")));
            exportJobRepository.save(dto, stepExecution);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Step1 write finished size {}", aca1095Forms.size());
        }
    }

    /**
     * Helper method to render a Form1095B as XML.
     *
     * @param form1095b
     * @param marshaller
     * @param air7ObjectFactory
     * @return Form 1095B data as an XML String.
     */
    protected static String writeAsXml(Form1095BUpstreamDetailType form1095b, Jaxb2Marshaller marshaller, ObjectFactory air7ObjectFactory) {
        // Wrap the IRS data object in the SOAP wrapper
        JAXBElement<Form1095BUpstreamDetailType> elementWrapper = air7ObjectFactory.createForm1095BUpstreamDetail(form1095b);
        StringWriter writer = new StringWriter();
        marshaller.marshal(elementWrapper, new StreamResult(writer));
        return writer.toString();
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

}
