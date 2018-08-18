package us.deloitteinnovation.aca.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class IntegerValidator implements ConstraintValidator<IntegerLength, Integer> {
	private int min;
	private int max;

	public void initialize(IntegerLength parameters) {
		min = parameters.min();
		max = parameters.max();
		validateParameters();
	}

	public boolean isValid(Integer value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		int length = String.valueOf(value).length();
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