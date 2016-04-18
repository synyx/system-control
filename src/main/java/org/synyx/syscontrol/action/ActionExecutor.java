package org.synyx.syscontrol.action;

import org.synyx.syscontrol.system.System;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class ActionExecutor {

   
    
    private final RestTemplate restTemplate;

    public ActionExecutor() {
        restTemplate =  new RestTemplate();
        //restTemplate.setErrorHandler(responseErrorHandler);
    }

    public ExecutionResult execute(Action action, System system) {
        String url = system.getHost() + action.getTemplate();
        try {
            
        return restTemplate.execute(url, action.getMethod(), null, action.getExtractor());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ExecutionResult.builder().status(e.getStatusCode()).data("message", e.getMessage()).build();
        }
        
    }
    
}
