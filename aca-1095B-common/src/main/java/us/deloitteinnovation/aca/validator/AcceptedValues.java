package us.deloitteinnovation.aca.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AcceptedValuesValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptedValues {

	String[] acceptValues();

	String message() default "Not allowded value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
