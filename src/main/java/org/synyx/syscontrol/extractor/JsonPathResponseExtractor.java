package org.synyx.syscontrol.extractor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;
import org.synyx.syscontrol.execution.ExecutionResult;

import java.io.IOException;
import java.util.Map;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class JsonPathResponseExtractor implements ResponseExtractor<ExecutionResult> {
    
    private final Map<String, String> pathExpressions;

    public JsonPathResponseExtractor(Map<String, String> pathExpressions) {
        this.pathExpressions = pathExpressions;
    }

    @Override
    public ExecutionResult extractData(ClientHttpResponse response) throws IOException {
        
        DocumentContext context = JsonPath.parse(response.getBody());

        ExecutionResult.ExecutionResultBuilder builder = ExecutionResult.builder().status(response.getStatusCode());
        
        if (response.getStatusCode().is2xxSuccessful()) {

            for (Map.Entry<String, String> pathExpression : pathExpressions.entrySet()) {
                String result = context.read(pathExpression.getValue(), String.class);
                builder.data(pathExpression.getKey(), result);
            }
            
        }

        return builder.build();

    }
}
