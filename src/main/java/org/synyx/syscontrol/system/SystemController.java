package org.synyx.syscontrol.system;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SystemController {
    
    @Autowired
    private SystemProvider systemProvider;

    @RequestMapping("/system")
    public List<System> index() {
        return systemProvider.listSystems();
    }

    @RequestMapping("/system/{systemName}")
    public System show(@PathVariable("systemName") String systemName) {
        return systemProvider.getByName(systemName).orElse(null);
    }
}
