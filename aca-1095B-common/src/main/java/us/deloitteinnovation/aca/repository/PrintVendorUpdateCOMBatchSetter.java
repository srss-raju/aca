package us.deloitteinnovation.aca.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import us.deloitteinnovation.aca.entity.PrintDetail;

/**
 * 
 * @author RajeshKumar B
 *
 */
public class PrintVendorUpdateCOMBatchSetter implements BatchPreparedStatementSetter {
	
	final List<PrintDetail> printDetailList;

	public PrintVendorUpdateCOMBatchSetter(List<PrintDetail> printDetailList) {
		this.printDetailList = printDetailList;
	}

	public void setValues(PreparedStatement ps, int i) throws SQLException {
        final PrintDetail printDetail = printDetailList.get(i);
        ps.setString(1, printDetail.getPrintStatus());
        ps.setTimestamp(2, createSQLDate());
        ps.setLong(3, printDetail.getId().getSourceUniqueId());
        ps.setInt(4, Integer.parseInt(printDetail.getId().getTaxYear()));
        ps.setString(5, printDetail.getId().getSourceCd());
        ps.setString(6, printDetail.getId().getPrintFileName());
        ps.setString(7, "MAIL_PENDING");
        
    }

	public int getBatchSize() {
		return printDetailList.size();
	}
	
	private Timestamp createSQLDate() {
        final Calendar cal = Calendar.getInstance();
        final java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }
}
