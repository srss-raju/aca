package us.deloitteinnovation.aca.web.validation.dto;

/**
 * Field Error Data Transfer Object
 * Holds relevant field error information
 *
 * @author ltornquist
 * @since 7/1/2015
 */
public class FieldErrorDTO {
    private String field;

    private String message;

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
