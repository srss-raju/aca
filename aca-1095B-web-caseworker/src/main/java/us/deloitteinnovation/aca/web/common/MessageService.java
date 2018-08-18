package us.deloitteinnovation.aca.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.model.SourceCoverMessage;
import us.deloitteinnovation.aca.model.SourceSystemConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rgopalani on 12/4/2015.
 */
@Service("messageService")
public class MessageService {
    Map<String, SourceSystemConfig> sourceSystemConfig = new HashMap<String, SourceSystemConfig>();
    Map<String, SourceCoverMessage> sourceCoverMessage = new HashMap<String, SourceCoverMessage>();

    @Autowired
    private SourceSystemConfigDataService sourceSystemConfigDataService;

    public SourceSystemConfig getSourceSystemConfig(String sourceCD) {
        if (!sourceSystemConfig.containsKey(sourceCD)) {
            sourceSystemConfig.put(sourceCD, sourceSystemConfigDataService.getSourceSystemConfig(sourceCD));
        }
        return sourceSystemConfig.get(sourceCD);
    }


}
