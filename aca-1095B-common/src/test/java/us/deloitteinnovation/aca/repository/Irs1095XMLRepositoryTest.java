package us.deloitteinnovation.aca.repository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.Irs1095XML;
import us.deloitteinnovation.aca.entity.Irs1095XMLPK;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by bhchaganti on 4/5/2016.*/

public class Irs1095XMLRepositoryTest extends AbstractCommonTestConfig {


    @Autowired
    Irs1095XMLRepository irs1095XMLRepository ;


    @Before
    public void before() throws Exception {

        irs1095XMLRepository.clearAll("ARDHSDSS", 91660202l);
    }

    @Test@Ignore
    public void saveMinimalOriginal() throws Exception {
        StringBuilder xmlString = new StringBuilder();

        xmlString.append("<Form1095BUpstreamDetail recordType=\"String\" lineNum=\"0\">")
                .append("<RecordId>1</RecordId>").append("<CorrectedInd>0</CorrectedInd>").append("<TaxYr>2015</TaxYr>").append("</Form1095BUpstreamDetail>");

        saveAndAssert(irs1095XMLRepository,
                91660202l,
                "ARDHSDSS",
                "OR",
                xmlString.toString(),
                "irsXmlRepositoryTest") ;

        /*Irs1095XML irs1095XML1 = irs1095XMLRepository.findByTransmissionStatusCode(91660202l, "ARDHSDSS", "OR");
        assertNotNull(irs1095XML1);
*/
        Irs1095XML irs1095XML2 = irs1095XMLRepository.findByTransmissionStatusCode(91660202l, "ARDHSDSS", "RP");
        assertNull(irs1095XML2);

        irs1095XMLRepository.updateStatus("ARDHSDSS", 91660202l, "OR", "XG", "irsXmlRepositoryTest", new java.util.Date());

        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK();
        irs1095XMLPK.setSourceCd("ARDHSDSS");
        irs1095XMLPK.setSourceUniqueId(91660202l);
        Irs1095XML irs1095XML = irs1095XMLRepository.findOne(irs1095XMLPK);
        assertEquals(irs1095XML.getIrsTransmissionStatusCd(), "XG");

    }

    @Test @Ignore
    public void saveMinimalCorrection() throws Exception {
        StringBuilder xmlString = new StringBuilder();

        xmlString.append("<Form1095BUpstreamDetail recordType=\"String\" lineNum=\"0\">")
                .append("<RecordId>1</RecordId>").append("<CorrectedInd>0</CorrectedInd>").append("<TaxYr>2015</TaxYr>").append("</Form1095BUpstreamDetail>");

        saveAndAssert(irs1095XMLRepository,
                91660202l,
                "ARDHSDSS",
                "CO",
                xmlString.toString(),
                "irsXmlRepositoryTest") ;
        irs1095XMLRepository.updateStatus("ARDHSDSS",91660202l, "CO", "XG", "irsXmlRepositoryTest", new java.util.Date());

        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK();
        irs1095XMLPK.setSourceCd("ARDHSDSS");
        irs1095XMLPK.setSourceUniqueId(91660202l);
        Irs1095XML irs1095XML = irs1095XMLRepository.findOne(irs1095XMLPK);

        assertEquals(irs1095XML.getIrsTransmissionStatusCd(), "XG");
    }


    @Test @Ignore
    public void saveMinimalReplacement() throws Exception {
        StringBuilder xmlString = new StringBuilder();

        xmlString.append("<Form1095BUpstreamDetail recordType=\"String\" lineNum=\"0\">")
                .append("<RecordId>1</RecordId>").append("<CorrectedInd>0</CorrectedInd>").append("<TaxYr>2015</TaxYr>").append("</Form1095BUpstreamDetail>");


        saveAndAssert(irs1095XMLRepository,
                91660202l,
                "ARDHSDSS",
                "RP",
                xmlString.toString(),
                "irsXmlRepositoryTest") ;
        irs1095XMLRepository.updateStatus("ARDHSDSS",91660202l, "RP", "XG", "irsXmlRepositoryTest", new java.util.Date());

        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK();
        irs1095XMLPK.setSourceCd("ARDHSDSS");
        irs1095XMLPK.setSourceUniqueId(91660202l);
        Irs1095XML irs1095XML = irs1095XMLRepository.findOne(irs1095XMLPK);

        assertEquals(irs1095XML.getIrsTransmissionStatusCd(), "XG");
    }

    public static Irs1095XML saveAndAssert(Irs1095XMLRepository repo,
                                           long sourceUniqueId,
                                           String sourceCode,
                                           String irsTransmissionStatusCd,
                                           String rawXML,
                                           String updatedBy) {

        Irs1095XML irs1095XML = new Irs1095XML() ;

        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK(sourceUniqueId, sourceCode, new Integer(2015));

        if( repo.exists(irs1095XMLPK)) { repo.delete(irs1095XMLPK);}

        irs1095XML.setId(irs1095XMLPK);
        irs1095XML.setIrsTransmissionStatusCd(irsTransmissionStatusCd);
        irs1095XML.setIrs1095BXml(rawXML);
        irs1095XML.setUpdatedDate(new Date());
        irs1095XML.setUpdatedBy(updatedBy);
        repo.save(irs1095XML);
        assertNotNull(irs1095XML.getUpdatedDate());

        Irs1095XML irs1095XML2 = repo.findOne(irs1095XMLPK);
        assertNotNull(irs1095XML2);
        assertEquals(irs1095XML2.getIrs1095BXml(), irs1095XML.getIrs1095BXml());
        assertEquals(irs1095XML2.getUpdatedBy(), irs1095XML.getUpdatedBy());
        return irs1095XML ;

    }


}
