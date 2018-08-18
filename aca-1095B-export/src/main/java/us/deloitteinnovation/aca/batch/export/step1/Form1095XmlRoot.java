package us.deloitteinnovation.aca.batch.export.step1;

import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import javax.xml.bind.annotation.*;

/**
 * Created by pebradley on 4/6/16.
 */
@XmlType(name = "root")
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Form1095BUpstreamDetailType.class})
public class Form1095XmlRoot {
    Form1095BUpstreamDetailType form1095BUpstreamDetailType;

    public Form1095XmlRoot() {

    }

    public Form1095XmlRoot(Form1095BUpstreamDetailType form1095BUpstreamDetailType) {
        this.form1095BUpstreamDetailType = form1095BUpstreamDetailType ;
    }


    public Form1095BUpstreamDetailType getForm1095BUpstreamDetailType() {
        return form1095BUpstreamDetailType;
    }

    public void setForm1095BUpstreamDetailType(Form1095BUpstreamDetailType form1095BUpstreamDetailType) {
        this.form1095BUpstreamDetailType = form1095BUpstreamDetailType;
    }
}
