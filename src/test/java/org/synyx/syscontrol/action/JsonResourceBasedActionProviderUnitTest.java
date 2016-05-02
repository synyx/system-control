package org.synyx.syscontrol.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.synyx.syscontrol.TestData;
import org.synyx.syscontrol.action.view.ActionConfiguration;
import org.synyx.syscontrol.action.view.ExtractorType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class JsonResourceBasedActionProviderUnitTest {

    @Test
    public void testReadsJson() throws Exception {
        List<Action> expectedActions = Arrays.asList(TestData.completeAction());
        
        List<ActionConfiguration> view = expectedActions.stream()
                .map( (Action a) -> ActionConfiguration.builder()
                        .name(a.getName())
                        .template(a.getTemplate())
                        .method(a.getMethod())
                        .extractorType(ExtractorType.STATUS)
                        .build()).
                collect(Collectors.toList());
        
        String json = new ObjectMapper().writeValueAsString(view);
        Resource resource = new ByteArrayResource(json.getBytes());
        
        JsonResourceBasedActionProvider provider = new JsonResourceBasedActionProvider(resource);
        
        List<Action> actions = provider.getAllActions();

        Assert.assertThat(actions, Matchers.is(expectedActions));
    }
}