package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import us.deloitteinnovation.aca.entity.FilerDemographicCP;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.web.citizenportal.services.ICitizenPortalService;
import us.deloitteinnovation.aca.web.citizenportal.vo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * Created by sdalavi on 3/9/2016.
 */
public class CitizenLoginControllerTest {
    private CitizenLoginController cpController;
    private ICitizenPortalService citizenPortalService;
    HttpServletRequest request;
    @Before
    public void beforeTest() {
        citizenPortalService = mock(ICitizenPortalService.class);
        request = mock(HttpServletRequest.class);
        cpController = new CitizenLoginController(citizenPortalService);
    }

    @Test
    @Ignore
    public void getAvailableStatesTest() {
        List<SourceSystemConfig> sourceConfigList = new ArrayList<>();
        SourceSystemConfig config = new SourceSystemConfig();
        config.setStateName("ARKANSAS");
        config.setStateAbbreviation("AR");
        sourceConfigList.add(config);
        Mockito.when(citizenPortalService.getSelectedStateList()).thenReturn(sourceConfigList);
        String statesString = cpController.getAvailableStates(request);
        Assert.assertNotNull(statesString);
        Assert.assertEquals("[{\"stateName\":\"ARKANSAS\",\"stateCode\":\"AR\"}]", statesString);
    }

    @Test
    @Ignore
    public void getUserAuthInJSONTest() {
      /*  UserInfo userInfo = new UserInfo();
        userInfo.setCity("Atlanta");
        userInfo.setDob("01/01/2010");
        userInfo.setUidType("SSN");
        userInfo.setUidNumber("111111");
        FilerDemographicCP filerDemographicCP = new FilerDemographicCP();
        filerDemographicCP.setIsUserFound(true);
        Mockito.when(citizenPortalService.verifyUserInformation(userInfo)).thenReturn(filerDemographicCP);
        Map<String, String> mapErros = new HashMap<>();
        BindingResult bindingResult = new MapBindingResult(mapErros, null);
        String response = cpController.getUserAuthInJSON(userInfo, bindingResult, null, request);
        Assert.assertNotNull(response);*/
    }
}
