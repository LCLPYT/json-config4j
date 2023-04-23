package work.lclpnet.config.json;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface for config serialization.
 * Implementations are responsible for config reading and writing.
 * @param <T> The config class.
 */
public interface ConfigSerializer<T extends JsonConfig> {

    /**
     * Loads a config from some source.
     * @param file The path to the config.
     * @return The loaded config object.
     * @throws IOException On unsuccessful I/O-operations.
     */
    T loadConfig(Path file) throws IOException;

    /**
     * Writes a specified config to some destination.
     * @param config The config to write.
     * @param file The target path to the config.
     * @throws IOException On unsuccessful I/O-operations.
     */
    void saveConfig(T config, Path file) throws IOException;
}
