package us.deloitteinnovation.aca.security;

import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sdalavi on 11/9/2015.
 */
public class UnicornSAMLCredential  implements SAMLUserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnicornSAMLCredential.class);
    private static final String FIRST_TIME_STATUS_ZERO = "0";
    private static final String ENABLE_STATUS_ZERO = "0";
    private static final String ENABLE_STATUS_ONE = "1";
    private static final String EXPIRED_STATUS_CODE = "16777216";
    private static final String EXPIRED_STATUS_VALUE = "Expired";

    public UnicornSAMLCredential() {
    }

    public Object loadUserBySAML(SAMLCredential pSAMLCredential) throws UsernameNotFoundException {
        Attribute attribute = pSAMLCredential.getAttribute("AssertionAttributeDN");
        String userId = pSAMLCredential.getNameID().getValue();
        UserSession sessionVO = this.setUserSessionObject(pSAMLCredential);
        return sessionVO;
    }

    public UserSession setUserSessionObject(SAMLCredential pSAMLCredential) {
        UserSession userSession = new UserSession();
        Iterator i$ = pSAMLCredential.getAttributes().iterator();
        while(i$.hasNext()) {
            Attribute attribute = (Attribute) i$.next();
            Iterator i$1 = attribute.getAttributeValues().iterator();
            while (i$1.hasNext()) {
                XMLObject xmlObject = (XMLObject) i$1.next();
                XSAnyImpl xsAnyImpl = (XSAnyImpl) xmlObject;
                this.mapSAMLAttrToUserSession(attribute, xsAnyImpl, userSession);
            }
        }
        return userSession;
    }

    private void mapSAMLAttrToUserSession(Attribute attribute, XSAnyImpl xsAnyImpl, UserSession userSession) {
        try {
            if (attribute.getName().equals("AssertionAttributeDN")) {
                userSession.setDn(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeFirstName")) {
                userSession.setFirstName(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeLastName")) {
                userSession.setLastName(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeCompany")) {
                userSession.setState(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeEmail")) {
                userSession.setEmail(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeFirstTimeLogin")) {
                if (xsAnyImpl.getTextContent() != null && xsAnyImpl.getTextContent().equals(FIRST_TIME_STATUS_ZERO)) {
                    userSession.setFirstTimeLogin(true);
                } else if (xsAnyImpl.getTextContent() != null && !xsAnyImpl.getTextContent().equals(FIRST_TIME_STATUS_ZERO)) {
                    userSession.setFirstTimeLogin(false);
                } else {
                    userSession.setFirstTimeLogin(false);
                }
            } else if (attribute.getName().equals("AssertionAttributeGroup")) {
                userSession.setStringRoles(this.getUserRolesList(xsAnyImpl.getTextContent()));
                userSession.setRole(this.getApplicationRole(userSession.getStringRoles()));
            } else if (attribute.getName().equals("AssertionpostOfficeBox")) {
                userSession.setApplications(xsAnyImpl.getTextContent());
            } else if (attribute.getName().equals("AssertionAttributeStatus")) {
                if (xsAnyImpl.getTextContent().equals(ENABLE_STATUS_ZERO)) {
                    userSession.setEnable(true);
                } else if (xsAnyImpl.getTextContent().equals(ENABLE_STATUS_ONE)) {
                    userSession.setEnable(false);
                } else if (xsAnyImpl.getTextContent().equals(EXPIRED_STATUS_CODE)) {
                    userSession.setExpired(true);
                }
            } else if (attribute.getName().equals("AssertionAttributePwdExpiryDate")) {
               if(xsAnyImpl.getTextContent() != null && !StringUtils.isEmpty(xsAnyImpl.getTextContent())) {
                    if(xsAnyImpl.getTextContent().equalsIgnoreCase(EXPIRED_STATUS_VALUE)) {
                        userSession.setExpiryDate(EXPIRED_STATUS_VALUE);
                    } else {
                        SimpleDateFormat inputSdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date inputExpiryDate = inputSdFormat.parse(xsAnyImpl.getTextContent());
                        SimpleDateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy");
                        userSession.setExpiryDate(sdFormat.format(inputExpiryDate));
                    }
                }
            }
        } catch(ParseException pe) {
            LOGGER.error("Parse error has occurred: ", pe);
            throw new RuntimeException(pe);
        }
    }

    private List<String> getUserRolesList(String role) {
        List<String> roles = new ArrayList<>();
        if(role != null && role.contains("^")) {
            StringTokenizer tokenizer = new StringTokenizer(role, "^");
            while(tokenizer.hasMoreElements()) {
                roles.add(tokenizer.nextToken());
            }
        } else {
            roles.add(role);
        }
        return roles;
    }

    private String getApplicationRole(List<String> roles) {
        String applicationRole = null;
        for(String roleString: roles) {
            if((roleString.contains("1095B_External") || roleString.contains("1095B_OPS"))
                    && roleString.contains(",")) {
                StringTokenizer tokenizer = new StringTokenizer(roleString, ",");
                while(tokenizer.hasMoreElements()) {
                    String[] tokens = tokenizer.nextToken().split("=");
                    if(tokens[0].equals("CN")) {
                        applicationRole = tokens[1];
                        return applicationRole;
                    }
                }
            }
        }
        return applicationRole;
    }

}
