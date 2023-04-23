package work.lclpnet.config;

import java.io.IOException;
import java.nio.file.Path;

public interface ConfigSerializer<T extends JsonConfig> {

    T loadConfig(Path file) throws IOException;

    void saveConfig(T config, Path file) throws IOException;
}
