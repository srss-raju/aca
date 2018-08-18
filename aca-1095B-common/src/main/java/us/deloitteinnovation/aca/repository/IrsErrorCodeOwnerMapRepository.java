package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsErrorCodeOwnerMapEntity;

import java.util.List;

/**
 * Created by bhchaganti on 4/4/2016.
 */
@Transactional
public interface IrsErrorCodeOwnerMapRepository extends CrudRepository<IrsErrorCodeOwnerMapEntity,String> {

    @Transactional(readOnly = true)
    @Query("select t from us.deloitteinnovation.aca.entity.IrsErrorCodeOwnerMapEntity t ")
    List<IrsErrorCodeOwnerMapEntity> getTransmissionStatus();
}
