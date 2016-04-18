package org.synyx.syscontrol.action;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
public class ExecutionResult {

    private HttpStatus status;

    @Singular("data")
    private Map<String, Object> data = new HashMap<>();
}
