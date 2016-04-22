package org.synyx.syscontrol.action;

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
public class ActionServiceUnitTest {
    
    private final List<Action> providedActions = TestData.testActions();
    private ActionProvider actionProvider = Mockito.mock(ActionProvider.class);
    
    private ActionService actionService = new ActionService(actionProvider);
    
    @Test
    public void testReturnsSystemsProvidedByProvider() throws Exception {
        Mockito.when(actionProvider.getAllActions()).thenReturn(providedActions);

        List<Action> actionList = actionService.listActions();

        Assert.assertThat(actionList, Matchers.is(providedActions));
    }


    @Test
    public void testReturnsSystemByName() throws Exception {
        Mockito.when(actionProvider.getAllActions()).thenReturn(providedActions);

        Optional<Action> action = actionService.getByName(providedActions.get(0).getName());

        Assert.assertThat(action.get(), Matchers.is(providedActions.get(0)));
    }


    @Test
    public void testReturnsEmptySystemWhenNameIsUnknown() throws Exception {
        Mockito.when(actionProvider.getAllActions()).thenReturn(providedActions);

        Optional<Action> system = actionService.getByName("Hurz");

        Assert.assertThat(system.isPresent(), Matchers.is(false));
    }
    
}