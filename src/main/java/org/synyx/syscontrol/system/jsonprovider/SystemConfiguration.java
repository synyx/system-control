package org.synyx.syscontrol.system.jsonprovider;

import lombok.*;

import java.util.Set;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemConfiguration {
    private String name;
    private String host;

    @Singular
    private Set<String> tags;

    private String username;

    private String password;


}
