package us.deloitteinnovation.aca.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 */
public abstract class BatchTestUtil {

    static StreamSource [] xmlSchemaSources2015 ;
    static StreamSource [] xmlSchemaSources2016 ;
    static {
        List<StreamSource> sources2015 = new ArrayList<>() ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/COMMON/IRS-CAC.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/COMMON/IRS-CBC.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/COMMON/IRS-SDT.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/EXT/IRS-EXT-ACA-AIR-7.0.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-ACABusinessHeaderMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-ACAUserInterfaceHeaderMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-Form1094-1095BCTransmitterMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-Form1094-1095BCTransmitterReqMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-Form1094-1095BTransmitterUpstreamMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-Form1094-1095CTransmitterUpstreamMessage.xsd"))) ;
        sources2015.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2015v8/MSG/IRS-WSTimeStampElementMessage.xsd"))) ;
        xmlSchemaSources2015 = sources2015.toArray(new StreamSource[sources2015.size()]) ;

        List<StreamSource> sources2016 = new ArrayList<>() ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/COMMON/IRS-CAC.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/COMMON/IRS-CBC.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/COMMON/IRS-SDT.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/EXT/IRS-EXT-ACA-AIR-1094BC.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-ACABusinessHeaderMessage.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-ACAUserInterfaceHeaderMessage.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-Form1094-1095BCTransmitterReqMessage.xsd")));
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-Form1094-1095BTransmitterUpstreamMessage.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-Form1094-1095CTransmitterUpstreamMessage.xsd"))) ;
        sources2016.add(new StreamSource(new File("./src/main/xsd/air_schemaspck_ty2016v3.3/MSG/IRS-WSTimeStampElementMessage.xsd"))) ;
        xmlSchemaSources2016 = sources2016.toArray(new StreamSource[sources2016.size()]) ;
    }


    /**
     * Creates a mock StepExecution for injection within Step tests.
     * @param jobName
     * @return
     */
    public static StepExecution createStepExecutionMock(String jobName) {
        StepExecution stepExecution = mock(StepExecution.class) ;
        JobExecution jobExecution = mock(JobExecution.class) ;
        when(stepExecution.getJobExecution()).thenReturn(jobExecution) ;
        JobInstance jobInstance = mock(JobInstance.class) ;
        when(jobExecution.getJobInstance()).thenReturn(jobInstance) ;
        when(jobInstance.getJobName()).thenReturn(jobName) ;
        ExecutionContext executionContext = mock(ExecutionContext.class);
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);
        return stepExecution ;
    }


    /**
     * Creates a test BatchInfo with the provided test name as the state code.
     * @param batchInfoDao
     * @return
     */
    public static BatchInfoDto createBatchInfo(BatchInfoDao batchInfoDao, String sourceCode, String stateCode) {
        BatchInfoDto info = new BatchInfoDto() ;
        info.setSourceCode(sourceCode);
        info.setStateCd(stateCode);
        batchInfoDao.save(info) ;
        return info ;
    }

    /**
     * @param xmlFile      Location of the XML file.
     * @throws Exception
     */
    public static void validateIrsAcaXml(File xmlFile) throws Exception {
        validateXml(xmlFile,xmlSchemaSources2015 );
    }

    public static void validateIrsAcaXml(File xmlFile, Long taxYear) throws Exception {
        if (2015 == taxYear) {
            validateXml(xmlFile,xmlSchemaSources2015);
        } else if (2016 == taxYear) {
            validateXml(xmlFile,xmlSchemaSources2016);
        }
    }

    /**
     * Validates an XML file given a set of XSD schema documents.
     * @param xmlFile
     * @param schemaDocuments
     * @throws Exception
     */
    public static void validateXml(File xmlFile, StreamSource[] schemaDocuments) throws Exception {
        Source xmlSource = new StreamSource(xmlFile);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaDocuments);
        Validator validator = schema.newValidator();
        validator.validate(xmlSource);
    }


    /**
     * Test utility entry point to validate a single IRS XML file.
     * @param args  First argument should be the location of the file to validate.
     */
    public static void main (String args []) throws Exception {
        validateIrsAcaXml(new File(args[0]));
    }

}
