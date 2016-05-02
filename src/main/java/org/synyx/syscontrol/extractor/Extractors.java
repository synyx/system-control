package org.synyx.syscontrol.extractor;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseExtractor;
import org.synyx.syscontrol.execution.ExecutionResult;

import java.nio.charset.Charset;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class Extractors {
    
    public static final ResponseExtractor<ExecutionResult> DEFAULT_EXTRACTOR = (ClientHttpResponse response) ->
            ExecutionResult.builder()
                    .status(response.getStatusCode())
                    .data("statusText", response.getStatusText())
                    .build();
    public static final ResponseExtractor<ExecutionResult> FULL_BODY_EXTRACTOR = (ClientHttpResponse response) ->
            ExecutionResult.builder()
                    .status(response.getStatusCode())
                    .data("statusText", response.getStatusText())
                    .data("data", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()))
                    .build();
}
