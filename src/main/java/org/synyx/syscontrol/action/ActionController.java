package org.synyx.syscontrol.action;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.synyx.syscontrol.execution.ActionExecutor;
import org.synyx.syscontrol.execution.ExecutionResult;
import org.synyx.syscontrol.system.System;
import org.synyx.syscontrol.system.SystemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/action")
public class ActionController {

    private final ActionService actionService;

    private final SystemService systemService;
        
    private final ActionExecutor actionExecutor;

    @Autowired
    public ActionController(ActionService actionService, SystemService systemService, ActionExecutor actionExecutor) {
        this.actionService = actionService;
        this.systemService = systemService;
        this.actionExecutor = actionExecutor;
    }

    @RequestMapping("/")
    public List<Action> index() {
        return actionService.listActions();
    }

    @RequestMapping( value = "/{actionName}", method = RequestMethod.GET)
    public ExecutionResult execute(@PathVariable("actionName") String actionName, @RequestParam("systemName") String systemName) {
        Optional<Action> action = actionService.getByName(actionName);
        Optional<System> system = systemService.getByName(systemName);
        
        return actionExecutor.execute(action.orElseThrow(RuntimeException::new), system.orElseThrow(RuntimeException::new));
    }
}
