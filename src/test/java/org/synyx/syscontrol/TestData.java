package org.synyx.syscontrol;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.synyx.syscontrol.action.Action;
import org.synyx.syscontrol.execution.ExecutionResult;
import org.synyx.syscontrol.system.System;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class TestData {

    public static List<System> testSystems() {
        
        return Arrays.asList(completeSystem(), System.builder().name("two").build());
    }

    public static List<Action> testActions() {
        return Arrays.asList(completeAction());
    }

    public static Action completeAction() {
        return Action.builder()
                .method(HttpMethod.DELETE)
                .name("action")
                .template("template")
                .build();
    }

    public static System completeSystem() {
        return System.builder()
                .host("http://localhost")
                .name("system")
                .username("user")
                .password("pass").build();
    }

    public static ExecutionResult completeExecutionResult() {
        return ExecutionResult.builder()
                .data("thisis", "expected").status(HttpStatus.MULTI_STATUS).build();
    }
}
