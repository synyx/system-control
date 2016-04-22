package org.synyx.syscontrol.execution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.synyx.syscontrol.action.Action;
import org.synyx.syscontrol.system.System;

import java.nio.charset.Charset;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class ActionExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(ActionExecutor.class);

    private final RestTemplate restTemplate;

    public ActionExecutor() {
        this(new RestTemplate());
    }

    public ActionExecutor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExecutionResult execute(Action action, System system) {
        String url = system.getHost() + action.getTemplate();
        try {
            LOG.info("Executing request action on url {}: {}", url, action);
            return restTemplate.execute(url, action.getMethod(), authCallback(system), action.getExtractor());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ExecutionResult.builder().status(e.getStatusCode()).data("message", e.getMessage()).build();
        }

    }

    private RequestCallback authCallback(System system) {
        return (ClientHttpRequest request) -> {
            String auth = system.getUsername() + ":" + system.getPassword();
            byte[] encodedAuth = Base64Utils.encode(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            request.getHeaders().add("Authorization", authHeader);
        };
    }


}
