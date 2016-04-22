package org.synyx.syscontrol.system;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.syscontrol.TestData;

import java.util.List;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class SystemServiceUnitTest {
    
    private SystemProvider systemProvider = Mockito.mock(SystemProvider.class);
    
    private SystemService systemService = new SystemService(systemProvider);
    
    private final List<System> providedSystems = TestData.testSystems();


    @Test
    public void testReturnsSystemsProvidedByProvider() throws Exception {
        Mockito.when(systemProvider.getAllSystems()).thenReturn(providedSystems);

        List<System> systemList = systemService.listSystems();

        Assert.assertThat(systemList, Matchers.is(providedSystems));
    }


    @Test
    public void testReturnsSystemByName() throws Exception {
        Mockito.when(systemProvider.getAllSystems()).thenReturn(providedSystems);

        Optional<System> system = systemService.getByName(providedSystems.get(0).getName());

        Assert.assertThat(system.get(), Matchers.is(providedSystems.get(0)));
    }


    @Test
    public void testReturnsEmptySystemWhenNameIsUnknown() throws Exception {
        Mockito.when(systemProvider.getAllSystems()).thenReturn(providedSystems);

        Optional<System> system = systemService.getByName("Hurz");

        Assert.assertThat(system.isPresent(), Matchers.is(false));
    }
}