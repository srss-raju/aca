package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto;

import java.io.Serializable;

/**
 *
 * this class will be response of the user authentication request
 * Created by tthakore on 9/28/2015.
 */
public class ViewStateList implements Serializable {




    private static final long serialVersionUID = 752647115562271002L;



    private String generatedDate = "";//date
    private String address1 = "";// city
    private String address2 = "";// city
    private String city = "";
    private String state  =  "";
    private String zipcode = "";


    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }







}
