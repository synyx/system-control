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
public class SystemControllerUnitTest {

    private final List<System> systemList = TestData.testSystems();
    private SystemService systemService = Mockito.mock(SystemService.class);
    
    private SystemController systemController = new SystemController(systemService);

    @Test
    public void testListMethod() throws Exception {
        Mockito.when(systemService.listSystems()).thenReturn(systemList);
        
        List<System> systems = systemController.index();

        Assert.assertThat(systems, Matchers.is(systemList));
    }
    
    @Test
    public void testShowMethodWithExistingSystem() throws Exception {
        Mockito.when(systemService.getByName("name")).thenReturn(Optional.of(systemList.get(0)));

        System system = systemController.show("name");

        Assert.assertThat(system, Matchers.is(systemList.get(0)));
    }

    @Test
    public void testShowMethodWithNonExistingSystem() throws Exception {
        Mockito.when(systemService.getByName("doesnotexist")).thenReturn(Optional.empty());

        System system = systemController.show("doesnotexist");

        Assert.assertThat(system, Matchers.nullValue());
    }
}