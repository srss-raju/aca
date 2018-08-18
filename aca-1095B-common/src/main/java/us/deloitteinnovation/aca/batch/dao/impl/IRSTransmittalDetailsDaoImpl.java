package us.deloitteinnovation.aca.batch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dao.IRSTransmittalDetailsDao;
import us.deloitteinnovation.aca.batch.dto.IRSTransmittalDetailsDto;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Transactional
public class IRSTransmittalDetailsDaoImpl extends NamedParameterJdbcDaoSupport implements IRSTransmittalDetailsDao{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public int save(IRSTransmittalDetailsDto irsTransmittalDetailsDto){
        Map<String, Object> params =createParams(irsTransmittalDetailsDto) ;
        this.namedParameterJdbcTemplate.update(BatchQueryConstants.INSERT_IRS_TRANSMIT_DETAILS, params);
        return this.namedParameterJdbcTemplate.queryForObject("select MAX(BATCH_ID) from BATCH_INFO", params, Integer.class);
    }

    @Transactional
    public void saveAll(List<IRSTransmittalDetailsDto> list) {
        Map<String, Object> params;

        for (IRSTransmittalDetailsDto dto : list) {
            params = createParams(dto) ;
            this.namedParameterJdbcTemplate.update(BatchQueryConstants.INSERT_IRS_TRANSMIT_DETAILS, params);
        }
    }

    protected Map<String, Object> createParams(IRSTransmittalDetailsDto irsTransmittalDetailsDto) {
        Map<String, Object> params = new HashMap<>();
        params.put(CommonEntityConstants.BATCH_ID,irsTransmittalDetailsDto.getBatchId());
        params.put(BatchExportConstants.EX_SOURCE_UNIQUE_ID_NUMBER,irsTransmittalDetailsDto.getSourceUniqueId());
        params.put(BatchExportConstants.PARAM_SOURCE_CD,irsTransmittalDetailsDto.getSourceCd());
        params.put(BatchExportConstants.PARAM_TRANSMIT_STATUS,irsTransmittalDetailsDto.getTransmitStatus());
        params.put(BatchExportConstants.PARAM_TRANSMIT_FILE_NAME,irsTransmittalDetailsDto.getTransmitFileName());
        params.put(BatchExportConstants.PARAM_UPDATED_BY,irsTransmittalDetailsDto.getUpdatedBy());
        params.put(BatchExportConstants.PARAM_UPDATED_DATE,irsTransmittalDetailsDto.getUpdatedDate());
        return params ;
    }

}
