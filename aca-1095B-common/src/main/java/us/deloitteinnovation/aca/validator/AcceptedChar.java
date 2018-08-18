package us.deloitteinnovation.aca.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by yaojia on 11/18/2016.
 */

@Documented
@Constraint(validatedBy = AcceptedCharValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptedChar {

    char[] acceptValues();

    String message() default "Not allowded value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
