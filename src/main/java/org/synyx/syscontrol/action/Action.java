package org.synyx.syscontrol.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.util.Set;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
public class Action {
    
    private static final ResponseExtractor<ExecutionResult>  DEFAULT_EXTRACTOR = (ClientHttpResponse response) ->
            ExecutionResult.builder()
                    .status(response.getStatusCode())
                    .data("statusText", response.getStatusText())
                    .build();
    
    private String name;

    @JsonIgnore
    private String template;
    
    @JsonIgnore
    private HttpMethod method = HttpMethod.GET;

    @JsonIgnore
    private ResponseExtractor<ExecutionResult> extractor;
    
    @Singular("forTag")
    private Set<String> tagRestriction;

    
    @JsonIgnore
    public ResponseExtractor<ExecutionResult> getExtractor() {
        if (extractor != null) {
            return extractor;
        } else {
            return DEFAULT_EXTRACTOR;
        }
    }
    

}
