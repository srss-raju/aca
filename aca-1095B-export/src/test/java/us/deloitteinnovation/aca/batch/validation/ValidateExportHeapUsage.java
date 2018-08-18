package us.deloitteinnovation.aca.batch.validation;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import us.deloitteinnovation.aca.batch.AbstractExportTestCase;
import us.deloitteinnovation.aca.batch.export.ExportBatchJobConfiguration;
import us.deloitteinnovation.aca.batch.util.ExportJobExecutionUtil;
import us.deloitteinnovation.aca.validator.StateCode;

/**
 * Test case to profile & improve performance of Export Job2. <br/>
 * Issue: ACAB-1601
 *
 * @author yaojia
 * @since 2.1.0
 */
@ActiveProfiles({"dev"})
public class ValidateExportHeapUsage extends AbstractExportTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateExportHeapUsage.class);

    @Autowired
    ExportJobExecutionUtil jobExecutionUtil;

    @Autowired
    Job acaGenerate1095Originals;

    @Autowired
    Job acaGenerate109495Originals;

    @Ignore
    @Test
    public void test() throws Exception {
        // Execute Job1
        LOGGER.debug(">> Starting Job 1 ...");
        jobExecutionUtil.executeJob(acaGenerate1095Originals, StateCode.AR, 2016);

        // Execute Job2
        LOGGER.debug(">> Starting Job 2 ...");
        jobExecutionUtil.executeJob(acaGenerate109495Originals, StateCode.AR, 2016);
    }
}
