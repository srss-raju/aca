package us.deloitteinnovation.aca.exception;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhchaganti on 8/19/2016.
 */
public class PrintVendorUSStateCodeValidator implements ConstraintValidator<PrintVendorUSStateCode, String>{

    List<String> valueList = null;

    @Override
    public void initialize(PrintVendorUSStateCode usStateCode) {

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
