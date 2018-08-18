package us.deloitteinnovation.aca.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

/**
 * Created by rgopalani on 9/29/2015.
 */
public class BigIntegerValidator implements ConstraintValidator<BigIntegerLength, BigInteger> {
    private int min;
    private int max;

    public void initialize(BigIntegerLength parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    public boolean isValid(BigInteger value,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        int length = value.toString().length();
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if (min < 0) {
            throw new IllegalArgumentException(
                    "The min parameter cannot be negative.");
        }
        if (max < 0) {
            throw new IllegalArgumentException(
                    "The max parameter cannot be negative.");
        }
        if (max < min) {
            throw new IllegalArgumentException("The length cannot be negative.");
        }
    }
}