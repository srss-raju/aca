package us.deloitteinnovation.aca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsTransmissionErrors;

/**
 */
@Transactional
public interface IrsTransmissionErrorsRepository extends CrudRepository<IrsTransmissionErrors, Integer> {
}
