package org.synyx.syscontrol.action.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionConfiguration {

    private String name;

    private String template;

    private HttpMethod method = HttpMethod.GET;

    private ExtractorType extractorType = ExtractorType.STATUS;

    private Map<String, String> extractorConfiguration;

}
