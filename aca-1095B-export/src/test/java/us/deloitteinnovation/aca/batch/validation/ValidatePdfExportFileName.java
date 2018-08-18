package us.deloitteinnovation.aca.batch.validation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import us.deloitteinnovation.aca.batch.AbstractExportTestCase;
import us.deloitteinnovation.aca.batch.export.steppdf.PdfUtil;
import us.deloitteinnovation.aca.batch.util.ExportJobExecutionUtil;
import us.deloitteinnovation.aca.validator.StateCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple unit test to make sure Pdfexport writes to BATCH_INFO with MMDDYYYY format
 *
 * @author yaojia
 * @since 2.1.0
 */

@ActiveProfiles({"dev"})
public class ValidatePdfExportFileName extends AbstractExportTestCase {

    @Test
    public void test() throws Exception {
        String in = "2016-12-25";
        Assert.assertEquals("12252016", PdfUtil.returnDate(in));
    }
}
