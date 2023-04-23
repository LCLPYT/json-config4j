package work.lclpnet.config.json;

import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A config serializer that reads from and writes to the default file system.
 * @param <T> The config class.
 */
public class FileConfigSerializer<T extends JsonConfig> implements ConfigSerializer<T> {

    private final JsonConfigFactory<T> factory;
    private final Logger logger;

    /**
     * Create a new FileConfigSerializer.
     *
     * @param factory A {@link JsonConfigFactory} that converts {@link JSONObject}s to configs and provides a default config.
     * @param logger A logger for error logging.
     */
    public FileConfigSerializer(JsonConfigFactory<T> factory, Logger logger) {
        this.factory = factory;
        this.logger = logger;
    }

    public T loadConfig(Path file) throws IOException {
        if (!Files.exists(file)) {
            var conf = factory.createDefaultConfig();

            try {
                saveConfig(conf, file);
            } catch (IOException e) {
                // ignore the error but log the error
                logger.error("Failed to save config", e);
            }

            return conf;
        }

        var content = Files.readString(file, StandardCharsets.UTF_8);
        var json = new JSONObject(content.trim());
        var config = factory.createConfig(json);

        if (!json.similar(config.toJson())) {
            // old config detected, overwrite with newer version
            saveConfig(config, file);
        }

        return config;
    }

    public void saveConfig(T config, Path file) throws IOException {
        var json = config.toJson();
        var content = json.toString(2);

        var dir = file.getParent();

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        Files.writeString(file, content, StandardCharsets.UTF_8);
    }
}