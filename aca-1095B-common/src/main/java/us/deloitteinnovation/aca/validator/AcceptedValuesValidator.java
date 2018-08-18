package us.deloitteinnovation.aca.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class AcceptedValuesValidator implements
		ConstraintValidator<AcceptedValues, String> {

	private List<String> valueList;

	
	public void initialize(AcceptedValues constraintAnnotation) {
		valueList = new ArrayList<String>();
		for (String val : constraintAnnotation.acceptValues()) {
			valueList.add(val.toLowerCase());
		}
	}

	
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (valueList!=null&&value!=null&&!valueList.contains(value.toLowerCase())) {
			return false;
		}
		return true;
	}


	
}
