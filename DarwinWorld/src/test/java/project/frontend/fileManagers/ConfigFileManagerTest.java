package project.frontend.fileManagers;

import org.junit.jupiter.api.Test;
import project.backend.backend.globalViaSimulation.GlobalOptions;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;

import java.util.List;

class ConfigFileManagerTest {
    @Test
    public void testLoadConfigFile() {
        try {
            ConfigFileManager.add(new GlobalOptions(1 , 2 , MapType.WATER_MAP , MutationType.FULL_RANDOM , 5 , 6 , 7 , 8 , 9 , 10 , 11 , 12 , 13 , 14));
            ConfigFileManager.add(new GlobalOptions(1 , 2 , MapType.WATER_MAP , MutationType.FULL_RANDOM , 5 , 6 , 7 , 8 , 9 , 10 , 11 , 12 , 13 , 14));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<GlobalOptions> a = ConfigFileManager.readAllConfigs();
        System.out.println(a);
    }

}