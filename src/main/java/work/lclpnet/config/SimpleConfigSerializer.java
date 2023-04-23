package work.lclpnet.config;

import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleConfigSerializer<T extends JsonConfig> implements ConfigSerializer<T> {

    private final Logger logger;
    private final JsonConfigFactory<T> factory;

    public SimpleConfigSerializer(Logger logger, JsonConfigFactory<T> factory) {
        this.logger = logger;
        this.factory = factory;
    }

    public T loadConfig(Path file) throws IOException {
        if (!Files.exists(file)) {
            var conf = factory.createDefaultConfig();

            try {
                saveConfig(conf, file);
            } catch (IOException e) {
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