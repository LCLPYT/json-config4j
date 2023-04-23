package work.lclpnet.config.json;

import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An optional wrapper class that encapsulates config loading and writing.
 * When using this class, consumers don't have to worry about handling {@link IOException}s or synchronization.
 * @param <T> The config class type.
 */
public class ConfigHandler<T extends JsonConfig> {

    private final Path file;
    private final ConfigSerializer<T> serializer;
    private final Logger logger;
    private T config;

    /**
     * Create a new config handler.
     * @param file The path to read / write configs from / to.
     * @param serializer A fitting config serializer. When writing to the file system, you probably want to use {@link FileConfigSerializer}.
     * @param logger A logger for error logging.
     */
    public ConfigHandler(Path file, ConfigSerializer<T> serializer, Logger logger) {
        this.file = file;
        this.serializer = serializer;
        this.logger = logger;
    }

    public boolean loadConfig() {
        final T config;
        try {
            config = serializer.loadConfig(file);
        } catch (IOException e) {
            logger.error("Failed to load config", e);
            return false;
        }

        synchronized (this) {
            this.config = config;
        }

        return true;
    }

    public boolean writeConfig() {
        final T config;

        synchronized (this) {
            config = this.config;
        }

        try {
            serializer.saveConfig(config, file);
        } catch (IOException e) {
            logger.error("Failed to save config", e);
            return false;
        }

        return true;
    }

    public T getConfig() {
        return config;
    }

    public void setConfig(T config) {
        this.config = config;
    }
}
