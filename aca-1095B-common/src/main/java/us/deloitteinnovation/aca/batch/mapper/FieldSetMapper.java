package us.deloitteinnovation.aca.batch.mapper;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by rgopalani on 9/26/2015.
 */
public interface FieldSetMapper<T> {

    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet the {@link FieldSet} to map
     * @throws BindException if there is a problem with the binding
     */
    T mapFieldSet(FieldSet fieldSet, int line) throws BindException;
}
