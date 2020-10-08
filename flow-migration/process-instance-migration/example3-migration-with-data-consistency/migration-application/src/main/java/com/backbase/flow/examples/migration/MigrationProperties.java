package com.backbase.flow.examples.migration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "migration")
@Getter
@Setter
public class MigrationProperties {

    private ProcessDefinition sourceDefinition;
    private ProcessDefinition targetDefinition;
    private List<BreakingActivity> activities = new ArrayList<>();
    private List<String> migrationIsland = new ArrayList<>();
    private boolean updateEventTriggers;
    private boolean skipIoMappings;
    private boolean skipCustomListeners;

    @Getter
    @Setter
    public static class ProcessDefinition {
        private String key;
        private String version;
    }

    @Getter
    @Setter
    public static class BreakingActivity {
        private String source;
        private String target;
        private boolean updateEventTrigger;
    }

}
