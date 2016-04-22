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
    
    private final SystemService systemService;
    
    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }
    
    @RequestMapping("/system")
    public List<System> index() {
        return systemService.listSystems();
    }

    @RequestMapping("/system/{systemName}")
    public System show(@PathVariable("systemName") String systemName) {
        return systemService.getByName(systemName).orElse(null);
    }
}
