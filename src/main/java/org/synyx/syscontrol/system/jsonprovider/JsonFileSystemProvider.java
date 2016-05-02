package org.synyx.syscontrol.system.jsonprovider;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.synyx.syscontrol.system.System;
import org.synyx.syscontrol.system.SystemProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
@Primary
public class JsonFileSystemProvider implements SystemProvider {

    public static final String SYSTEMS_RESOURCE = "systems.json";
    private final Resource resource;

    


    @Autowired
    public JsonFileSystemProvider(@Value("${systems.jsonfile}") Resource resource) {
        this.resource = resource;
    }


    @Override
    public List<System> getAllSystems() {

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser jp;
        try (InputStream is = resource.getInputStream()){
            jp = factory.createParser(is);
            return  mapper.readValue(jp, new TypeReference<List<System>>() {});
           
        } catch (IOException e) {
            
            throw new RuntimeException("Cannot parse Resource " + resource + ":" + e.getMessage(), e);
        } 
        
        
    }
}
