package org.synyx.syscontrol.action;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class ActionProvider {
    
    private static final List<Action> ACTIONS = Arrays.asList(
            Action.builder().template("/").name("GET_aufs_Root").method(HttpMethod.GET).build(),
            Action.builder().template("/tasks/status").name("TaskExecutor_Status").method(HttpMethod.GET).build(),
            Action.builder().template("/tasks/XZ").name("Ausschalten_DWH").method(HttpMethod.PUT).build()

            
    );
    
    public List<Action> listActions() {
        return ACTIONS;
    }


    public Optional<Action> getByName(String actionName) {
        
        return listActions().stream().filter(
                (Action a) -> actionName.equals(a.getName())
            ).findFirst();
        
    }
}
