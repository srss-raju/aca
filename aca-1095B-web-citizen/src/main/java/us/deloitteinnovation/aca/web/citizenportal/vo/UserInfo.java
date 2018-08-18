package us.deloitteinnovation.aca.web.citizenportal.vo;

import org.hibernate.validator.constraints.NotEmpty;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tthakore on 9/28/2015.
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 752647115562271000L;
    private String userFname = "";

    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "^.{1,30}$", message = ErrorMessageConstants.WRONG_INFO)
    private String userLname = "last name" ;

    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    private String dob = "oct-27";

    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "([1-57-8][0-9]{2}|0([1-9][0-9]|[0-9][1-9])|6([0-57-9][0-9]|[0-9][0-57-9]))([1-9][0-9]|[0-9][1-9])([1-9]\\d{3}|\\d[1-9]\\d{2}|\\d{2}[1-9]\\d|\\d{3}[1-9])", message = ErrorMessageConstants.WRONG_INFO)
    private String uidNumber = "1234567";

    private String uidType ="";//SSN or TIN

    private String streetAddress1 = "";

    private String streetAddress2 = "";
    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "^.{1,30}$", message = ErrorMessageConstants.WRONG_INFO)
    private String city = "";
    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "^.{1,30}$", message = ErrorMessageConstants.WRONG_INFO)
    private String state = "";

    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "[0-9]{5,5}", message = ErrorMessageConstants.WRONG_INFO)
    private String zipCode = "";


    @NotNull(message = ErrorMessageConstants.WRONG_INFO)
    @NotEmpty(message = ErrorMessageConstants.WRONG_INFO)
    @Pattern(regexp = "[a-zA-Z]{2,2}", message = ErrorMessageConstants.WRONG_INFO)
    private String userSelectedState = "";



    private String userSelectedTaxYear = "";


    private String gRecaptchaResponse;




    public void setUserFname(String userFname) {
        this.userFname =  (userFname != null)?userFname:"";
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 =  (streetAddress2 != null)?streetAddress2:"";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserFname() {
        return userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public String getDob() {
        return dob;
    }


    public Date getDOBDateFormat()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {

             date = formatter.parse(this.dob);
        }
        catch (ParseException e) {

        }
        return date;
    }


    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipCode;
    }

    public String getUserSelectedState() {
        return userSelectedState;
    }

    public void setUserSelectedState(String userSelectedState) {
        this.userSelectedState = userSelectedState;
    }
    public String getUidType() {
        return uidType;
    }

    public void setUidType(String uidType) {
        this.uidType = uidType;
    }
    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }
    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 =  (streetAddress1 != null)?streetAddress1:"";
    }
    public String getUserSelectedTaxYear() {
        return userSelectedTaxYear;
    }

    public void setUserSelectedTaxYear(String userSelectedTaxYear) {
        this.userSelectedTaxYear = userSelectedTaxYear;
    }

    public String getgRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setgRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }




}
