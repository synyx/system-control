package org.synyx.syscontrol.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResponseExtractor;
import org.synyx.syscontrol.execution.ExecutionResult;
import org.synyx.syscontrol.extractor.Extractors;

import java.util.Set;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
public class Action {


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
            return Extractors.DEFAULT_EXTRACTOR;
        }
    }


}
