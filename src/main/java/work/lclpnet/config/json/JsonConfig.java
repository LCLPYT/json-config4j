package work.lclpnet.config.json;

import org.json.JSONObject;

/**
 * An interface to convert a config to a {@link JSONObject}.
 * Config classes should implement this interface and write its contents to the returned {@link JSONObject}.
 */
public interface JsonConfig {

    /**
     * Serializes the config to a {@link JSONObject}.
     * @return A {@link JSONObject} representation of this config.
     */
    JSONObject toJson();
}
