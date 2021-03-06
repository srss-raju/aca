//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.ErrorMessageDetail;
import us.gov.treasury.irs.common.impl.ErrorMessageDetailImpl;
import us.gov.treasury.irs.ext.aca.air._7.NameControlValResultDetail;
import us.gov.treasury.irs.ext.aca.air._7.TINValidateNameRequestDetail;
import us.gov.treasury.irs.ext.aca.air._7.TINValidateNameResponseDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TINValidateNameResponseDetailType", propOrder = {
    "tinValidateNameRequestDetail",
    "nameControlValResultDetails",
    "errorMessageDetail"
})
public class TINValidateNameResponseDetailTypeImpl
    implements TINValidateNameResponseDetailType
{

    @XmlElement(name = "TINValidateNameRequestDetail", type = TINValidateNameRequestDetailImpl.class)
    protected TINValidateNameRequestDetailImpl tinValidateNameRequestDetail;
    @XmlElement(name = "NameControlValResultDetail", type = NameControlValResultDetailImpl.class)
    protected List<NameControlValResultDetail> nameControlValResultDetails;
    @XmlElement(name = "ErrorMessageDetail", namespace = "urn:us:gov:treasury:irs:common", type = ErrorMessageDetailImpl.class)
    protected ErrorMessageDetailImpl errorMessageDetail;

    public TINValidateNameRequestDetail getTINValidateNameRequestDetail() {
        return tinValidateNameRequestDetail;
    }

    public void setTINValidateNameRequestDetail(TINValidateNameRequestDetail value) {
        this.tinValidateNameRequestDetail = ((TINValidateNameRequestDetailImpl) value);
    }

    public List<NameControlValResultDetail> getNameControlValResultDetails() {
        if (nameControlValResultDetails == null) {
            nameControlValResultDetails = new ArrayList<NameControlValResultDetail>();
        }
        return this.nameControlValResultDetails;
    }

    public ErrorMessageDetail getErrorMessageDetail() {
        return errorMessageDetail;
    }

    public void setErrorMessageDetail(ErrorMessageDetail value) {
        this.errorMessageDetail = ((ErrorMessageDetailImpl) value);
    }

}
