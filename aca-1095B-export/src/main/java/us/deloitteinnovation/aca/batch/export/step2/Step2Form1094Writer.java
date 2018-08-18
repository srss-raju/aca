package us.deloitteinnovation.aca.batch.export.step2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.deloitteinnovation.aca.batch.export.ExportJob1094Repository;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.util.StaticTimer;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import javax.xml.bind.JAXBElement;
import java.io.StringWriter;
import java.util.List;

/**
 * Renders each Form1094BUpstreamDetailType as encapsulated within the list of Step2Form1094Dto objects.
 * Stores the entire Step2Form1094Dto, along with rendered XML to the ExportJob1095Repository.
 * @see Form1094BUpstreamDetailType
 * @see ExportJob1095Repository
 * @see Step2Form1094Dto
 */
public class Step2Form1094Writer implements ItemWriter<Step2Form1094Dto> {

    private static final Logger LOG = LoggerFactory.getLogger(Step2Form1094Writer.class) ;

    @Autowired
    protected Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    ExportJob1094Repository exportJob1094Repository ;

    @Override
    public void write(List<? extends Step2Form1094Dto> aca1094Forms) throws Exception {
        StaticTimer.start();
        for (Step2Form1094Dto dto : aca1094Forms) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Generating Form 1094B for ein {} and tax year {}",
                        dto.getSourceSystemConfig().getProviderIdentificationNumber(),
                        dto.getSourceSystemConfig().getTaxYear());
            }
            dto.setRawXml(new byte[1]) ;
            exportJob1094Repository.save(dto) ;
        }
        StaticTimer.stop(Step2Form1094Writer.class);
    }

}
