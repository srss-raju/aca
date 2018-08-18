package us.deloitteinnovation.aca.batch.invalidaddress.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ExecutionContext;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;

public class InvalidAddressUtil {
    
    private InvalidAddressUtil(){
        
    }
    
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        return sdf.format(date);

    }
    
    public static String getState(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.JobPropertiesKeys.STATE) ;
    }
    
    public static String getAgencyCode(String state){
        String agencyCode = null;
        if("AR".equals(state)){
            agencyCode = "DHS";
        }else if("IN".equals(state)){
            agencyCode = "FSS";
        }else if("LA".equals(state)){
            agencyCode = "DHH";
        }
        return agencyCode;
    }
    
    public static String getSystemCode(String state){
        String systemCode = null;
        if("AR".equals(state)){
            systemCode = "DSS";
        }else if("IN".equals(state)){
            systemCode = "ICE";
        }else if("LA".equals(state)){
            systemCode = "EES";
        }
        return systemCode;
    }

}
