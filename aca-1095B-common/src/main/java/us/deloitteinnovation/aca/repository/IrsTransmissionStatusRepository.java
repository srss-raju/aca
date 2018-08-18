package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsTransmissionStatus;
import us.deloitteinnovation.aca.entity.IrsTransmissionStatusPK;

import java.util.List;

/**
 * Created by bhchaganti on 4/4/2016.
 */
@Transactional
public interface IrsTransmissionStatusRepository extends CrudRepository<IrsTransmissionStatus, IrsTransmissionStatusPK> {


    @Transactional(readOnly = true)
    @Query("select t from us.deloitteinnovation.aca.entity.IrsTransmissionStatus t where t.id.typeCD like :typeCd ")
    List<IrsTransmissionStatus> getTransmissionStatus(@Param("typeCd") String typeCd);

}
