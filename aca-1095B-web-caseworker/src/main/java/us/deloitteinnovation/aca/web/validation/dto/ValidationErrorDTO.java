package us.deloitteinnovation.aca.web.validation.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation Data Transfer Object
 * Handles bundling together Field Error Data Transfer Objects
 *
 * @author ltornquist
 * @since 7/1/2015
 */
public class ValidationErrorDTO {

    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public ValidationErrorDTO() {
    }

    public void addFieldError(String path, String message) {
        fieldErrors.add(new FieldErrorDTO(path, message));
    }
}
