package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private Map<String, String> environment;

    public EnvController(@Value("${PORT:NOT SET}") String port, @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String instanceIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String instanceAddress){
        environment = new HashMap<String, String>();

        environment.put("PORT", port);
        environment.put("MEMORY_LIMIT", memoryLimit);
        environment.put("CF_INSTANCE_INDEX", instanceIndex);
        environment.put("CF_INSTANCE_ADDR", instanceAddress);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv(){
        return environment;
    }
}
