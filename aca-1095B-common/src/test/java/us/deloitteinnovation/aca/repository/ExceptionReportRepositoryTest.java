package us.deloitteinnovation.aca.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by bhchaganti on 11/13/2016.
 */
public class ExceptionReportRepositoryTest extends AbstractCommonTestConfig {
    @Autowired
    BatchInfoRepository batchInfoRepository;
    @Autowired
    ExceptionReportRepository exceptionReportRepository;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDistinctSourceUniqueIdCount() throws Exception {

        List<Integer> batchIdList = batchInfoRepository.retrieveBatchIdsForFileIngestion();
        if (!batchIdList.isEmpty()) {
            Integer topBatchId = batchIdList.get(0);
            Integer count = exceptionReportRepository.getDistinctRowNumberCount(topBatchId);
            assertThat("Getting Count from exception report failed", count, notNullValue());
        }
    }
}