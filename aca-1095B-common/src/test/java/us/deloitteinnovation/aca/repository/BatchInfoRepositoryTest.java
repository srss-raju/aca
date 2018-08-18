package us.deloitteinnovation.aca.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by bhchaganti on 11/13/2016.
 */
public class BatchInfoRepositoryTest extends AbstractCommonTestConfig {
    @Autowired
    BatchInfoRepository batchInfoRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getTopBatchIdForFileIngestion() throws Exception {
        List<Integer> batchInfoList = batchInfoRepository.retrieveBatchIdsForFileIngestion();
        assertThat("Batch Info retrieval failed", batchInfoList, notNullValue());
        if(batchInfoList.size() != 0){
            assertThat("Wrong batch id",batchInfoList.get(0), is(not(0)));
        }
    }

}