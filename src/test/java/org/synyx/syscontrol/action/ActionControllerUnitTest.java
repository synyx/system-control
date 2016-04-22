package org.synyx.syscontrol.action;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.syscontrol.TestData;
import org.synyx.syscontrol.execution.ActionExecutor;
import org.synyx.syscontrol.execution.ExecutionResult;
import org.synyx.syscontrol.system.System;
import org.synyx.syscontrol.system.SystemService;

import java.util.List;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class ActionControllerUnitTest {

    private final List<Action> actionList = TestData.testActions();
    private final List<System> systemList = TestData.testSystems();
    
    private ActionService actionService = Mockito.mock(ActionService.class);
    private SystemService systemService = Mockito.mock(SystemService.class);
    private ActionExecutor actionExecutor = Mockito.mock(ActionExecutor.class);
    
    private ActionController actionController = new ActionController(actionService, systemService, actionExecutor);


    @Test
    public void testActionList() throws Exception {
        Mockito.when(actionService.listActions()).thenReturn(actionList);

        List<Action> actions = actionController.index();

        Assert.assertThat(actions, Matchers.is(actionList));
    }

    @Test
    public void testExecuteWhenSystemAndActionExist() throws Exception {
        Action action = actionList.get(0);
        System system = systemList.get(0);
        ExecutionResult expectedResult = TestData.completeExecutionResult();

        Mockito.when(actionService.getByName(action.getName())).thenReturn(Optional.of(action));
        Mockito.when(systemService.getByName(system.getName())).thenReturn(Optional.of(system));
        Mockito.when(actionExecutor.execute(action, system)).thenReturn(expectedResult);

        ExecutionResult result = actionController.execute(action.getName(), system.getName());

        Assert.assertThat(result, Matchers.is(expectedResult));
    }

    @Test(expected = RuntimeException.class)
    public void throwsWhenActionIsNotfound() throws Exception {
        System system = systemList.get(0);

        Mockito.when(actionService.getByName("unknown")).thenReturn(Optional.empty());
        Mockito.when(systemService.getByName(system.getName())).thenReturn(Optional.of(system));

        actionController.execute("unknown", system.getName());
    }


    @Test(expected = RuntimeException.class)
    public void throwsWhenSystemIsNotFound() throws Exception {
        Action action = actionList.get(0);

        Mockito.when(actionService.getByName(action.getName())).thenReturn(Optional.of(action));
        Mockito.when(systemService.getByName("unknown")).thenReturn(Optional.empty());

        actionController.execute(action.getName(), "unknown");
    }
}