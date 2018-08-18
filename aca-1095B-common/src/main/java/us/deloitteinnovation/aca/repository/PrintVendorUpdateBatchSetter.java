package us.deloitteinnovation.aca.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import us.deloitteinnovation.aca.entity.FilerDemographic;

/**
 * 
 * @author RajeshKumar B
 *
 */
public class PrintVendorUpdateBatchSetter implements BatchPreparedStatementSetter {
	
	final List<FilerDemographic> filerDemographicList;

	public PrintVendorUpdateBatchSetter(List<FilerDemographic> filerDemographicList) {
		this.filerDemographicList = filerDemographicList;
	}

	public void setValues(PreparedStatement ps, int i) throws SQLException {
        final FilerDemographic filerDemographic = filerDemographicList.get(i);
        ps.setString(1, filerDemographic.getFormStatus());
        ps.setTimestamp(2, createSQLDate());
        ps.setLong(3, filerDemographic.getId().getSourceUniqueId());
        ps.setString(4, filerDemographic.getId().getSourceCd());
        ps.setInt(5, filerDemographic.getId().getTaxYear());
    }

	public int getBatchSize() {
		return filerDemographicList.size();
	}
	
	private Timestamp createSQLDate() {
        final Calendar cal = Calendar.getInstance();
        final java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }
}
