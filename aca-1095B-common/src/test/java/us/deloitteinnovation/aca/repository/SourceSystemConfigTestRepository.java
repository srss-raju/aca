package us.deloitteinnovation.aca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;

/**
 */
@Transactional
public interface SourceSystemConfigTestRepository  extends CrudRepository<SourceSystemConfig, String> {

}
