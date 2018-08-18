package us.deloitteinnovation.aca.batch.export.steppdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.deloitteinnovation.aca.model.Filer;

/**
 * 
 * @author rbongurala
 *
 */
public class GeneratePdf {

	private static final Logger LOG = LoggerFactory.getLogger(GeneratePdf.class);

	public static void exportPdf(String targetFileLocation, Filer filer, String year) {

		StringBuilder targetFileName = new StringBuilder();
		targetFileName.append(filer.getTaxYear()).append("_").append(filer.getSourceUniqueId()).append(".pdf");

		try {
		    CreatePDF.create(filer, targetFileLocation+targetFileName, year);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		LOG.debug("Pdf Generated");
	}

}
