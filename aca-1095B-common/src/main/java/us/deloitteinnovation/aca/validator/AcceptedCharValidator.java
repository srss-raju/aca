package us.deloitteinnovation.aca.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaojia on 11/18/2016.
 */
public class AcceptedCharValidator implements
        ConstraintValidator<AcceptedChar, Character> {

    private List<Character> valueList;

    @Override
    public void initialize(AcceptedChar constraintAnnotation) {
        valueList = new ArrayList<>();
        for (char val : constraintAnnotation.acceptValues()) {
            valueList.add(val);
        }
    }

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext constraintValidatorContext) {
        if (valueList!=null && character!=null && !valueList.contains(character)) {
            return false;
        }
        return true;
    }
}
