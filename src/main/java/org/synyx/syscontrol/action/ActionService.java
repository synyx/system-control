package org.synyx.syscontrol.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class ActionService {

    private final ActionProvider actionProvider;

    @Autowired
    public ActionService(ActionProvider actionProvider) {
        this.actionProvider = actionProvider;
    }


    public List<Action> listActions() {
        return actionProvider.getAllActions();
    }


    public Optional<Action> getByName(String actionName) {

        return listActions().stream().filter(
                (Action a) -> actionName.equals(a.getName())
        ).findFirst();

    }
}
