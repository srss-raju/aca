package us.deloitteinnovation.aca.web.opsportal.data;

import us.deloitteinnovation.aca.web.opsportal.dto.IrsTransmittalDetailsDto;
import us.deloitteinnovation.aca.web.opsportal.dto.StateDto;
import us.deloitteinnovation.aca.web.opsportal.dto.StatusDto;
import us.deloitteinnovation.aca.web.opsportal.dto.TaxYearDto;

import java.util.List;

/**
 * Created by sdalavi on 3/28/2016.
 */
public interface IOpsPortalDataService {
    public List<StateDto> getTransmissionStates();
    public List<TaxYearDto> getTransmissionYears();
    public List<StatusDto> getTransmissionStatuses(String typeCd);
    public List<IrsTransmittalDetailsDto> getTransmissionRecords(String stateCd, int taxYear);
    public int saveTransmissionRecord(IrsTransmittalDetailsDto irsTransmittalDetailsDto);
    public int rejectResendCorrection(Long transmissionId, String recordStatus, String userName, int taxYear);
}
