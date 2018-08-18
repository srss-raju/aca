package us.deloitteinnovation.aca.web.caseworkerportal.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author ritmukherjee
 * @since 11/04/2015
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    void delete(T deleted);

    List<T> findAll();

    java.util.Optional<T> findOne(ID id);

    T save(T persisted);
}