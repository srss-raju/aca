package us.deloitteinnovation.aca.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by bhchaganti on 8/19/2016.
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = USStateCodeValidator.class)
public @interface USStateCode {

    Class<? extends Enum<?>> acceptValues();

    String message() default "Not a valid US state code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
