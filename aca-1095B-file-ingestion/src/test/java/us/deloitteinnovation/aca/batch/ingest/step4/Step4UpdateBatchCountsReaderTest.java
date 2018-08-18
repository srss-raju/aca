package us.deloitteinnovation.aca.batch.ingest.step4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.FileIngestionService;
import us.deloitteinnovation.aca.repository.ExceptionReportRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bhchaganti on 11/11/2016.
 */
public class Step4UpdateBatchCountsReaderTest {

    Integer testBatchId;
    BatchInfoDto batchInfoDto;
    FileIngestionService fileIngestionService;
    ExceptionReportRepository exceptionReportRepository;
    Step4UpdateBatchCountsReader step4UpdateBatchCountsReader;

    @Before
    public void setUp() throws Exception {
        testBatchId = 2483;
        batchInfoDto = new BatchInfoDto();
        batchInfoDto.setBatchId(testBatchId);
        step4UpdateBatchCountsReader = new Step4UpdateBatchCountsReader();
        step4UpdateBatchCountsReader.batchInfoDto = this.batchInfoDto;
        exceptionReportRepository = mock(ExceptionReportRepository.class);
        fileIngestionService = mock(FileIngestionService.class);
        step4UpdateBatchCountsReader.exceptionReportRepository = this.exceptionReportRepository;
        step4UpdateBatchCountsReader.fileIngestionService = this.fileIngestionService;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void read() throws Exception {
        BatchCountDto batchCountDto;
        when(step4UpdateBatchCountsReader.exceptionReportRepository.getDistinctRowNumberCount(testBatchId)).thenReturn(10);
        when(step4UpdateBatchCountsReader.fileIngestionService.getDistinctSourceUniqueIdsForBatch(testBatchId)).thenReturn(10);
        batchCountDto = step4UpdateBatchCountsReader.read();
        assertThat("Count mismatch", batchCountDto.getCountInBusinessDecisionLog(), is(10));
        assertThat("Count  mismatch", batchCountDto.getCountInExceptionReport(), is(10));
    }

}
