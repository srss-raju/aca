package us.deloitteinnovation.aca;


import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.mapper.ApplicationMapper;
import us.deloitteinnovation.aca.batch.mapper.DefaultLineMapper;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

/**
 * Defines flat file readers for reading in Filer objects for testing.
 */
@Configuration
public class FlatFileImportTestConfiguration {

    @Bean
    @Qualifier("applicationReader")
    public FlatFileItemReader<FilerDemographicDto> applicationReader() {
        FlatFileItemReader<FilerDemographicDto> reader = new FlatFileItemReader<FilerDemographicDto>();
        reader.setLineMapper(lineMapper());
        reader.setLinesToSkip(1);
        reader.setStrict(true);
        return reader;
    }

    /**
     * @return
     */
    @Bean
    public LineMapper<FilerDemographicDto> lineMapper() {
        return new DefaultLineMapper<>();
    }

    @Bean
    public DelimitedLineTokenizer delimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{BatchConstants.RECIPIENT_CASE_APPLICATION_ID, BatchConstants.RECIPIENT_UNIQUE_ID, BatchConstants.TAX_YEAR, CommonEntityConstants.CORRECTION, BatchConstants.RECIPIENT_FIRST_NAME, BatchConstants.RECIPIENT_MIDDLE_NAME, BatchConstants.RECIPIENT_LAST_NAME, BatchConstants.RECIPIENT_NAME_SUFFIX, CommonDataConstants.RECIPIENT_SSN, CommonDataConstants.RECIPIENT_TIN, CommonDataConstants.RECIPIENT_DOB, BatchConstants.RECIPIENT_LANGUAGE_PREFERENCE, BatchConstants.EMAIL, BatchConstants.RECIPIENT_ADDRESS_LINE_1, BatchConstants.RECIPIENT_ADDRESS_LINE_2, BatchConstants.RECIPIENT_CITY, BatchConstants.RECIPIENT_STATE_CODE, BatchConstants.RECIPIENT_ZIP_5, BatchConstants.RECIPIENT_ZIP_4, BatchConstants.POLICY_ORIGIN, BatchConstants.POLICY_PROGRAM_NAME, BatchConstants.POLICY_COVERAGE_BEGIN_DT, BatchConstants.POLICY_COVERAGE_END_DT, BatchConstants.POLICY_SHOP_IDENTIFIER, BatchConstants.EMPLOYER_NAME, BatchConstants.EMPLOYER_IDENTIFICATION_NUMBER, BatchConstants.EMPLOYER_CONTACT_NO, BatchConstants.EMPLOYER_ADDRESS_LINE_1, BatchConstants.EMPLOYER_ADDRESS_LINE_2, BatchConstants.EMPLOYER_CITY_OR_TOWN, BatchConstants.EMPLOYER_STATE_OR_PROVINCE, BatchConstants.EMPLOYER_COUNTRY, BatchConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE, BatchConstants.PROVIDER_NAME, BatchConstants.PROVIDER_IDENTIFICATION_NUMBER, BatchConstants.PROVIDER_CONTACT_NUMBER, BatchConstants.PROVIDER_ADDRESS_LINE_1, BatchConstants.PROVIDER_ADDRESS_LINE_2, BatchConstants.PROVIDER_CITY_OR_TOWN, BatchConstants.PROVIDER_STATE_OR_PROVINCE, BatchConstants.PROVIDER_COUNTRY, BatchConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE, BatchConstants.FILER_STATUS, BatchConstants.RESPONSIBLE_PERSON_UNIQUE_ID, BatchConstants.COMM_PREFERENCE, BatchConstants.MAILED_FORM});
        return lineTokenizer;
    }

    @Bean
    public ApplicationMapper applicationMapper() {
        return new ApplicationMapper() ;
    }
}
