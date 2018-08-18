package us.deloitteinnovation.aca.batch.ingest.step4;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.batch.FileIngestionConfiguration;
import us.deloitteinnovation.aca.batch.FileIngestionTestConfiguration;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.entity.BatchInfo;
import us.deloitteinnovation.aca.repository.BatchInfoRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by bhchaganti on 11/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = {CommonConfiguration.class, FileIngestionConfiguration.class})

public class Step4UpdateBatchCountsWriterTest {

    BatchInfoDto batchInfoDto;
    @Autowired
    BatchInfoRepository batchInfoRepository;
    @Autowired
    BatchInfoService batchInfoService;
    BatchCountDto batchCountDto;
    List<BatchCountDto> batchCountDtoList;

    Step4UpdateBatchCountsWriter step4UpdateBatchCountsWriter;

    @Before
    public void setUp() throws Exception {

        step4UpdateBatchCountsWriter = new Step4UpdateBatchCountsWriter();

        batchInfoDto = new BatchInfoDto();
        batchInfoDto.setReceiveDt(new Date());
        batchInfoDto.setTotalCount(15);
        int batchId = batchInfoService.save(batchInfoDto);
        batchInfoDto.setBatchId(batchId);

        batchCountDto = new BatchCountDto();
        batchCountDto.setCountInBusinessDecisionLog(10);
        batchCountDto.setCountInExceptionReport(5);

        batchCountDtoList = new ArrayList<>();
        batchCountDtoList.add(batchCountDto);

        step4UpdateBatchCountsWriter.batchInfoDto = this.batchInfoDto;
        step4UpdateBatchCountsWriter.batchInfoService = this.batchInfoService;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    @Ignore
    public void write() throws Exception {
        step4UpdateBatchCountsWriter.write(batchCountDtoList);
        BatchInfo batchInfo = batchInfoRepository.findOne(this.batchInfoDto.getBatchId());
        assertThat("Total pass count doesn't match", batchInfo.getTotalPass(), is(10));
        assertThat("Total fail count doesn't match", batchInfo.getTotalFail(), is(5));
        assertThat("Total received count doesn't match", batchInfo.getTotalCount(), is(15));
        // Delete this entity since it's only for tests.
        batchInfoRepository.delete(batchInfo);
        BatchInfo batchInfo1 = batchInfoRepository.findOne(this.batchInfoDto.getBatchId());
        assertThat("Test entity could not be deleted", batchInfo1, nullValue());// Confirm that it got deleted!!
    }

}