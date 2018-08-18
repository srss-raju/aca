package us.deloitteinnovation.aca.batch.dao;

import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dto.IRSTransmittalDetailsDto;

import java.util.List;

/**
 */
@Component
public interface IRSTransmittalDetailsDao {
    int save(IRSTransmittalDetailsDto irsTransmittalDetailsDto);

    void saveAll(List<IRSTransmittalDetailsDto> list);
}
