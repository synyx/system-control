package org.synyx.syscontrol.action;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */

import org.synyx.syscontrol.system.System;
import org.synyx.syscontrol.system.SystemProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionProvider actionProvider;

    @Autowired
    private SystemProvider systemProvider;
    
    @Autowired
    private ActionExecutor actionExecutor;

    @RequestMapping("/")
    public List<Action> index() {
        return actionProvider.listActions();
    }

    @RequestMapping( value = "/{actionName}", method = RequestMethod.GET)
    public ExecutionResult execute(@PathVariable("actionName") String actionName, @RequestParam("systemName") String systemName) {
        Optional<Action> action = actionProvider.getByName(actionName);
        Optional<System> system = systemProvider.getByName(systemName);
        
        return actionExecutor.execute(action.orElseThrow(RuntimeException::new), system.orElseThrow(RuntimeException::new));
    }
}
