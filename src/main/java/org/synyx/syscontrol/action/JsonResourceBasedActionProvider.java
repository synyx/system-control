package org.synyx.syscontrol.action;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.synyx.syscontrol.action.view.ActionConfiguration;
import org.synyx.syscontrol.execution.ExecutionResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class JsonResourceBasedActionProvider implements ActionProvider {
    
    private final Resource resource;
    
    @Autowired
    public JsonResourceBasedActionProvider(@Value("${actions.jsonfile}") Resource resource) {
        this.resource = resource;
    }
    
   
    @Override
    public List<Action> getAllActions() {

        List<ActionConfiguration> actionConfigurations = readActions();

        return actionConfigurations.stream()
                .map(this::toAction)
                .collect(Collectors.toList());

    }
    

    private List<ActionConfiguration> readActions() {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser jp;
        try (InputStream is = resource.getInputStream()){
            jp = factory.createParser(is);
            return  mapper.readValue(jp, new TypeReference<List<ActionConfiguration>>() {});

        } catch (IOException e) {
            throw new RuntimeException("Cannot parse resource " + resource + ":" + e.getMessage(), e);
        }
    }


    private Action toAction(ActionConfiguration actionConfiguration) {
        ResponseExtractor<ExecutionResult> extractor = createExtractorFor(actionConfiguration);

        return Action.builder()
                .template(actionConfiguration.getTemplate())
                .method(actionConfiguration.getMethod())
                .name(actionConfiguration.getName())
                .extractor(extractor).build();
    }

    private ResponseExtractor<ExecutionResult> createExtractorFor(ActionConfiguration actionConfiguration) {
        return actionConfiguration.getExtractorType().createExtractor(actionConfiguration.getExtractorConfiguration());
    }


}
