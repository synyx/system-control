package org.synyx.syscontrol.system.jsonprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.synyx.syscontrol.TestData;
import org.synyx.syscontrol.system.System;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class JsonResourceBasedSystemProviderUnitTest {
    

    @Test
    public void testReadsJson() throws Exception {
        System expectedSystem = TestData.completeSystem();
        SystemConfiguration configuration = createConfigurationForSystem(expectedSystem);

        List<SystemConfiguration> configurations = Arrays.asList(configuration);

        
        String json = new ObjectMapper().writeValueAsString(configurations);
        Resource resource = new ByteArrayResource(json.getBytes());
        
        JsonResourceBasedSystemProvider provider = new JsonResourceBasedSystemProvider(resource);

        List<System> systems = provider.getAllSystems();

        Assert.assertThat(systems, Matchers.is(Arrays.asList(expectedSystem)));
    }

    private SystemConfiguration createConfigurationForSystem(System expectedSystem) {
        return SystemConfiguration.builder()
                    .host(expectedSystem.getHost())
                    .name(expectedSystem.getName())
                    .username(expectedSystem.getUsername())
                    .password(expectedSystem.getPassword())
                    .tags(expectedSystem.getTags())
                    .build();
    }

}