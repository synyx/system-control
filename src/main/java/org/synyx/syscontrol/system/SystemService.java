package org.synyx.syscontrol.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class SystemService {
  
    private final SystemProvider systemProvider;


    private Map<String, System> cache = new HashMap<>();


    @Autowired
    public SystemService(SystemProvider systemProvider) {
        this.systemProvider = systemProvider;
    }
    
    public List<System> listSystems() {
       
        return updateCache();
    }

    private List<System> updateCache() {
        List<System> allSystems = systemProvider.getAllSystems();
        cache.clear();
        for (System system : allSystems) {
            cache.put(system.getName(), system);
        }
        return allSystems;
    }


    public Optional<System> getByName(String systemName) {
        if (cache.isEmpty()) {
            updateCache();
        }
        
        return Optional.ofNullable(cache.get(systemName));
    }
    
}
