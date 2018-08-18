package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.Irs109495XMLDetailsEntity;

import java.io.File;
import java.util.List;

/**
 * Created by bhchaganti on 1/19/2017.
 */
public interface Irs109495XMLDetailsRepository extends CrudRepository<Irs109495XMLDetailsEntity,Integer> {

    List<Irs109495XMLDetailsEntity> findByManifestCreated(Boolean manifestCreated);


    /**
     * <p>
     *     Updates the MANIFEST_CREATED flag to 1 (TRUE) for a given file path
     * </p>
     * @param absolutePath The absolute path of the file.
     * @param updatedBy The source of the updation.
     *
     * @return void
     */
    @Transactional
    @Modifying
    @Query("UPDATE " +
            "Irs109495XMLDetailsEntity e " +
            "SET " +
            "e.manifestCreated = TRUE, e.updatedBy = :updatedBy " +
            "WHERE " +
            "e.xmlFilePath = :absolutePath")

    void updateManifestFlag(@Param("absolutePath")String absolutePath, @Param("updatedBy")String updatedBy);
}
