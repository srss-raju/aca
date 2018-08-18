package us.deloitteinnovation.aca.repository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.Irs109495XMLDetailsEntity;


import java.util.Date;
import java.util.List;

import org.hamcrest.number.OrderingComparison.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;


/**
 * Created by bhchaganti on 1/19/2017.
 */
public class Irs109495XMLDetailsRepositoryTest extends AbstractCommonTestConfig {

    @Autowired
    Irs109495XMLDetailsRepository   irs109495XMLDetailsRepository;

    @Before
    public void clearAll(){
        irs109495XMLDetailsRepository.deleteAll();
    }

    @Test@Ignore("Enable when required to test. Disabling this to avoid Jenkins running this test and updating DB.")
    public void saveMinimum() throws Exception {

        String updatedBy = "saveMinimumTest";

        Irs109495XMLDetailsEntity entityToBeSaved = new Irs109495XMLDetailsEntity();
        entityToBeSaved.setTransmissionId(new Integer(1));
        entityToBeSaved.setForm1094BCount(1);
        entityToBeSaved.setForm1095BCount(5);
        entityToBeSaved.setXmlFilePath("\\opt\\data\\file\\file.xml");
        entityToBeSaved.setManifestCreated(false);
        entityToBeSaved.setUpdatedDate(new Date());
        entityToBeSaved.setUpdatedBy(updatedBy);
        entityToBeSaved.setTransmissionTypeCd("O");

        Irs109495XMLDetailsEntity entitySaved = irs109495XMLDetailsRepository.save(entityToBeSaved);

        assertThat("Entity could not be saved", entityToBeSaved, is(entitySaved));

        irs109495XMLDetailsRepository.updateManifestFlag("\\opt\\data\\file\\file.xml", updatedBy);

        List<Irs109495XMLDetailsEntity> entitySearched = irs109495XMLDetailsRepository.findByManifestCreated(Boolean.TRUE);
       assertThat("No results returned with updated manifest flag", entitySearched.size(), is(greaterThan(0)));

        for(Irs109495XMLDetailsEntity entity : entitySearched) {
            assertThat("Not the right entity",entity.getTransmissionId(), is(1));
            assertThat("Not the right entity",entity.getForm1094BCount(), is(1));
            assertThat("Not the right entity",entity.getForm1095BCount(), is(5));
            assertThat("Not the right entity",entity.getXmlFilePath(), is("\\opt\\data\\file\\file.xml"));
            assertThat("Not the right entity",entity.getManifestCreated(), is(Boolean.TRUE));
        }
    }
}
