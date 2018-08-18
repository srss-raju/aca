package us.deloitteinnovation.aca.batch.export.steppdf;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class ExportPdfMapper   implements RowMapper<ExportPdfDto> {

	 @Autowired
	 ExportPdfFilerMapper exportPdfFilerMapper;

	@Override
	public ExportPdfDto mapRow(ResultSet rs, int i) throws SQLException {
		ExportPdfDto dto = new ExportPdfDto() ;
		dto.filer = exportPdfFilerMapper.mapRow(rs, i) ;
		return dto;
	}
}
