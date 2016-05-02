package org.synyx.syscontrol.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.Set;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
