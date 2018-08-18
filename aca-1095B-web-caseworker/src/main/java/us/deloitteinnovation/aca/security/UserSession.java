package us.deloitteinnovation.aca.security;

import java.util.List;

/**
 * Created by sdalavi on 12/8/2015.
 */
public class UserSession {
    private String dn;
    private List<String> stringRoles;
    private String firstName;
    private String lastName;
    private String state;
    private String email;
    private boolean firstTimeLogin;
    private String applications;
    private String role;
    private boolean enable;
    private boolean expired;
    private String expiryDate;
    // constants for toString method
    private final String USER_SESSION = "UserSession";
    private final String DN = "dn";
    private final String STRING_ROLES = "stringRoles";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String STATE = "state";
    private final String EMAIL = "email";
    private final String FIRST_TIME_LOGIN = "firstTimeLogin";
    private final String APPLICATIONS = "applications";
    private final String ROLE = "role";
    private final String ENABLE = "enable";
    private final String EXPIRED = "expired";
    private final String EXPIRY_DATE = "expiryDate";
    private final String EQUAL = "=";
    private final String COMMA = ",";
    private final String CURLYOPEN = "{";
    private final String CURLYCLOSE = "}";

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public List<String> getStringRoles() {
        return stringRoles;
    }

    public void setStringRoles(List<String> stringRoles) {
        this.stringRoles = stringRoles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }

    public String getApplications() {
        return applications;
    }

    public void setApplications(String applications) {
        this.applications = applications;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(USER_SESSION);
        builder.append(CURLYOPEN);
        builder.append(DN);
        builder.append(EQUAL);
        builder.append(dn);
        builder.append(COMMA);
        builder.append(STRING_ROLES);
        builder.append(EQUAL);
        builder.append(stringRoles);
        builder.append(COMMA);
        builder.append(FIRST_NAME);
        builder.append(EQUAL);
        builder.append(firstName);
        builder.append(COMMA);
        builder.append(LAST_NAME);
        builder.append(EQUAL);
        builder.append(lastName);
        builder.append(COMMA);
        builder.append(STATE);
        builder.append(EQUAL);
        builder.append(state);
        builder.append(COMMA);
        builder.append(EMAIL);
        builder.append(EQUAL);
        builder.append(email);
        builder.append(COMMA);
        builder.append(FIRST_TIME_LOGIN);
        builder.append(EQUAL);
        builder.append(firstTimeLogin);
        builder.append(COMMA);
        builder.append(APPLICATIONS);
        builder.append(EQUAL);
        builder.append(applications);
        builder.append(COMMA);
        builder.append(ROLE);
        builder.append(EQUAL);
        builder.append(role);
        builder.append(COMMA);
        builder.append(ENABLE);
        builder.append(EQUAL);
        builder.append(enable);
        builder.append(COMMA);
        builder.append(EXPIRED);
        builder.append(EQUAL);
        builder.append(expired);
        builder.append(COMMA);
        builder.append(EXPIRY_DATE);
        builder.append(EQUAL);
        builder.append(expiryDate);
        builder.append(CURLYCLOSE);
        return builder.toString();
    }
}
