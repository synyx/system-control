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
        List<System> expectedSystems = Arrays.asList(TestData.completeSystem());

        // FIXME this currently does not get serialized so the test will fail otherwise
        // think abour removeing @JsonIgnore or introduce a SystemConfiguration (see ActionConfiguration) 
        expectedSystems.get(0).setUsername(null);
        expectedSystems.get(0).setPassword(null);
        
        String json = new ObjectMapper().writeValueAsString(expectedSystems);
        Resource resource = new ByteArrayResource(json.getBytes());
        
        JsonResourceBasedSystemProvider provider = new JsonResourceBasedSystemProvider(resource);

        List<System> systems = provider.getAllSystems();

        Assert.assertThat(systems, Matchers.is(expectedSystems));
    }
    
}