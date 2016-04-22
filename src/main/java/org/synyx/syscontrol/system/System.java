package org.synyx.syscontrol.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Set;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
public class System {
    private String name;
    private String host;

    @Singular
    private Set<String> tags;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;


}
