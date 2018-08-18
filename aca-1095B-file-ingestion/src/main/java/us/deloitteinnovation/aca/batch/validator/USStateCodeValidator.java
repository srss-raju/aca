package us.deloitteinnovation.aca.batch.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhchaganti on 8/19/2016.
 */
public class USStateCodeValidator implements ConstraintValidator<USStateCode, String>{

    List<String> valueList = null;

    @Override
    public void initialize(USStateCode usStateCode) {

        valueList = new ArrayList<String>();
        Class<? extends Enum<?>> enumClass = usStateCode.acceptValues();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for(@SuppressWarnings("rawtypes") Enum enumVal: enumValArr){

            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        
        boolean isValid = true;
        if(!valueList.contains(value.toUpperCase())){
            isValid = false;
        }
        return isValid;
    }
}
