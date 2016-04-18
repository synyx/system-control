package org.synyx.syscontrol.system;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Service
public class SystemProvider {

    public static final List<String> TENANT = Arrays.asList("DE", "CZ", "HU", "SI", "AT", "VL");
    private static final List<String> STAGES = Arrays.asList("DEV", "STAGE", "PERF", "RELEASE", "PROD");
    private static final List<String> NODES = Arrays.asList("APPSRV01", "APPSRV02", "APPSRV03", "APPSRV04", "IMPORT");


    private List<System> systems = new ArrayList<>();

    public SystemProvider() {
        create();
    }

    public List<System> listSystems() {
        return systems;
    }


    public Optional<System> getByName(String systemName) {

        return listSystems().stream().filter(
                (System a) -> systemName.equals(a.getName())
        ).findFirst();

    }



    public void create() {
        for (String mandant : TENANT) {
            for (String stage : STAGES) {

                if (Arrays.asList("STAGE", "DEV", "PERF").contains(stage)) {
                    if (! Arrays.asList("DE", "VL").contains(mandant)) {
                        continue;
                    }
                } else if (Arrays.asList( "VL").contains(mandant)) {
                    continue;
                }

                for (String node : NODES) {

                    System.SystemBuilder builder = System.builder();
                    builder
                            .host("http://localhost:8080/action/")
                            .name(String.format("%s %s %s", mandant, stage, node))
                            .tag(mandant).tag(stage).tag(node);

                    if (!node.equals("IMPORT")) {
                        builder.tag("APPSRV");
                    }


                    systems.add(builder.build());


                }

            }
        }

    }
}
