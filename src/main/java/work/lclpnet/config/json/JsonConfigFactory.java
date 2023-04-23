package work.lclpnet.config.json;

import org.json.JSONObject;

/**
 * A config factory that converts {@link JSONObject}s to a specified config instance.
 * @param <T> The config class.
 */
public interface JsonConfigFactory<T extends JsonConfig> {

    /**
     * Create a defaulted config.
     * @return The default config.
     */
    T createDefaultConfig();

    /**
     * Deserialize a {@link JSONObject} to a config.
     * @param json The json source.
     * @return The parsed config object.
     */
    T createConfig(JSONObject json);
}
